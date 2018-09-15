package com.handge.hr.behavior.service.api.statistics;


import com.handge.hr.domain.entity.behavior.web.request.statistics.NonWorkingTimeSoltParam;

/**
 * Created by DaLu Guo on 2018/5/4.
 */


public interface INonWorkingTimeSolt {


    /**
     * 工作无关上网时段分布
     *
     * @return
     */
    public Object listNonWorkingTimeSolt(NonWorkingTimeSoltParam nonWorkingTimeSoltParam);
}
