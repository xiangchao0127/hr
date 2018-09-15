package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.BehaviorTagUrl;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by DaLu Guo on 2018/8/8.
 */
public interface BehaviorTagUrlMapper extends CommonMapper<BehaviorTagUrl,Integer> {
    @Select("select app_name,CONCAT(basic_class) as basicClass FROM behavior_tag_url group by app_name,basic_class")
    List<Map<String,String>> getAppName();
}
