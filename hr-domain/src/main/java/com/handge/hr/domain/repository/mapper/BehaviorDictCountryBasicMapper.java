package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.entity.behavior.web.response.statistics.IpRegionOfCountry;
import com.handge.hr.domain.repository.pojo.BehaviorDictCountryBasic;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by DaLu Guo on 2018/8/8.
 */
public interface BehaviorDictCountryBasicMapper extends CommonMapper<BehaviorDictCountryBasic,Integer> {
    /**
     * 获取国家起始IP、结束IP
     * @return
     */
    @Select("SELECT cb.\"name\",m.\"start\", m.\"end\"\n" +
            "FROM  behavior_dict_country_basic cb\n" +
            "INNER JOIN behavior_dict_global_ip_country_map m ON m.country_id = cb.id ")
    List<IpRegionOfCountry> getIp();
}
