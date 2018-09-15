package com.handge.hr.behavior.service.impl.monitor;

import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.EsUtil;
import com.handge.hr.domain.entity.behavior.web.request.monitor.NonWorkingTimeDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.monitor.TopOfNonWorkingTimeParam;
import com.handge.hr.domain.entity.behavior.web.response.monitor.AppTime;
import com.handge.hr.domain.entity.behavior.web.response.monitor.NonWorkingTimeLengthUserTop;
import com.handge.hr.behavior.service.api.monitor.ITopOfNonWorkingTime;
import com.handge.hr.behavior.common.utils.FormulaUtil;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.ESIndexEnum;
import com.handge.hr.domain.repository.mapper.BehaviorEntityDeviceBasicMapper;
import com.handge.hr.exception.custom.UnifiedException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 工作无关上网时长Top（实时监控）
 *
 * @author liuqian
 */
@Component
public class TopOfNonWorkingTimeImpl implements ITopOfNonWorkingTime {
    // TODO: 2018/5/10 liuqian 角色判断
    /**
     * 基础数据库查询bean
     */
    @Autowired
    private IBaseDAO baseDAO;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    BehaviorEntityDeviceBasicMapper behaviorEntityDeviceBasicMapper;

    @Override
    public Object listTopOfNonWorkingTime(TopOfNonWorkingTimeParam topOfNonWorkingTimeParam) {
        List<NonWorkingTimeLengthUserTop> result = getResult(topOfNonWorkingTimeParam);
        //返回top n
        return (topOfNonWorkingTimeParam.getN() <= result.size() ? result.subList(0, topOfNonWorkingTimeParam.getN()) : result);
    }

    @Override
    public Object listNonWorkingTimeDetail(NonWorkingTimeDetailParam nonWorkingTimeDetailParam) {
        List<NonWorkingTimeLengthUserTop> result = getResult(nonWorkingTimeDetailParam);
        //返回分页数据
        return CollectionUtils.getPageResult(result, nonWorkingTimeDetailParam.getPageNo(), nonWorkingTimeDetailParam.getPageSize());
    }

    /**
     * 查询mysql，获取员工姓名及员工ip集合、员工姓名及部门工号
     *
     * @return List ibdex 0:employeeIpsMap; index 1:employeeBasicMap
     */
    private List searchForMySql(Object o) {
        //员工姓名-ip集合
        Map<String, String> employeeIpsMap = new HashMap<>(100);
        //员工姓名-工号、部门
        Map<String, List<String>> employeeBasicMap = new HashMap<>(100);
        String number = "";
        String name = "";
        String department = "";
        if (o instanceof TopOfNonWorkingTimeParam) {
            department = ((TopOfNonWorkingTimeParam) o).getDepartment();
        }
        if (o instanceof NonWorkingTimeDetailParam) {
            NonWorkingTimeDetailParam nonWorkingTimeDetailParam = (NonWorkingTimeDetailParam) o;
            number = nonWorkingTimeDetailParam.getNumber();
            name = nonWorkingTimeDetailParam.getName();
            department = nonWorkingTimeDetailParam.getDepartment();
        }
        List<Map<String, Object>> mapList = behaviorEntityDeviceBasicMapper.getEmpNameIpDept(number, name, department);
        for (Map<String, Object> map : mapList) {
            String employeeName = map.get("name") == null ? null : map.get("name").toString();
            String employeeIpList = map.get("ip_list") == null ? null : map.get("ip_list").toString();
            employeeIpsMap.put(employeeName, employeeIpList);
            //存储 员工姓名--工号、部门
            List<String> nameNumberDepartmentList;
            nameNumberDepartmentList = new ArrayList<>();
            nameNumberDepartmentList.add(map.get("job_number") == null ? null : map.get("job_number").toString());
            nameNumberDepartmentList.add(map.get("department") == null ? null : map.get("department").toString());
            employeeBasicMap.put(employeeName, nameNumberDepartmentList);
        }
        List list = new ArrayList();
        list.add(employeeIpsMap);
        list.add(employeeBasicMap);
        return list;
    }

    /**
     * 查询es
     *
     * @return SearchResponse
     */
    private SearchResponse searchForEs() {
        //今天零点零分零秒的时间戳
        long zeroTimeStamp = DateUtil.getDayStartTimeStamp();
        //获取当前系统时间戳
        long curTimeStamp = DateUtil.getNowTimeStamp();
        //工作无关标签
        List<String> nonWorkingTagList = baseDAO.listTagsOfNonWorking();
        //获取配置
        Map<String, Object> configParamMap = baseDAO.getConfigParam();

        String[] indices = EsUtil.generateIndices(new Date(), new Date(), ESIndexEnum.MAPPING);
        Client esClient = elasticsearchTemplate.getClient();
        String esType = configParamMap.get("ES_TYPE").toString();
        //非工作性上网sourceIP、count
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders
                        .rangeQuery("startTime")
                        .from(zeroTimeStamp, true)
                        .to(curTimeStamp, true)
                )
                .filter(QueryBuilders.termsQuery("appTags", nonWorkingTagList));
        TermsAggregationBuilder ipAgg = AggregationBuilders.terms("ipCount").field("localIp").size(10000);
        TermsAggregationBuilder appAgg = AggregationBuilders.terms("appCount").field("appName").size(3);
        SearchRequestBuilder srb = esClient.prepareSearch(indices)
                .setTypes(esType)
                .setQuery(queryBuilder)
                .addAggregation(ipAgg.subAggregation(appAgg));
        //返回连接
        return srb.execute().actionGet();
    }

    /**
     * 处理es和mysql获取的数据，得到对象集合
     *
     * @return List<NonWorkingTimeLengthUserTop>
     */
    private List<NonWorkingTimeLengthUserTop> getResult(Object o) {
        List<NonWorkingTimeLengthUserTop> result = new ArrayList<>();
        Map<String, String> employeeIpsMap = null;
        Map<String, Map<String, Long>> ipAppNameCountMap = new HashMap<>(100);
        TreeMap<String, Long> appNameCountMap = null;
        TreeMap<String, Long> employeeFrequencyMap = new TreeMap<>();
        //员工姓名-工号、部门
        Map<String, List<String>> employeeBasicMap = new HashMap<>(100);
        Map<String, TreeMap<String, Long>> employeeAppFrequencyMap = new HashMap<>(200);

        try {
            //查询mysql
            List alist = searchForMySql(o);
            employeeIpsMap = (Map<String, String>) alist.get(0);
            employeeBasicMap = (Map<String, List<String>>) alist.get(1);
            //查询es
            SearchResponse response = searchForEs();

            Terms sourceIpCount = response.getAggregations().get("ipCount");
            for (Terms.Bucket bucket : sourceIpCount.getBuckets()) {
                String ip = bucket.getKeyAsString();
                long countSourceIp = bucket.getDocCount();
                Terms appCount = bucket.getAggregations().get("appCount");
                appNameCountMap = new TreeMap<>();
                for (Terms.Bucket apps : appCount.getBuckets()) {
                    String appName = (String) apps.getKey();
                    long count = apps.getDocCount();
                    appNameCountMap.put(appName, count);
                }
                ipAppNameCountMap.put(ip, appNameCountMap);
                for (Map.Entry<String, String> entry : employeeIpsMap.entrySet()) {
                    String empLoyeeName = entry.getKey();
                    //ip集合
                    String ips = entry.getValue();
                    List<String> ipList = Arrays.asList(ips.split(","));
                    //判断ip是否存在这个集合中
                    //如果存在，存入 员工-应用时长的map中
                    if (ipList.contains(ip)) {
                        //判断员工-次数map中是否存在该key
                        if (employeeFrequencyMap.containsKey(empLoyeeName)) {
                            //存在则将该Key的值取出来加上新值
                            Long oldFrequency = employeeFrequencyMap.get(empLoyeeName);
                            Long newFrequency = oldFrequency + countSourceIp;
                            employeeFrequencyMap.put(empLoyeeName, newFrequency);
                        } else {
                            //不存在，直接存
                            employeeFrequencyMap.put(empLoyeeName, countSourceIp);
                        }

                        //判断员工-应用次数map中是否存在该key
                        if (employeeAppFrequencyMap.containsKey(empLoyeeName)) {
                            //获取值
                            TreeMap<String, Long> appFrequency = employeeAppFrequencyMap.get(empLoyeeName);
                            for (Map.Entry<String, Long> entry1 : appFrequency.entrySet()) {
                                String appName = entry1.getKey();
                                Long count = entry1.getValue();
                                if (appNameCountMap.containsKey(appName)) {
                                    Long oldCount = appNameCountMap.get(appName);
                                    Long newCount = appFrequency.get(appName);
                                    appNameCountMap.put(appName, oldCount + newCount);
                                } else {
                                    appNameCountMap.put(appName, count);
                                }
                            }
                        } else {
                            employeeAppFrequencyMap.put(empLoyeeName, appNameCountMap);
                        }
                    }
                }
            }
            //将员工—次数TreeMap按value降序排列
            //将map.entrySet()转换成list
            List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(employeeFrequencyMap.entrySet());
            // 通过比较器来实现排序
            list.sort(new Comparator<Map.Entry<String, Long>>() {
                @Override
                public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                    // 降序排序
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            NonWorkingTimeLengthUserTop nonWorkingTimeLengthTop;
            Map<String, Object> configParamMap = baseDAO.getConfigParam();
            double minuteByOneClick = Double.parseDouble(String.valueOf(configParamMap.get("MINUTE_BY_ONE_CLICK")));
            for (Map.Entry<String, Long> mapping : list) {
                nonWorkingTimeLengthTop = new NonWorkingTimeLengthUserTop();
                String employeenName = mapping.getKey();
                nonWorkingTimeLengthTop.setName(employeenName);
                nonWorkingTimeLengthTop.setTimes(FormulaUtil.timesToHourMinute(mapping.getValue(), String.valueOf(minuteByOneClick)));
                nonWorkingTimeLengthTop.setNumber(employeeBasicMap.get(employeenName).get(0));
                nonWorkingTimeLengthTop.setDepartment(employeeBasicMap.get(employeenName).get(1));
                Map<String, Long> appFrequency = employeeAppFrequencyMap.get(employeenName);
                List<Map.Entry<String, Long>> list2 = new ArrayList<Map.Entry<String, Long>>(appFrequency.entrySet());
                // 通过比较器来实现排序
                list2.sort(new Comparator<Map.Entry<String, Long>>() {
                    @Override
                    public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                        // 降序排序
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });
                AppTime appTime = null;
                List<AppTime> appTimeList = new ArrayList<>();
                for (Map.Entry<String, Long> mapping2 : list2) {
                    appTime = new AppTime();
                    appTime.setAppName(mapping2.getKey());
                    appTime.setTime(FormulaUtil.timesToHourMinute(mapping2.getValue(), String.valueOf(minuteByOneClick)));
                    appTimeList.add(appTime);
                }
                nonWorkingTimeLengthTop.setAppTimeList(appTimeList);
                result.add(nonWorkingTimeLengthTop);
            }
        } catch (Exception e) {
            throw new UnifiedException(e);
        }
        return result;
    }
}