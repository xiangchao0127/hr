package com.handge.hr.behavior.service.impl.statistics;

import com.handge.hr.common.enumeration.behavior.JobPropertyEnum;
import com.handge.hr.common.utils.EsUtil;
import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.behavior.web.request.statistics.FootPrintParam;
import com.handge.hr.domain.entity.behavior.web.response.statistics.InternetFootprint;
import com.handge.hr.domain.entity.behavior.web.response.statistics.InternetFootprintInfo;
import com.handge.hr.behavior.service.api.statistics.IInternetFootprint;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.enumeration.base.ESIndexEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.mapper.BehaviorTagPropertyMapper;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import com.handge.hr.domain.repository.pojo.BehaviorTagProperty;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

/**
 * @author MaJianfu
 * @date 2018/5/18 16:36
 **/
@SuppressWarnings("Duplicates")
@Component
public class InternetFootprintImpl implements IInternetFootprint {
    /**
     * 基础数据库查询Bean
     */
    @Autowired
    IBaseDAO baseDao;

    @Autowired
    BehaviorTagPropertyMapper tagPropertyMapper;

    @Autowired
    EntityEmployeeMapper employeeMapper;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    List<String> tagNameList = new ArrayList<>();
    List<String> ipList = new ArrayList<>();

    @Override
    public Object listInternetFootprint(FootPrintParam footPrintParam) {
        PageResults<InternetFootprint> internetFootprintPageResults = pageResult(footPrintParam,
                footPrintParam.getPageNo(),
                footPrintParam.getPageSize());
        return internetFootprintPageResults;
    }

    private PageResults<InternetFootprint> pageResult(FootPrintParam footPrintParam, int pageNo, int pageSize) {

        List<InternetFootprint> result = new ArrayList<>();
        Map<String, String> internetFootprintMap = new LinkedHashMap<>();
        String[] indices = EsUtil.generateIndices(
                StringUtils.notEmpty(footPrintParam.getStartTime()) ? footPrintParam.getStartTime() : new Date(),
                StringUtils.notEmpty(footPrintParam.getEndTime()) ? footPrintParam.getEndTime() : new Date(),
                ESIndexEnum.MAPPING
        );

        Client client = elasticsearchTemplate.getClient();
        if (JobPropertyEnum.UNCERTAIN.getCode().equals(footPrintParam.getProperty())) {
            tagNameList.add("");
        }
        BehaviorTagProperty tag = new BehaviorTagProperty();
        String code = "全部";
        if (code.equals(footPrintParam.getProperty()) || StringUtils.isEmpty(footPrintParam.getProperty())) {
            tag.setProperty(null);
        }
        tag.setProperty(footPrintParam.getProperty());
        List<BehaviorTagProperty> propertyList = tagPropertyMapper.select(tag);
        for (BehaviorTagProperty pro : propertyList) {
            tagNameList.add(pro.getTagName());
        }
        String department = "";
        String name = "";
        String number = "";
        String ip = "";
        String type = "";
        String id = "";
        String accessTime = "";
        String app = "";
        String url = "";
        String property = "";
        if (StringUtils.notEmpty(footPrintParam.getName())) {
            String[] split = footPrintParam.getName().split("\\|", -1);
            int splitLength = 3;
            if (split.length != splitLength) {
                // TODO: 2018/5/31 参数异常
                throw new UnifiedException("传入参数不正确 ", ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        List<Map<String, String>> footPrintDetails = employeeMapper.getFootPrintDetails(footPrintParam);
        for (Map<String, String> map : footPrintDetails) {
            department = map.get("department_name");
            name = map.get("name");
            number = map.get("number");
            ip = map.get("static_ip");
            ipList.add(ip);
        }
        int startNum = (pageNo - 1) * pageSize;
        int endSize = pageNo * pageSize <= 10000 ? pageSize : 10000 - startNum;
        QueryBuilder queryBuilder = createQuery(footPrintParam);
        Map<String, Object> configParamMap = baseDao.getConfigParam();
        String esType = configParamMap.get("ES_TYPE").toString();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indices).setTypes(esType)
                .setQuery(queryBuilder).addSort("startTime", SortOrder.DESC).setFrom(startNum).setSize(endSize);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        SearchHits hits = response.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next();
            id = searchHit.getId().toString();
            app = searchHit.getSource().get("appName").toString();
            url = searchHit.getSource().get("domain").toString();
            accessTime = searchHit.getSource().get("startTime").toString();
            ip = searchHit.getSource().get("localIp").toString();
            type = searchHit.getSource().get("appTags").toString();
            String style = type.replace("[,", "[[],");
            property = propertyQuery(type);
            internetFootprintMap.put(id, ip + "|" + style + "|" + accessTime + "|" + app + "|" + url + "|" + property);
        }
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>
                (internetFootprintMap.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return -Long.compare(Long.parseLong(o1.getValue().split("\\|")[2]),
                        Long.parseLong(o2.getValue().split("\\|")[2]));
            }
        });
        List<InternetFootprintInfo> infoList = new ArrayList<>();
        for (Map.Entry<String, String> mapping : list) {
            InternetFootprintInfo internetFootprintInfo = new InternetFootprintInfo();
            String value = mapping.getValue();
            String[] str = value.split("\\|", -1);
            internetFootprintInfo.setIp(str[0]);
            internetFootprintInfo.setType(str[1]);
            internetFootprintInfo.setAccessTime(DateUtil.timeStampToStrDate(Long.valueOf(str[2]), DateFormatEnum.SECONDS));
            internetFootprintInfo.setApp(str[3]);
            internetFootprintInfo.setUrl(str[4]);
            internetFootprintInfo.setProperty(str[5]);
            infoList.add(internetFootprintInfo);
        }

        InternetFootprint internetFootprint = new InternetFootprint();
        internetFootprint.setDepartment(department);
        internetFootprint.setName(name);
        internetFootprint.setNumber(number);
        internetFootprint.setInfoList(infoList);
        tagNameList.clear();
        ipList.clear();
        if (!"".equals(internetFootprint.getNumber())) {
            result.add(internetFootprint);
        }
        PageResults<InternetFootprint> pageResult = new PageResults<>();
        pageResult.setCurrentPage(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalCount((int) hits.getTotalHits() > 10000 ? 10000 : (int) hits.getTotalHits());
        pageResult.setPageCount(((pageResult.getTotalCount() - 1) / pageSize) + 1);
        pageResult.setNextPageNo(pageNo + 1 < pageResult.getPageCount() ? pageNo + 1 : pageResult.getPageCount());
        pageResult.setResults(result);
        return pageResult;
    }

    private BoolQueryBuilder createQuery(FootPrintParam footPrintParam) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        // 如果ip存在，只要符合一种就查询
        query.filter(QueryBuilders.termsQuery("localIp", ipList));
        //appName
        if (StringUtils.notEmpty(footPrintParam.getApp())) {
            query.filter(new QueryStringQueryBuilder(footPrintParam.getApp()).field("appName"));
        }
        //Type
        if (StringUtils.notEmpty(footPrintParam.getType())) {
            query.filter(new QueryStringQueryBuilder(footPrintParam.getType()).field("appTags"));
        }
        //Property
        if (StringUtils.notEmpty(footPrintParam.getProperty())) {
            query.filter(QueryBuilders.termsQuery("appTags", tagNameList));
        }
        //StartTime
        if (StringUtils.notEmpty(footPrintParam.getStartTime())) {
            long begin = 0;
            try {
                begin = DateUtil.dateToTimeStamp(footPrintParam.getStartTime(), DateFormatEnum.SECONDS);
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
            query.filter(new RangeQueryBuilder("startTime").format("epoch_millis").gte(begin));
        }
        //EndTime
        if (StringUtils.notEmpty(footPrintParam.getEndTime())) {
            long end = 0;
            try {
                end = DateUtil.dateToTimeStamp(footPrintParam.getEndTime(), DateFormatEnum.SECONDS);
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
            query.filter(new RangeQueryBuilder("startTime").format("epoch_millis").lte(end));
        }
        return query;
    }

    private String propertyQuery(String type) {
        String property = "";
        String tags = "";
        Set<String> set = new HashSet<String>();
        HashMap<String, String> tagMap = new HashMap<>();
        List<BehaviorTagProperty> behaviorTagProperties = tagPropertyMapper.selectAll();
        for (BehaviorTagProperty tagProperty : behaviorTagProperties) {
            tagMap.put(tagProperty.getTagName(), tagProperty.getProperty());
        }
        String acceptType = "[]";
        String acceptNewType = ",";
        if (!acceptType.equals(type)) {
            String replace = type.replace("[", "").replace("]", "").replace(" ", "");
            if (!replace.contains(acceptNewType)) {
                if (tagMap.containsKey(replace)) {
                    tags = tagMap.get(replace);
                    if (tags.equals(JobPropertyEnum.WORK_RELATED.getCode())) {
                        set.add("工作相关");
                    } else if (tags.equals(JobPropertyEnum.WORK_NO_RELATED.getCode())) {
                        set.add("工作无关");
                    } else {
                        set.add("不确定");
                    }
                }
            } else {
                String[] arr = replace.split(",");
                for (String array : arr) {
                    if ("".equals(array)) {
                        set.add("不确定");
                    } else {
                        if (tagMap.containsKey(array)) {
                            tags = tagMap.get(array);
                            if (tags.equals(JobPropertyEnum.WORK_RELATED.getCode())) {
                                set.add("工作相关");
                            } else if (tags.equals(JobPropertyEnum.WORK_NO_RELATED.getCode())) {
                                set.add("工作无关");
                            } else {
                                set.add("不确定");
                            }
                        }
                    }
                }
            }
        } else {
            set.add("不确定");
        }
        String[] arrStr = set.toArray(new String[set.size()]);
        property = Arrays.asList(arrStr).toString();
        return property;
    }
}
