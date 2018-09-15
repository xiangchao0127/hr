package com.handge.hr.manage.service.api.archive;


import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.manage.web.request.employee.EmployeeParam;
import com.handge.hr.domain.entity.manage.web.request.archive.InformationParam;
import com.handge.hr.domain.entity.manage.web.response.archive.InformationRes;

import java.util.ArrayList;

public interface IEmployee {
    /**
     * 添加员工
     * @param employeeParam
     * @return
     */
    Integer addEmployee(EmployeeParam employeeParam);

    /**
     * 删除员工
     * @param ids
     * @return
     */
    Integer deleteEmployeeById(ArrayList<String> ids);

    /**
     * 更新员工
     * @param employeeParam
     * @return
     */
    Integer updateEmployee(EmployeeParam employeeParam);

    /**
     * 员工详情
     * @param id
     * @return
     */
    InformationRes getEmployeeDetails(String id);

    /**
     * 查询员工信息
     * @return
     */
    PageResults getInformation(InformationParam information);
}
