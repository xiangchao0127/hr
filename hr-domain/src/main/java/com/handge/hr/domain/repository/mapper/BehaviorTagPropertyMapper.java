package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.BehaviorTagProperty;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by DaLu Guo on 2018/8/8.
 */
public interface BehaviorTagPropertyMapper extends CommonMapper<BehaviorTagProperty,Integer> {

    /**
     * 获取工作【无关】应用标签列表
     * @return
     */
    @Select("SELECT tag_name AS tagName FROM behavior_tag_property WHERE property ='0'" )
    List<String> listTagsOfNonWorking();

    /**
     * 获取工作【有关】应用标签列表
     * @return
     */
    @Select("SELECT tag_name AS tagName FROM behavior_tag_property WHERE property ='1'" )
    List<String> listTagsOfWorking();


}
