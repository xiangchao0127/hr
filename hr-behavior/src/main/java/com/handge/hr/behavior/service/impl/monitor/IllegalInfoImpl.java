package com.handge.hr.behavior.service.impl.monitor;

import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.common.utils.EsUtil;
import com.handge.hr.domain.entity.behavior.web.request.monitor.IllegalDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.monitor.IllegalInfoParam;
import com.handge.hr.domain.entity.behavior.web.response.monitor.IllegalInfo;
import com.handge.hr.behavior.service.api.monitor.IIllegalInfo;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.enumeration.base.ESIndexEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import com.handge.hr.exception.custom.UnifiedException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

/**
 * Created by DaLu Guo on 2018/5/16.
 */
@Component
public class IllegalInfoImpl implements IIllegalInfo {
    @Autowired
    IBaseDAO baseDAO;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    /**
     * 直接查询es
     * @param illegalInfoParam
     * @return
     */
    @Override
    public Object listIllegalInfo(IllegalInfoParam illegalInfoParam) {
        Map<String, Object> configMap = baseDAO.getConfigParam();
        List<String> illegalTags = Arrays.asList(configMap.get("ILLEGAL_TAGS").toString().split(","));
        //region 1 查詢es
        Client esClient = elasticsearchTemplate.getClient();
        String today = DateUtil.timeStampToStrDate(System.currentTimeMillis(), DateFormatEnum.DAY);
        String[] indices = EsUtil.generateIndices(today, today, ESIndexEnum.MAPPING);
        String esType = configMap.get("ES_TYPE").toString();
        long startTimeStamp = 0;
        try {
            startTimeStamp = DateUtil.dateToStartOrEndStamp(DateUtil.timeStampToStrDate(System.currentTimeMillis(), DateFormatEnum.SECONDS), 0);
        } catch (ParseException e) {
            throw new UnifiedException(e);
        }
        long endTimeStamp = System.currentTimeMillis();
        List<String> ips = new ArrayList<>();
        if (StringUtils.notEmpty(illegalInfoParam.getDepartment())) {
            ips = baseDAO.getEmployeeIps(illegalInfoParam.getDepartment()).get(illegalInfoParam.getDepartment());
        } else {
            for (String key : baseDAO.getEmployeeIps("").keySet()) {
                ips.addAll(baseDAO.getEmployeeIps("").get(key));
            }
        }
        if (ips == null) {
            return new IllegalInfo();
        }
        SearchRequestBuilder builder = esClient.prepareSearch(indices)
                .setTypes(esType)
                .setQuery(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.rangeQuery("startTime").from(startTimeStamp, true).to(endTimeStamp, true))
                        .filter(QueryBuilders.termsQuery("appTags", illegalTags))
                        .filter(QueryBuilders.termsQuery("localIp", ips))
                )
                .addAggregation(AggregationBuilders.terms("appName").field("appName").size(illegalInfoParam.getN())
                        .subAggregation(AggregationBuilders.terms("localIp").field("localIp").size(10000)));
        SearchResponse response = builder.execute().actionGet();
        //endregion
        return wrapData(response);
    }

    @Override
    public Object listIllegalInfoDetail(IllegalDetailParam illegalDetailParam) {
        Map<String, Object> configMap = baseDAO.getConfigParam();
        List<String> illegalTags = Arrays.asList(configMap.get("ILLEGAL_TAGS").toString().split(","));
        //region 1 查詢es
        Client esClient = elasticsearchTemplate.getClient();
        String esType = configMap.get("ES_TYPE").toString();
        long startTimeStamp = 0;
        try {
            startTimeStamp = DateUtil.dateToTimeStamp(illegalDetailParam.getStartTime(), DateFormatEnum.SECONDS);
        } catch (ParseException e) {
            throw new UnifiedException(e);
        }
        long endTimeStamp = 0;
        try {
            endTimeStamp = DateUtil.dateToTimeStamp(illegalDetailParam.getEndTime(), DateFormatEnum.SECONDS);
        } catch (ParseException e) {
            throw new UnifiedException(e);
        }
        String[] indices = EsUtil.generateIndices(startTimeStamp, endTimeStamp, ESIndexEnum.MAPPING);
        List<String> ips = getIps(illegalDetailParam.getDepartment(), illegalDetailParam.getName(), illegalDetailParam.getNumber());
        SearchRequestBuilder builder = esClient.prepareSearch(indices)
                .setTypes(esType)
                .setQuery(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.rangeQuery("startTime").from(startTimeStamp, true).to(endTimeStamp, true))
                        .filter(QueryBuilders.termsQuery("appTags", illegalTags))
                        .filter(QueryBuilders.termsQuery("localIp", ips))
                )
                .addAggregation(AggregationBuilders.terms("appName").field("appName").size(10000)
                        .subAggregation(AggregationBuilders.terms("localIp").field("localIp").size(10000)));
        SearchResponse response = builder.execute().actionGet();
        //endregion
        List<IllegalInfo> list = wrapData(response);
        return CollectionUtils.getPageResult(list,illegalDetailParam.getPageNo(),illegalDetailParam.getPageSize());
    }

    private List<IllegalInfo> wrapData( SearchResponse response){
        List<IllegalInfo> result = new ArrayList<>();
        //获取所有员工ip及对应的职工编号
        HashMap<String, String> allEmployeeIpAndNumber = baseDAO.getAllEmployeeIpAndNumber();
        Map<String, String> allEmployeeNumberAndName = baseDAO.getAllEmployeeNumberAndName();
        Terms appNameTerm =  response.getAggregations().get("appName");
        for(Terms.Bucket bucket : appNameTerm.getBuckets() ){
            String appName = bucket.getKeyAsString();
            String appTagCount = String.valueOf(bucket.getDocCount());
            Terms localIpTerm =  bucket.getAggregations().get("localIp");
            HashMap<String,Long> nameAndCountMap = new HashMap<>();
            for(Terms.Bucket ipBucket : localIpTerm.getBuckets()){
                String number = allEmployeeIpAndNumber.get(ipBucket.getKey());
                long count = ipBucket.getDocCount();
                if(nameAndCountMap.keySet().contains(number)){
                    long current = nameAndCountMap.get(number);
                    nameAndCountMap.put(number,current + count);
                }else{
                    nameAndCountMap.put(number,count);
                }
            }
            //map排序
            List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(nameAndCountMap.entrySet());
            // 通过比较器来实现排序
            list.sort(new Comparator<Map.Entry<String, Long>>() {
                @Override
                public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                    // 降序排序
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            List<String> names = new ArrayList<>();
            for (Map.Entry<String, Long> mapping : list) {
                if(names.size() >= 3){
                    break;
                }else{
                    names.add(allEmployeeNumberAndName.get(mapping.getKey()));
                }
            }
            IllegalInfo illegalInfo = new IllegalInfo();
            illegalInfo.setAppName(appName);
            illegalInfo.setCount(appTagCount);
            illegalInfo.setName(names);
            result.add(illegalInfo);
        }
        return result;
    }

    private List<String> getIps(String department, String name, String number) {
        List<String> ips = entityEmployeeMapper.getIps(department,name,number);
        return ips;
    }
}
