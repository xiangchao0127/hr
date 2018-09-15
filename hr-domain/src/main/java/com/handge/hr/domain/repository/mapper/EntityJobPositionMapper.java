package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.EntityJobPosition;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.archive.FindPositionParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;


@Mapper
public interface EntityJobPositionMapper extends CommonMapper<EntityJobPosition,String> {

    /**
     * 通过岗位名称查询岗位id
     * @param name 岗位名称
     * @return
     */
    @Select("select id from entity_job_position where name=#{name}")
    String getId(String name);

    /**
     * 通过id查询岗位信息
     * @param id id
     * @return
     */
    @Select("select * from entity_job_position where id=#{id}")
    EntityJobPosition getPositionById(String id);

    /**
     * 通过上级id查询岗位信息
     * @param higherPositionId
     * @return
     */
    @Select("select * from entity_job_position where higher_position_id=#{higherPositionId}")
    EntityJobPosition getPositionByHigherPositionId(String higherPositionId);


    /**
     * 查询岗位（模糊查询）
     * @param findPositionParam
     * @return
     */
    @SelectProvider(type = PositionSqlBuilder.class,method = "buildGetPositions")
    List<EntityJobPosition> selectPositions(FindPositionParam findPositionParam);

    class PositionSqlBuilder{
        public static String buildGetPositions(FindPositionParam findPositionParam){
            return new SQL(){
                {
                    SELECT("e.gmt_create as createTime,e.gmt_modified as modifyTime,e.name as name,"+
                            "e.description as description,e.id as id,d.name as status,e2.name as higherPositionName");
                    FROM("entity_job_position e");
                    LEFT_OUTER_JOIN("entity_job_position e2 on e.higher_position_id = e2.id");
                    LEFT_OUTER_JOIN("dict_common d ON d.id = e.status");
                    WHERE("1=1");
                    if(StringUtils.notEmpty(findPositionParam.getName())){
                        AND();
                        WHERE("e.name like CONCAT('%',#{name},'%')");
                    }
                    if(StringUtils.notEmpty(findPositionParam.getDescription())){
                        AND();
                        WHERE("e.description like CONCAT('%',#{description},'%')");
                    }
                    if(StringUtils.notEmpty(findPositionParam.getStatus())){
                        AND();
                        WHERE("e.status like CONCAT('%',#{status},'%')");
                    }
                    if(StringUtils.notEmpty(findPositionParam.getHigherPositionName())){
                        AND();
                        WHERE("e2.name like CONCAT('%',#{higherPositionName},'%')");
                    }
                }
            }.toString();
        }
    }
}
