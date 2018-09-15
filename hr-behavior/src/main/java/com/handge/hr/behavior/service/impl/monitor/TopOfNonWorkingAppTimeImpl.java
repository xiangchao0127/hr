package com.handge.hr.behavior.service.impl.monitor;

import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.EsUtil;
import com.handge.hr.domain.entity.behavior.web.request.monitor.NonWorkingAppTimeDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.monitor.TopOfNonWorkingAppTimeParam;
import com.handge.hr.domain.entity.behavior.web.response.monitor.NonWorkingAppTimeDetail;
import com.handge.hr.domain.entity.behavior.web.response.monitor.NonWorkingAppTimeTop;
import com.handge.hr.behavior.service.api.monitor.ITopOfNonWorkingAppTime;
import com.handge.hr.behavior.common.utils.FormulaUtil;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.ESIndexEnum;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.mapper.BehaviorTagUrlMapper;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author liuqian
 * @date 2018/7/13
 * @Description:
 */
@Component
public class TopOfNonWorkingAppTimeImpl implements ITopOfNonWorkingAppTime {
    /**
     * 基础数据库查询bean
     */
    @Autowired
    private IBaseDAO baseDAO;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    BehaviorTagUrlMapper behaviorTagUrlMapper;

    @Override
    public Object listTopOfNonWorkingAppTime(TopOfNonWorkingAppTimeParam topOfNonWorkingAppTimeParam) {
        List<NonWorkingAppTimeTop> result = getResults(topOfNonWorkingAppTimeParam);
        return (topOfNonWorkingAppTimeParam.getN() <= result.size() ? result.subList(0, topOfNonWorkingAppTimeParam.getN()) : result);
    }

    @Override
    public Object listNonWorkingAppTimeDetail(NonWorkingAppTimeDetailParam nonWorkingAppTimeDetailParam) {
        List<NonWorkingAppTimeDetail> result = getResults(nonWorkingAppTimeDetailParam);
        //返回分页数据
        return CollectionUtils.getPageResult(result, nonWorkingAppTimeDetailParam.getPageNo(), nonWorkingAppTimeDetailParam.getPageSize());
    }

    /**
     * 查询ES
     *
     * @return SearchResponse
     */
    private SearchResponse searchForEs(Object object) {
        //今天零点零分零秒的时间戳
        long zeroTimeStamp = DateUtil.getDayStartTimeStamp();
        //获取当前系统时间戳
        long curTimeStamp = DateUtil.getNowTimeStamp();
        //工作无关标签
        List<String> nonWorkingTagList = baseDAO.listTagsOfNonWorking();
        //获取配置
        Map<String, Object> configParamMap = baseDAO.getConfigParam();

        Client esClient = elasticsearchTemplate.getClient();
        String[] indices = EsUtil.generateIndices(new Date(), new Date(), ESIndexEnum.MAPPING);

        String esType = configParamMap.get("ES_TYPE").toString();
        //非工作性上网sourceIP、count
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders
                        .rangeQuery("startTime")
                        .from(zeroTimeStamp, true)
                        .to(curTimeStamp, true)
                )
                .filter(QueryBuilders.termsQuery("appTags", nonWorkingTagList));
        if (object instanceof TopOfNonWorkingAppTimeParam) {
            String departmentName = ((TopOfNonWorkingAppTimeParam) object).getDepartment();
            if (StringUtils.notEmpty(departmentName)) {
                if (baseDAO.getEmployeeIps(departmentName).size() == 0) {
                    throw new UnifiedException("部门名称不存在 ", ExceptionWrapperEnum.IllegalArgumentException);
                }
                queryBuilder.filter(QueryBuilders.termsQuery("localIp", baseDAO.getEmployeeIps(departmentName).get(departmentName)));
            }
        }
        TermsAggregationBuilder appAgg = AggregationBuilders.terms("appCount").field("appName").size(1000);
        SearchRequestBuilder srb = esClient.prepareSearch(indices)
                .setTypes(esType)
                .setQuery(queryBuilder)
                .addAggregation(appAgg);
        return srb.execute().actionGet();
    }

    /**
     * 查询Mysql
     *
     * @return
     */
    private Map<String, String> searchForMysql() {
        Map<String, String> appNameAndTagMap = new HashMap<>(200);
        List<Map<String, String>> appNameMapList = behaviorTagUrlMapper.getAppName();
        for (Map<String, String> map : appNameMapList) {
            appNameAndTagMap.put(map.get("app_name"), map.get("basicclass"));
        }
        return appNameAndTagMap;
    }

    private List getResults(Object object) {
        TreeMap<String, Long> appNameAndCountMap = new TreeMap<>();
        Map<String, Object> configParamMap = baseDAO.getConfigParam();
        double minuteByOneClick = Double.parseDouble(String.valueOf(configParamMap.get("MINUTE_BY_ONE_CLICK")));
        SearchResponse response = searchForEs(object);
        Terms terms = response.getAggregations().get("appCount");
        Iterator<Terms.Bucket> iterator = (Iterator<Terms.Bucket>) terms.getBuckets().iterator();
        while (iterator.hasNext()) {
            Terms.Bucket bucket = iterator.next();
            String appName = bucket.getKeyAsString();
            long appNameCount = bucket.getDocCount();
            //将appName为""的情况排除
            if (StringUtils.notEmpty(appName)) {
                appNameAndCountMap.put(appName, appNameCount);
            }
        }
        //将appName统计次数按降序排列
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(appNameAndCountMap.entrySet());
        // 通过比较器来实现排序
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                // 降序排序
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        if (object instanceof TopOfNonWorkingAppTimeParam) {
            List<NonWorkingAppTimeTop> appTime = new ArrayList<>();
            for (Map.Entry<String, Long> entry : list) {
                NonWorkingAppTimeTop nonWorkingAppTimeTop = new NonWorkingAppTimeTop();
                nonWorkingAppTimeTop.setAppName(entry.getKey());
                nonWorkingAppTimeTop.setTimes(FormulaUtil.timesToHourMinute(entry.getValue(), String.valueOf(minuteByOneClick)));
                appTime.add(nonWorkingAppTimeTop);
            }
            return appTime;
        } else {
            List<NonWorkingAppTimeDetail> appTime = new ArrayList<>();
            Map<String, String> appNameAndTagMap = searchForMysql();
            for (Map.Entry<String, Long> entry : list) {
                NonWorkingAppTimeDetail nonWorkingAppTimeDetail = new NonWorkingAppTimeDetail();
                nonWorkingAppTimeDetail.setAppName(entry.getKey());
                nonWorkingAppTimeDetail.setTimes(FormulaUtil.timesToHourMinute(entry.getValue(), String.valueOf(minuteByOneClick)));
                nonWorkingAppTimeDetail.setBasicTag(appNameAndTagMap.get(entry.getKey()));
                appTime.add(nonWorkingAppTimeDetail);
            }
            return appTime;
        }
    }
}
