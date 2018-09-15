package com.handge.hr.behavior.service.impl.professional;

import com.handge.hr.common.enumeration.behavior.EmployeeStatusEnum;
import com.handge.hr.common.utils.EsUtil;
import com.handge.hr.domain.entity.behavior.web.request.professional.EmployeeDetailsParam;
import com.handge.hr.domain.entity.behavior.web.response.professional.EmployeeDetailsInfo;
import com.handge.hr.behavior.service.api.professional.IEmployeeDetails;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.ESIndexEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DaLu Guo on 2018/6/12.
 */
@Component
public class EmployeeDetailsImpl implements IEmployeeDetails {
    /**
     * 基础数据库查询Bean
     */
    @Autowired
    IBaseDAO baseDAO;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;

    @Override
    public Object getEmployeeDetails(EmployeeDetailsParam employeeDetailsParam) {

        Map<String, Object> configMap = baseDAO.getConfigParam();

        /**
         * 1 查询mysql
         */
        Map<String, Object> resultSet = searchFronmMysql(employeeDetailsParam.getNumber());
        /**
         * 2 查询标签
         */
        List<String> tags = searchFromEs(employeeDetailsParam.getNumber(), configMap);
        /**
         * 3 封装数据返回前端
         */

        EmployeeDetailsInfo employeeDetailsInfo = returnResult(resultSet, tags);

        return employeeDetailsInfo;
    }

    private Map<String,Object> searchFronmMysql(String number) {
        return entityEmployeeMapper.getEmployeeInfoByNumber(number);
    }

    private List<String> searchFromEs(String number, Map configMap) {
        List<String> ips = baseDAO.getIpsByNo(number);
        Client esClient = elasticsearchTemplate.getClient();
        long startTimeStamp = DateUtil.getTimeByLastYear();
        long endTimeStamp = System.currentTimeMillis();
        String[] indices = EsUtil.generateIndices(startTimeStamp, endTimeStamp, ESIndexEnum.MAPPING);
        String esType = configMap.get("ES_TYPE").toString();
        SearchRequestBuilder builder = esClient.prepareSearch(indices)
                .setTypes(esType)
                .setQuery(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.rangeQuery("startTime").from(startTimeStamp, true).to(endTimeStamp, true))
                        .mustNot(QueryBuilders.termsQuery("appName", ""))
                        .filter(QueryBuilders.termsQuery("localIp", ips))
                )
                .addAggregation(AggregationBuilders.terms("appTags").field("appTags").size(10)
                        .subAggregation(AggregationBuilders.count("count").field("_index"))
                );
        SearchResponse response = builder.execute().actionGet();
        List<String> tags = new ArrayList<>();
        Terms appTerm = response.getAggregations().get("appTags");
        for (Terms.Bucket bucket1 : appTerm.getBuckets()) {
            tags.add(bucket1.getKeyAsString());
        }
        return tags;
    }

    private EmployeeDetailsInfo returnResult(Map<String,Object> resultSet, List<String> tags) {
        EmployeeDetailsInfo employeeDetailsInfo = new EmployeeDetailsInfo();
        employeeDetailsInfo.setName(resultSet.get("name").toString());
        employeeDetailsInfo.setDepartment(resultSet.get("department_name").toString());
        employeeDetailsInfo.setNumber(resultSet.get("job_number").toString());
        employeeDetailsInfo.setStatus(EmployeeStatusEnum.getDescByStatus(resultSet.get("job_status").toString()));
        // TODO: 2018/8/9 郭大露
        employeeDetailsInfo.setSeniority("null");
        employeeDetailsInfo.setPostAge("null");

        employeeDetailsInfo.setPost(resultSet.get("post").toString());
        employeeDetailsInfo.setPositionalTitles(resultSet.get("positional_titles").toString());
        employeeDetailsInfo.setTags(tags);

        return employeeDetailsInfo;
    }
}
