package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.entity.manage.web.response.archive.CommonData;
import com.handge.hr.domain.entity.manage.web.response.archive.DictCommonData;
import com.handge.hr.domain.repository.pojo.DictCommon;
import com.handge.hr.domain.repository.pojo.DictCommonTypeView;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DictCommonMapper extends CommonMapper<DictCommon,Integer> {

    @Insert("insert into dict_common(common_type_id,name,sub_order) values(#{commonTypeId},#{name},#{subOrder}) ")
    void save(@Param("commonTypeId") int commonTypeId, @Param("name") String name, @Param("subOrder") int subOrder);

    /**
     * 查询岗位状态id
     * @param name
     * @return
     */
    @Select("select id from dict_common where name=#{name} and common_type_id = 8")
    int getIdByCommonTypeAndName(String name);

    @ResultType(DictCommon.class )
    @Select("select id,name,code from dict_common")
    @MapKey("id")
    Map<Integer, DictCommon> getDictCommonMap();

    /**
     * 查询枚举表字段对应id
     * @param nameEn 大类名称
     * @param code  小类编号
     * @return
     */
    @Select("select c.name from dict_common c JOIN dict_common_type t ON c.common_type_id=t.id where t.name_en=#{nameEn} and c.code=#{code}")
    String getName(@Param("nameEn") String nameEn,@Param("code") String code);

    @Select("select c.code from dict_common c JOIN dict_common_type t ON c.common_type_id=t.id where t.name_en=#{nameEn} and c.name=#{name}")
    String getCode(@Param("nameEn") String nameEn,@Param("name") String name);

    @Select("select c.code as id,c.name from dict_common c JOIN dict_common_type t ON c.common_type_id=t.id where t.name_en=#{nameEn}")
    List<DictCommonData> getNames(@Param("nameEn") String nameEn);

    @Select("select c.code as id,t.name_en,c.name from dict_common c JOIN dict_common_type t ON c.common_type_id=t.id")
    List<CommonData> getAll();

    @Select("select * from dict_common_type_view where type_id = #{typeId}")
    List<DictCommonTypeView> getDictCommonData(Integer typeId);

}
