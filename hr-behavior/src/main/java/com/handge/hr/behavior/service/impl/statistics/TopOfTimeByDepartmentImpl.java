package com.handge.hr.behavior.service.impl.statistics;

import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.common.utils.EsUtil;
import com.handge.hr.domain.entity.behavior.web.request.statistics.TimeByDepartmentDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.statistics.TopOfTimeByDepartmentParam;
import com.handge.hr.domain.entity.behavior.web.response.statistics.NonWorkingTimeByDepartment;
import com.handge.hr.behavior.service.api.statistics.ITopOfTimeByDepartment;
import com.handge.hr.behavior.common.utils.FormulaUtil;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.enumeration.base.ESIndexEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.exception.custom.UnifiedException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作无关部门上网人均时长TOP(统计)
 *
 * @author liuqian
 */
@Component
public class TopOfTimeByDepartmentImpl implements ITopOfTimeByDepartment {
    // TODO: 2018/7/9 liuqian 角色判断
    /**
     * 基础数据库查询bean
     */
    @Autowired
    private IBaseDAO baseDAO;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public Object listTopOfTimeByDepartment(TopOfTimeByDepartmentParam topOfTimeByDepartmentParam) {
        //如果没有指定周期，默认周期当前月一号到目前日期
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateFormatEnum.SECONDS.getFormat());
        if (!StringUtils.notEmpty(topOfTimeByDepartmentParam.getStartTime())) {
            String yearAndMonthStr = df.format(day).substring(0, 8);
            String dateStr = yearAndMonthStr + "01 00:00:00";
            topOfTimeByDepartmentParam.setStartTime(dateStr);
        }
        if (!StringUtils.notEmpty(topOfTimeByDepartmentParam.getEndTime())) {
            topOfTimeByDepartmentParam.setEndTime(df.format(day));
        }
        List<NonWorkingTimeByDepartment> result = getResults(topOfTimeByDepartmentParam.getStartTime(), topOfTimeByDepartmentParam.getEndTime());
        //返回top n
        return (topOfTimeByDepartmentParam.getN() <= result.size() ? result.subList(0, topOfTimeByDepartmentParam.getN()) : result);
    }

    @Override
    public Object listTimeByDepartmentDetail(TimeByDepartmentDetailParam timeByDepartmentDetailParam) {
        //如果没有指定周期，默认周期当前月一号到目前日期
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateFormatEnum.SECONDS.getFormat());
        if (!StringUtils.notEmpty(timeByDepartmentDetailParam.getStartTime())) {
            String yearAndMonthStr = df.format(day).substring(0, 8);
            String dateStr = yearAndMonthStr + "01 00:00:00";
            timeByDepartmentDetailParam.setStartTime(dateStr);
        }
        if (!StringUtils.notEmpty(timeByDepartmentDetailParam.getEndTime())) {
            timeByDepartmentDetailParam.setEndTime(df.format(day));
        }
        //返回分页数据
        List<NonWorkingTimeByDepartment> result = getResults(timeByDepartmentDetailParam.getStartTime(), timeByDepartmentDetailParam.getEndTime());
        return CollectionUtils.getPageResult(result, timeByDepartmentDetailParam.getPageNo(), timeByDepartmentDetailParam.getPageSize());
    }

    /**
     * 查询es，获取内网ip及访问次数
     *
     * @param startTime 周期开始时间
     * @param endTime   周期结束时间
     * @return Map：ipFrequencyMap
     */
    private Map<String, Integer> searchForEs(String startTime, String endTime) {
        //获取配置
        Map<String, Object> configParamMap = baseDAO.getConfigParam();
        //转换成时间戳
        long startStamp = 0;
        try {
            startStamp = DateUtil.dateToTimeStamp(startTime, DateFormatEnum.SECONDS);
        } catch (ParseException e) {
            throw new UnifiedException(e);
        }
        long endStamp = 0;
        try {
            endStamp = DateUtil.dateToTimeStamp(endTime, DateFormatEnum.SECONDS);
        } catch (ParseException e) {
            throw new UnifiedException(e);
        }

        List<String> nonWorkingTagList = baseDAO.listTagsOfNonWorking();
        Map<String, Integer> ipFrequencyMap = new HashMap<>(200);

        //索引
        String[] indices = EsUtil.generateIndices(startTime, endTime, ESIndexEnum.MAPPING);
        Client esClient = elasticsearchTemplate.getClient();
        String esType = configParamMap.get("ES_TYPE").toString();
        //非工作性上网sourceIP、count
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders
                        .rangeQuery("startTime")
                        .from(startStamp, true)
                        .to(endStamp, true)
                )
                .filter(QueryBuilders.termsQuery("appTags", nonWorkingTagList));

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("ipCount").field("localIp").size(10000);
        SearchRequestBuilder srb = esClient.prepareSearch(indices)
                .setTypes(esType)
                .setQuery(queryBuilder)
                .addAggregation(aggregation);

        SearchResponse response = srb.execute().actionGet();
        Map<String, Aggregation> map = response.getAggregations().asMap();
        StringTerms terms = (StringTerms) map.get("ipCount");

        for (Terms.Bucket bucket : terms.getBuckets()) {
            String sourceIp = (String) bucket.getKey();
            long countSourceIp = bucket.getDocCount();
            ipFrequencyMap.put(sourceIp, (int) countSourceIp);
        }
        return ipFrequencyMap;
    }

    /**
     * 查询mysql,获取部门及部门访问总次数
     *
     * @param startTime 周期开始时间
     * @param endTime   周期结束时间
     * @return Map<String,Long>
     */
    private List<Map<String, Integer>> searchForMySql(String startTime, String endTime) {
        List<Map<String, Integer>> result = new ArrayList<>();
        Map<String, Integer> ipFrequencyMap = searchForEs(startTime, endTime);
        Map<String, Integer> departmentTotalNumMap;
        Map<String, ArrayList<String>> departmentIpsMap = new HashMap<>(30);
        Map<String, Integer> departmentFrequencyMap = new HashMap<>(30);
        //获取部门名称、部门员工总数
        departmentTotalNumMap = baseDAO.numberOfEmployeesGroupByDep();
        //获取 部门--部门ip集合
        departmentIpsMap = baseDAO.getEmployeeIps(null);
        //得到 部门-次数的map
        //遍历 部门--部门ip集合的map
        for (Map.Entry<String, ArrayList<String>> entry1 : departmentIpsMap.entrySet()) {
            //遍历ip--次数map集合
            for (Map.Entry<String, Integer> entry2 : ipFrequencyMap.entrySet()) {
                //获取各部门ip集合
                List<String> ipList = entry1.getValue();
                //获取ip--次数map中的key值
                String ip = entry2.getKey();
                //判断部门ip集合中是否存在该key
                //如果存在，获取值（即该ip对应的次数），存入 部门-次数 的map中
                if (ipList.contains(ip)) {
                    //判断部门-次数map中是否存在该部门的统计值
                    //如果存在，将值取出来更新之后再覆盖
                    String departmentName = entry1.getKey();
                    int deparmentFrequecncy = entry2.getValue();
                    if (departmentFrequencyMap.containsKey(departmentName)) {
                        //存在，更新该值
                        int frequency = departmentFrequencyMap.get(departmentName);
                        int newFrequency = frequency + deparmentFrequecncy;
                        departmentFrequencyMap.put(departmentName, newFrequency);
                    } else {
                        //不存在，直接添加
                        departmentFrequencyMap.put(departmentName, deparmentFrequecncy);
                    }
                }
            }
        }
        result.add(departmentTotalNumMap);
        result.add(departmentFrequencyMap);
        return result;
    }

    /**
     * 获取非工作性上网部门排名
     *
     * @param startTime 周期开始时间
     * @param endTime   周期结束时间
     * @return List<NonWorkingTimeByDepartment>
     */
    private List<NonWorkingTimeByDepartment> getResults(String startTime, String endTime) {
        List<NonWorkingTimeByDepartment> result = new ArrayList<>();
        List<Map<String, Integer>> maps = searchForMySql(startTime, endTime);
        Map<String, Integer> departmentFrequencyMap = maps.get(1);
        Map<String, Integer> departmentTotalNumMap = maps.get(0);
        TreeMap<String, Integer> departmentAvgFrequencyMap = new TreeMap<>();
        //部门每人每天平均时长
        for (Map.Entry<String, Integer> entry : departmentFrequencyMap.entrySet()) {
            String departmentName = entry.getKey();
            int totalFrequency = entry.getValue();

            int avgFrequency = totalFrequency / departmentTotalNumMap.get(departmentName);
            int avgFrequencyPerDay = 0;
            try {
                avgFrequencyPerDay = avgFrequency / DateUtil.differentDays(startTime, endTime, DateFormatEnum.SECONDS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            departmentAvgFrequencyMap.put(departmentName, avgFrequencyPerDay);
        }
        //将部门—次数TreeMap按value降序排列
        // 将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(departmentAvgFrequencyMap.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                // 降序排序
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        NonWorkingTimeByDepartment nonWorkingTimeByDepartment;
        Map<String, Object> configParamMap = baseDAO.getConfigParam();
        double minuteByOneClick = Double.parseDouble(String.valueOf(configParamMap.get("MINUTE_BY_ONE_CLICK")));
        for (Map.Entry<String, Integer> entry : list) {
            String dname = entry.getKey();
            nonWorkingTimeByDepartment = new NonWorkingTimeByDepartment();
            //部门
            nonWorkingTimeByDepartment.setDepartment(dname);
            //部门人数
            nonWorkingTimeByDepartment.setDepartmentNum(departmentTotalNumMap.get(dname).toString());
            //部门总时长
            int departmentTotalDrequency = departmentFrequencyMap.get(dname);
            String departmentTotalTime = FormulaUtil.timesToHourMinute(departmentTotalDrequency, String.valueOf(minuteByOneClick));
            nonWorkingTimeByDepartment.setTimeByDepartment(departmentTotalTime);
            //部门人均每天时长
            int departmentAvgDrequency = entry.getValue();
            String departmentAvgTime = FormulaUtil.timesToHourMinute(departmentAvgDrequency, String.valueOf(minuteByOneClick));
            nonWorkingTimeByDepartment.setTimeByPerson(departmentAvgTime);
            result.add(nonWorkingTimeByDepartment);
        }
        return result;
    }
}
