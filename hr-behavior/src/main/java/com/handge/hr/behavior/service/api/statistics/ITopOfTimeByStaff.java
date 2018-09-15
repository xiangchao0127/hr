package com.handge.hr.behavior.service.api.statistics;


import com.handge.hr.domain.entity.behavior.web.request.statistics.TimeByStaffDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.statistics.TopOfTimeByStaffParam;

/**
 * @author liuqian
 */
public interface ITopOfTimeByStaff {
    /**
     * 工作无关公司上网人均时长TOP
     * @param topOfTimeByStaffParam
     * @return
     */
    public Object listTopOfTimeByStaff(TopOfTimeByStaffParam topOfTimeByStaffParam);

    /**
     * 工作无关公司上网人均时长详情
     * @param timeByStaffDetailParam
     * @return
     */
    public Object listTimeByStaffDetail(TimeByStaffDetailParam timeByStaffDetailParam);
}
