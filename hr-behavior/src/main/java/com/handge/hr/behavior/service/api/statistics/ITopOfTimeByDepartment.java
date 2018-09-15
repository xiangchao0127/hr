package com.handge.hr.behavior.service.api.statistics;


import com.handge.hr.domain.entity.behavior.web.request.statistics.TimeByDepartmentDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.statistics.TopOfTimeByDepartmentParam;

/**
 * @author liuqian
 */
public interface ITopOfTimeByDepartment {
    /**
     * 工作无关部门上网人均时长TOP
     * @param topOfTimeByDepartmentParam
     * @return
     */
    public Object listTopOfTimeByDepartment(TopOfTimeByDepartmentParam topOfTimeByDepartmentParam);

    /**
     * 工作无关部门上网人均时长详情
     * @param timeByDepartmentDetailParam
     * @return
     */
    public Object listTimeByDepartmentDetail(TimeByDepartmentDetailParam timeByDepartmentDetailParam);
}
