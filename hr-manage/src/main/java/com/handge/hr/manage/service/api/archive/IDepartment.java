package com.handge.hr.manage.service.api.archive;

import com.handge.hr.domain.entity.manage.web.request.archive.DeleteDepartmentParam;
import com.handge.hr.domain.entity.manage.web.request.archive.DepartmentParam;
import com.handge.hr.domain.entity.manage.web.request.archive.ListMemberParam;
import com.handge.hr.domain.entity.manage.web.request.employee.MoveEmployeeParam;
import org.springframework.stereotype.Component;

/**
 * Created by DaLu Guo on 2018/7/31.
 */
@Component
public interface IDepartment {

    /**
     * 显示公司部门架构
     *
     * @return
     */
    Object showDepartmentStructure();

    /**
     * 部门列表
     *
     * @return
     */
    Object listDepartment();

    /**
     * 新增部门
     *
     * @param departmentParam
     * @return
     */
    Object addDepartment(DepartmentParam departmentParam);

    /**
     * 编辑部门
     *
     * @param departmentParam
     * @return
     */
    Object alterDepartment(DepartmentParam departmentParam);

    /**
     * 部门成员列表
     *
     * @param listMemberParam
     * @return
     */
    Object listMember(ListMemberParam listMemberParam);

    /**
     * 删除部门
     *
     * @param deleteDepartmentParam
     * @return
     */
    Object deleteDepartment(DeleteDepartmentParam deleteDepartmentParam);

    /**
     * 获取各部门及其部门成员
     * @return
     */
    Object findDepartmentAndMembers();

    /**
     * 人员调动
     * @param moveEmployeeParam
     * @return
     */
    Object moveEmployee(MoveEmployeeParam moveEmployeeParam);
}
