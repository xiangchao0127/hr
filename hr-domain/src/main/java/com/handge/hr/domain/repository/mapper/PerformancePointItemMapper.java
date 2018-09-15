package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.handler.JsonTypeHandler;
import com.handge.hr.domain.repository.pojo.PerformancePointItem;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author liuqian
 * @date 2018/8/22
 * @Description:
 */
public interface PerformancePointItemMapper extends CommonMapper<PerformancePointItem,Integer> {

    /**
     * 根据模块获取积分卡信息
     * @param origin 来源类型
     * @return
     */
    @Select("SELECT\n" +
            "\tpc.ID card_id,\n" +
            "\tpc.NAME card_name,\n" +
            "\tpi.ID item_id,\n" +
            "\tpi.NAME item_name,\n" +
            "\tpi.input_type,\n" +
            "\tpi.OPTIONS \n" +
            "FROM\n" +
            "\tperformance_point_card pc\n" +
            "\tLEFT JOIN performance_m_point_card_to_point_item pm ON pc.ID = pm.point_card_id\n" +
            "\tLEFT JOIN performance_point_item pi ON pm.point_item_id = pi.ID \n" +
            "WHERE\n" +
            "\tpc.origin =#{ origin } \n" +
            "\tAND pi.is_active = 't'")
    List<Map<String,Object>> getCardsInfo(String origin);
}
