package com.handge.hr.behavior.service.api.statistics;


import com.handge.hr.common.enumeration.base.TimeSectionEnum;
import com.handge.hr.domain.entity.behavior.web.request.statistics.NonWorkingTrendParam;
import com.handge.hr.domain.entity.behavior.web.response.UserContext;

/**
 * Created by DaLu Guo on 2018/5/4.
 */
public interface INonWorkingTrend {

    /**
     * 工作无关上网趋势
     *
     * @param section
     * @param context
     * @param threshold
     * @return
     */

    @Deprecated
    default Object listNonWorkingTrendOld(TimeSectionEnum section, UserContext context, double threshold) {
        System.out.println("该接口已废弃");
        return null;
    }

    Object listNonWorkingTrend(NonWorkingTrendParam nonWorkingTrendParam);
}
