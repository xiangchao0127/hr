package com.handge.hr.domain.repository.mapper;

import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.pojo.BehaviorEntityDeviceBasic;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by DaLu Guo on 2018/8/8.
 */
@Mapper
public interface BehaviorEntityDeviceBasicMapper extends CommonMapper<BehaviorEntityDeviceBasic,Integer> {
    @ResultType(java.util.Map.class )
    @Select("SELECT dep.name AS department_name,dev.static_ip AS static_ip FROM entity_employee emp " +
            "JOIN entity_account cou ON cou.employee_id =emp.ID JOIN behavior_entity_device_basic dev ON " +
            "dev.account_id = cou.ID JOIN entity_department AS dep ON emp.department_id = dep.ID")
    @MapKey("static_ip")
    public Map<String, Map<String, String>> getStaticIpDepartment();


    /**
     * 通过员工工号查找该部门的所有ip
     * @param number
     * @return
     */
    @Select("SELECT dev.static_ip\n" +
            "FROM entity_employee emp\n" +
            "JOIN entity_account cou ON cou.employee_id = emp.id\n" +
            "JOIN behavior_entity_device_basic dev ON dev.account_id = cou.id\n" +
            "WHERE emp.department_id = (\n" +
            "SELECT department_id\n" +
            "FROM entity_employee\n" +
            "WHERE job_number = #{number})\n")
    List<String> getDepartmentIpsByNo(String number);

    /**
     * 通过员工工号查找对应的IP
     * @param number
     * @return
     */
    @Select("SELECT static_ip AS ip FROM entity_employee emp\n" +
            "JOIN entity_account cou ON cou.employee_id = emp.id\n" +
            "JOIN behavior_entity_device_basic dev ON dev.account_id = cou.\"id\"\n" +
            "WHERE emp.job_number = #{number}")
    List<String> getIpsByNumber(String number);

    /**
     * 获取员工姓名及员工ip集合、员工姓名及部门工号
     */
    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetEmpNameIpDept")
    @ResultType(HashMap.class)
    List<Map<String,Object>> getEmpNameIpDept(String number,String name,String department);

    class UserSqlBuilder{
        public static String buildGetEmpNameIpDept(String number,String name,String department){
            StringBuilder sb = new StringBuilder();
            if (StringUtils.notEmpty(number)) {
                sb.append(" AND t3.number = '").append(number).append("'\n");
            }
            if (StringUtils.notEmpty(name)) {
                sb.append(" AND t3.name like '%").append(name).append("%'").append("\n");
            }
            if (StringUtils.notEmpty(department)) {
                sb.append(" AND t4.name like '%").append(department).append("%'").append("\n");
            }
            String sql = "SELECT t3.name,t3.job_number,t4.name as department,ARRAY_AGG(t1.static_ip) AS ip_list\n" +
                    "FROM behavior_entity_device_basic t1\n" +
                    "LEFT JOIN entity_account t2 ON t1.account_id = t2.id\n" +
                    "LEFT JOIN entity_employee t3 ON t2.employee_id = t3.id\n" +
                    "LEFT JOIN entity_department t4 ON t3.department_id = t4.id\n" +
                    "WHERE t3.leavedate is NULL\n" + sb +
                    "GROUP BY\n" +
                    "t3.name,\n" +
                    "t4.name,\n" +
                    "\tt3.job_number";
            return sql;
        }
    }
}
