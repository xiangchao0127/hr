package com.handge.hr.domain.entity.manage.web.request.employee;


import java.util.List;

/**
 * @author guodalu
 * @date 2018/8/1
 * @Description:
 */
public class MoveEmployeeParam {

    /**
     * 调出成员id集合
     */
    private List<String> outEmployeeIds;

    /**
     * 调入部门id
     */
    private String inDepartmentId;

    public List<String> getOutEmployeeIds() {
        return outEmployeeIds;
    }

    public void setOutEmployeeIds(List<String> outEmployeeIds) {
        this.outEmployeeIds = outEmployeeIds;
    }

    public String getInDepartmentId() {
        return inDepartmentId;
    }

    public void setInDepartmentId(String inDepartmentId) {
        this.inDepartmentId = inDepartmentId;
    }


    @Override
    public String toString() {
        return "MoveEmployeeParam{" +
                "outEmployeeIds=" + outEmployeeIds +
                ", inDepartmentId='" + inDepartmentId + '\'' +
                '}';
    }
}
