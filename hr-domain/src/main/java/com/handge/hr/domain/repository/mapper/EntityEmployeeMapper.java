package com.handge.hr.domain.repository.mapper;

import com.handge.hr.common.enumeration.behavior.EmployeeStatusEnum;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.behavior.web.response.common.UserInfo;
import com.handge.hr.domain.entity.behavior.web.request.statistics.FootPrintParam;
import com.handge.hr.domain.entity.manage.excel.EmployeeExcel;
import com.handge.hr.domain.entity.manage.web.request.archive.InformationParam;
import com.handge.hr.domain.entity.manage.web.response.archive.EmployeeSampleRes;
import com.handge.hr.domain.entity.manage.web.response.archive.InformationRes;
import com.handge.hr.domain.repository.pojo.EntityEmployee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface EntityEmployeeMapper extends CommonMapper<EntityEmployee, String> {

    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetInformation")
    List<InformationRes> getInformation(InformationParam information);

    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetFootPrintDetails")
    @ResultType(HashMap.class)
    List<Map<String, String>> getFootPrintDetails(FootPrintParam footPrintParam);

    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetInformation")
    List<EmployeeExcel> getExcelInformation(InformationParam information);


    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersByName")
    @Results({
            @Result(property = "departmentName", column = "departmentName"),
            @Result(property = "departmentId", column = "departmentId")
    }
    )
    List<EntityEmployee> selectCustomer(@Param("name") String name, @Param("departmentName") String departmentName, @Param("departmentId") String departmentId);

    @Select("select name from entity_employee where id = #{id}")
    String getEmployeeNameById(String id);

//    @Select("SELECT\n" +
//            "e.id,e.name\n" +
//            "FROM\n" +
//            "\tentity_m_role_to_account mra\n" +
//            "JOIN entity_account A ON A . ID = mra.account_id\n" +
//            "JOIN entity_role r ON r. ID = mra.role_id\n" +
//            "JOIN entity_employee e ON e. ID = A .employee_id\n" +
//            "where r.name = #{roleName}")
    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetEmployeesByRoleName")
    List<EmployeeSampleRes> getEmployeesByRoleName(String roleName);

    /**
     * 查询在岗人数
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM entity_employee e WHERE e.job_status != #{status}")
    Integer countTotalNumberOfEmployeesOnGuard(@Param("status") int status);

    /**
     * 查询各个部门人数(在岗)
     */
    @Select("SELECT d.name, COUNT (*) AS sumPerson FROM entity_employee e JOIN entity_department d ON e.department_id = d.ID WHERE (e.job_status != 20) GROUP BY d.name;")
    List<HashMap<String, Object>> getNumberOfEmployeesGroupByDept();

    /**
     * 获取所有员工ip及对应的职工编号
     */
    @Select("SELECT dev.static_ip,e.job_number " +
            "FROM entity_employee e " +
            "JOIN entity_account ac ON ac.employee_id = e.id " +
            "JOIN behavior_entity_device_basic dev ON dev.account_id = ac.id")
    List<HashMap<String, String>> getAllEmployeeIpAndNumber();

    /**
     * 通过给定的数据模糊查询员工
     *
     * @return
     */
    @Select("SELECT e.name AS employeeName, " +
            "de.name AS department, " +
            "e.job_number as number " +
            "FROM entity_employee e " +
            "INNER JOIN entity_department de ON de.id = e.department_id " +
            "WHERE e.name LIKE '%${user}%' " +
            "OR e.job_number LIKE '%${user}%' " +
            "OR de.name LIKE '%${user}%'")
    List<UserInfo> listUserInfoByName(@Param("user") String user);

    /**
     * 获取部门所有ip 传null获取所有部门员工ip（在岗）
     *
     * @param departmentName 部门名称
     * @return
     */
    @ResultType(java.util.Map.class)
    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetEmployeeIpsByDept")
    @MapKey("name")
    List<HashMap<String, String[]>> getEmployeeIpsMap(String departmentName);

    /**
     * 根据条件获取IP的集合
     */
    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetIps")
    List<String> getIps(String department, String name, String number);


    class UserSqlBuilder {
        public static String buildGetEmployeesByRoleName(String roleName) {
            String sql = "SELECT\n" +
                    "DISTINCT e.id,e.name,e.job_number as number\n" +
                    "FROM\n" +
                    "\tentity_m_role_to_account mra\n" +
                    "JOIN entity_account A ON A . ID = mra.account_id\n" +
                    "JOIN entity_role r ON r. ID = mra.role_id\n" +
                    "JOIN entity_employee e ON e. ID = A .employee_id\n" +
                    "WHERE 1 = 1\n";
            if (StringUtils.notEmpty(roleName)) {
                sql += "AND r.name = #{roleName}";
            }
            return sql;
        }

        public static String buildGetInformation(InformationParam information) {
            return new SQL() {{
                SELECT("emp.job_number as jobNumber,emp.name as name,dept.name as department,pro.professional_level as professionalLevel, \n" +
                        "job.name as position,emp.hiredate as hiredate,emp.job_status as JOB_STATUS, \n" +
                        "emp.birthday as birthday,emp.GENDER as GENDER,emp.nationality as nationality,emp.native_place as nativePlace, \n" +
                        "emp.identity_card as identityCard,emp.address as address,emp.marital_status as MARITAL_STATUS,emp.children_status as CHILDREN_STATUS,\n" +
                        "emp.graduate_from as graduateFrom,emp.professional as professional,emp.EDUCATION as EDUCATION,emp.political_status as POLITICAL_STATUS," +
                        "emp.mobile as mobile,emp.remark as remark");
                FROM("entity_employee emp");
                JOIN("entity_department dept on emp.department_id=dept.id");
                JOIN("entity_job_professional_level pro on emp.job_professional_level_id =pro.id");
                JOIN("entity_job_position job on emp.job_position_id=job.id");
                WHERE("1=1");
                if (StringUtils.notEmpty(information.getJobNumber())) {
                    AND();
                    WHERE("emp.job_number = '" + information.getJobNumber() + "'");
                }
                if (StringUtils.notEmpty(information.getName())) {
                    AND();
                    WHERE("emp.name =  '" + information.getName() + "'");
                }
                if (StringUtils.notEmpty(information.getDepartment())) {
                    AND();
                    WHERE("dept.name = '" + information.getDepartment() + "'");
                }
                if (StringUtils.notEmpty(information.getProfessionalLevel())) {
                    AND();
                    WHERE("pro.professional_level = '" + information.getProfessionalLevel() + "'");
                }
                if (StringUtils.notEmpty(information.getPosition())) {
                    AND();
                    WHERE("job.name = '" + information.getPosition() + "'");
                }
                if (StringUtils.notEmpty(information.getHireDate())) {
                    AND();
                    WHERE("emp.hiredate ='" + information.getHireDate() + "'");
                }
                if (StringUtils.notEmpty(information.getJobStatus())) {
                    AND();
                    WHERE("emp.job_status = '" + information.getJobStatus() + "'");
                }
                if (StringUtils.notEmpty(information.getBirthday())) {
                    AND();
                    WHERE("emp.birthday = '" + information.getBirthday() + "'");
                }
                if (StringUtils.notEmpty(information.getGender())) {
                    AND();
                    WHERE("emp.GENDER ='" + information.getGender() + "'");
                }
                if (StringUtils.notEmpty(information.getNationality())) {
                    AND();
                    WHERE("emp.nationality ='" + information.getNationality() + "'");
                }
                if (StringUtils.notEmpty(information.getNativePlace())) {
                    AND();
                    WHERE("emp.native_place ='" + information.getNativePlace() + "'");
                }
                if (StringUtils.notEmpty(information.getIdentityCard())) {
                    AND();
                    WHERE("emp.identity_card ='" + information.getIdentityCard() + "'");
                }
                if (StringUtils.notEmpty(information.getAddress())) {
                    AND();
                    WHERE("emp.address ='" + information.getAddress() + "'");
                }
                if (StringUtils.notEmpty(information.getMaritalStatus())) {
                    AND();
                    WHERE("emp.marital_status ='" + information.getMaritalStatus() + "'");
                }
                if (StringUtils.notEmpty(information.getChildrenStatus())) {
                    AND();
                    WHERE("emp.children_status = '" + information.getChildrenStatus() + "'");
                }
                if (StringUtils.notEmpty(information.getGraduateFrom())) {
                    AND();
                    WHERE("emp.graduate_from ='" + information.getGraduateFrom() + "'");
                }
                if (StringUtils.notEmpty(information.getProfessional())) {
                    AND();
                    WHERE("emp.professional = '" + information.getProfessional() + "'");
                }
                if (StringUtils.notEmpty(information.getEducation())) {
                    AND();
                    WHERE("emp.EDUCATION = '" + information.getEducation() + "'");
                }
                if (StringUtils.notEmpty(information.getPoliticalStatus())) {
                    AND();
                    WHERE("emp.political_status =  '" + information.getPoliticalStatus() + "'");
                }
                if (StringUtils.notEmpty(information.getMobile())) {
                    AND();
                    WHERE("emp.mobile = '" + information.getMobile() + "'");
                }
            }}.toString();
        }

        public static String buildGetUsersByName(@Param("name") final String name, @Param("departmentName") final String departmentName, @Param("departmentId") final String departmentId) {
            return new SQL() {{
                SELECT("e.id,e.name");
                FROM("entity_employee e");
                JOIN("entity_department d on e.department_id = d.id");
                WHERE("1=1");
                if (name != null) {
                    AND();
                    WHERE("e.name like #{name}");
                }
                AND();
                if (StringUtils.notEmpty(departmentName)) {
                    WHERE("d.name = #{departmentName}");
                }
                AND();
                if (StringUtils.notEmpty(departmentId)) {
                    WHERE("e.department_id = #{departmentId}");
                }
                ORDER_BY("e.id");
            }}.toString();
        }

        public static String buildGetEmployeeIpsByDept(@Param("departmentName") final String departmentName) {
            return new SQL() {{
                SELECT("dep.name,ARRAY_AGG (dev.static_ip)");
                FROM("entity_employee emp");
                JOIN("entity_account acc ON acc.employee_id = emp.id");
                JOIN("behavior_entity_device_basic dev ON dev.account_id = acc.id");
                INNER_JOIN("entity_department dep ON emp.department_id = dep.id");
                WHERE("emp.job_status != " + EmployeeStatusEnum.QUIT.getStatus());
                if (StringUtils.notEmpty(departmentName)) {
                    AND();
                    WHERE("dep.name like " + "'%" + departmentName + "%'");
                }
                GROUP_BY("dep.name");
            }}.toString();
        }

        public static String buildGetFootPrintDetails(FootPrintParam footPrintParam) {
            return new SQL() {{
                SELECT("dep.name AS department_name,dev.static_ip AS static_ip,emp.name AS name,emp.job_number AS number");
                FROM("entity_employee emp");
                JOIN("entity_account cou ON cou.employee_id = emp.id");
                JOIN("behavior_entity_device_basic dev ON dev.account_id = cou.id");
                JOIN("entity_department AS dep ON emp.department_id = dep.id");
                WHERE("1=1");
                if (StringUtils.notEmpty(footPrintParam.getName())) {
                    String[] split = footPrintParam.getName().split("\\|", -1);
                    if (StringUtils.notEmpty(split[0])) {
                        AND();
                        WHERE("emp.name= '" + split[0] + "'");
                    }
                    if (StringUtils.notEmpty(split[1])) {
                        AND();
                        WHERE("dep.name= '" + split[1] + "'");
                    }
                    int splitValue=2;
                    if (StringUtils.notEmpty(split[splitValue])) {
                        AND();
                        WHERE("emp.job_number= '" + split[2] + "'");
                    }
                }
                String ipValue="全部";
                if (StringUtils.notEmpty(footPrintParam.getIp()) && !ipValue.equals(footPrintParam.getIp())) {
                    AND();
                    WHERE("dev.static_ip= '" + footPrintParam.getIp() + "'");
                }
            }}.toString();
        }

        public static String buildGetIps(String department, String name, String number) {
            String sql = "\n" +
                    "SELECT\n" +
                    "\tdev.static_ip AS ip\n" +
                    "FROM\n" +
                    "\tentity_employee emp\n" +
                    "JOIN entity_account cou ON cou.employee_id = emp.id\n" +
                    "JOIN behavior_entity_device_basic dev ON dev.account_id = cou.id\n" +
                    "INNER JOIN entity_department dep ON emp.department_id = dep.id\n" +
                    "AND emp. job_status != " + EmployeeStatusEnum.QUIT.getStatus() + "\n";
            if (StringUtils.notEmpty(department)) {
                sql += "AND dep.name LIKE '%" + department + "%' \n";
            }
            if (StringUtils.notEmpty(name)) {
                sql += "AND emp.`name` LIKE '%" + name + "%'\n";
            }
            if (StringUtils.notEmpty(number)) {
                sql += "AND emp.job_number = " + number + ";";
            }
            return sql;
        }
    }

    /**
     * 查询员工信息
     *
     * @param number 工号
     * @return
     */
    @Select("SELECT e.\"name\", e.job_number, e.job_status, p.\"name\" post,\n" +
            "t.\"name\" positional_titles,d.\"name\" department_name\n" +
            "FROM entity_employee e\n" +
            "INNER JOIN entity_department d ON d.\"id\" = e.department_id\n" +
            "INNER JOIN entity_job_position p ON e.job_position_id = p.\"id\"\n" +
            "INNER JOIN dict_job_title t ON e.job_title_id = t.\"id\"\n" +
            "WHERE e.job_number = #{number}")
    Map<String, Object> getEmployeeInfoByNumber(String number);

    /**
     * 查询某部门员工信息
     *
     * @param depName
     * @return
     */
    @Select("SELECT t1.\"name\",t1.job_number,t2.\"name\" department_name\n" +
            "From entity_employee t1\n" +
            "JOIN entity_department t2 ON t1.department_id= t2.\"id\"\n" +
            "WHERE t2.\"name\" = #{depName}\n" +
            "AND t1.job_status!='24'")
    @ResultType(HashMap.class)
    List<Map<String, Object>> getEmployeeInfoByDepName(String depName);

    /**
     * 查询员工姓名和ip
     *
     * @return
     */
    @Select("SELECT e.\"name\" ,dev.static_ip ip\n" +
            "FROM entity_employee e\n" +
            "JOIN entity_account ac ON ac.employee_id = e.id\n" +
            "JOIN behavior_entity_device_basic dev ON dev.account_id = ac.id")
    List<Map<String, Object>> getNameAndIp();

    /**
     * 根据角色名称查询员工列表
     * @param roleName
     * @return
     */
    @Select("SELECT\n" +
            "\temp.id,emp.name,emp.job_number\n" +
            "FROM\n" +
            "\tentity_account acc\n" +
            "JOIN entity_m_role_to_account macc ON acc. ID = macc.account_id\n" +
            "join entity_employee emp on emp.id = acc.employee_id \n" +
            "join entity_role rol on rol.id = macc.role_id\n" +
            "WHERE\n" +
            "\trol.name = #{roleName}")
    List<EmployeeSampleRes> getEmployeesByRoleNames(String roleName);

    /**
     * 查询某月月初在职人数
     * @param firstDayOfMonth
     * @return
     */
    @Select("SELECT\n" +
            "\thirecount - leavecount startstaff \n" +
            "FROM\n" +
            "\t( SELECT COUNT( * ) hirecount FROM entity_employee WHERE hiredate <= #{firstDayOfMonth} ) t1,\n" +
            "\t( SELECT COUNT( * ) leavecount FROM entity_employee WHERE leavedate <= #{firstDayOfMonth} ) t2")
    int getEarlyMonthStaffCount(Date firstDayOfMonth);

    /**
     * 查询某月月末在职人数
     * @param lastDayOfMonth
     * @return
     */
    @Select("SELECT\n" +
            "\thirecount - leavecount endstaff \n" +
            "FROM\n" +
            "\t( SELECT COUNT( * ) hirecount FROM entity_employee WHERE hiredate <= #{lastDayOfMonth} ) t1,\n" +
            "\t( SELECT COUNT( * ) leavecount FROM entity_employee WHERE leavedate <= #{lastDayOfMonth} ) t2")
    int getEndOfMonthStaffCount(Date lastDayOfMonth);

    /**
     * 获取公司成立日期
     * @return
     */
    @Select("SELECT min(hiredate) FROM entity_employee")
    String getEstablishedDate();
}
