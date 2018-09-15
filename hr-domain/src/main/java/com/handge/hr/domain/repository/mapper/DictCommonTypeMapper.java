package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.DictCommonType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DictCommonTypeMapper extends CommonMapper<DictCommonType,Integer> {

    @Select("select id from  dict_common_type where name_ch=#{nameCh} and name_en=#{nameEn}")
    Integer getId(@Param("nameCh") String nameCh, @Param("nameEn") String nameEn);

    @Insert("insert into dict_common_type(name_ch,name_en) values(#{nameCh},#{nameEn}) ")
    void save(@Param("nameCh") String nameCh, @Param("nameEn") String nameEn);
}
