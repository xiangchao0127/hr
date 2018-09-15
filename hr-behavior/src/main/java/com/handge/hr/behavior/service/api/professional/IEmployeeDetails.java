package com.handge.hr.behavior.service.api.professional;


import com.handge.hr.domain.entity.behavior.web.request.professional.EmployeeDetailsParam;

/**
 * Created by DaLu Guo on 2018/6/12.
 */


public interface IEmployeeDetails {
    /**
     * 员工详情
     */
    Object getEmployeeDetails(EmployeeDetailsParam employeeDetailsParam);
}
