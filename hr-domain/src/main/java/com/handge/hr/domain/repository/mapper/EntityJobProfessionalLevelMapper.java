package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.EntityJobProfessionalLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EntityJobProfessionalLevelMapper extends CommonMapper<EntityJobProfessionalLevel,String> {

    @Select("select id from entity_job_professional_level where professional_level=#{professionalLevel}")
    public String getId(String professionalLevel);
}

