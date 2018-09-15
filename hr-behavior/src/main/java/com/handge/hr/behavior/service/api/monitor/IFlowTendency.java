package com.handge.hr.behavior.service.api.monitor;


import com.handge.hr.domain.entity.behavior.web.request.monitor.FlowTendencyParam;

/**
 * Created by DaLu Guo on 2018/5/4.
 */
public interface IFlowTendency {
    /**
     * 流量趋势
     *
     * @return
     */
    public Object listFlowTendency(FlowTendencyParam flowTendencyParam);
}
