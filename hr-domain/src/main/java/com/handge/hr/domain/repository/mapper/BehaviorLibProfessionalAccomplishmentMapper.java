package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.BehaviorLibProfessionalAccomplishment;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by DaLu Guo on 2018/8/8.
 */
@Mapper
public interface BehaviorLibProfessionalAccomplishmentMapper extends CommonMapper<BehaviorLibProfessionalAccomplishment,Integer> {
    /**
     * 查询模型结果
     * @param yearMonth
     * @param year
     * @return
     */
    @Select("SELECT l.static_ip,l.time,l.working_attitude,l.loyalty,l.compliance_discipline " +
            "FROM behavior_lib_professional_accomplishment AS l WHERE time <= #{yearMonth} AND time >= #{year}\n")
    List<BehaviorLibProfessionalAccomplishment> getModleResultRangeTime(@Param("yearMonth") String yearMonth, @Param("year") String year);

    /**
     * 查询模型结果
     * @param yearMonth
     * @return
     */
    @Select("SELECT l.static_ip,l.time,l.working_attitude,l.loyalty,l.compliance_discipline " +
            "FROM behavior_lib_professional_accomplishment AS l WHERE time = #{yearMonth}")
    List<BehaviorLibProfessionalAccomplishment> getModelResultByTime(String yearMonth);
}
