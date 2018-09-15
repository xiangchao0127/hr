package com.handge.hr.behavior.service.api.monitor;


import com.handge.hr.domain.entity.behavior.web.request.monitor.NonWorkingTimeDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.monitor.TopOfNonWorkingTimeParam;

/**
 * @author liuqian
 */
public interface ITopOfNonWorkingTime {
    /**
     * 工作无关上网时长
     * @param topOfNonWorkingTimeParam
     * @return
     */
    public Object listTopOfNonWorkingTime(TopOfNonWorkingTimeParam topOfNonWorkingTimeParam);

    /**
     * 工作无关上网时长详情
     * @param nonWorkingTimeDetailParam
     * @return
     */
    public Object listNonWorkingTimeDetail(NonWorkingTimeDetailParam nonWorkingTimeDetailParam);
}
