package com.handge.hr.behavior.service.api.monitor;


import com.handge.hr.domain.entity.behavior.web.request.monitor.FlowTrendParam;

/**
 * Created by DaLu Guo on 2018/5/4.
 *
 * @author Liujuhao
 * @date 2018/5/17
 */
public interface IFlowTrend {
    /**
     * 流量走势
     *
     * @return
     */
    public Object flowTrend(FlowTrendParam flowTrendParam);
}
