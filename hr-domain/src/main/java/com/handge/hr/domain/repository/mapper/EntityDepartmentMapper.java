package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.entity.behavior.web.response.professional.ProfessionalAccomplishmentByDepartmentManager;
import com.handge.hr.domain.repository.pojo.EntityDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntityDepartmentMapper extends CommonMapper<EntityDepartment,String>{

    @Select("select id from entity_department where name = #{name}")
    String getId(String name);

    @Select("select name from entity_department where id = #{id}")
    String getDepartmentNameById(String id);

    @Select("select header_employee_id from entity_department where id = #{id}")
    String getDepartmentEmployeeById(String id);

    @Select("select header_employee_id from entity_department d JOIN workflow_qc q on q.department_id=d.id WHERE q.project_type_id=#{type}")
    String getDepartmentEmployeeIdByTypeId(String type);

    /**
     * 获取某个部门id的下级部门集合
     * @param departmentId
     * @return
     */
    @Select("select id from entity_department where higher_department_id = #{departmentId}")
    List<String> getLowerDepartmentList(String departmentId);

    /*@Delete("delete from entity_department where id='94e7f41283a444899dead9c0f3e21fa0'")
    void delete();*/

    /**
     * 通过员工工号获得员工姓名
     * @param departmentName
     * @return
     */
    @Select("SELECT e.job_number\n" +
            "FROM entity_department d \n" +
            "INNER JOIN entity_employee e ON e.department_id = d.id \n" +
            "where d.\"name\" = #{departmentName}")
    List<String> getNumbersByDepartmentName(String departmentName);

    /**
     * 通过员工工号查找部门名称及部门人数
     * @param number
     * @param status
     * @return
     */
    @Select("SELECT d.\"name\" ,COUNT(*)\n" +
            "FROM entity_employee e \n" +
            "INNER JOIN entity_department d on e.department_id = d.\"id\" \n" +
            "WHERE e.department_id = \n" +
            "(SELECT e.department_id FROM entity_employee e WHERE e.job_number = #{number}) \n" +
            "AND e.job_status != #{status}\n" +
            "GROUP BY d.\"name\"")
    Map<String,Object> getDepartmentCountByNo(@Param("number") String number, @Param("status") Integer status);

    /**
     * 查询部门信息
     * @return
     */
    @Select("select d.\"name\" department,e.\"name\",e.job_number number\n" +
            "from entity_department d\n" +
            "inner join entity_employee e on d.header_employee_id = e.\"id\"")
    List<ProfessionalAccomplishmentByDepartmentManager> getDepartmentInfo();
}
