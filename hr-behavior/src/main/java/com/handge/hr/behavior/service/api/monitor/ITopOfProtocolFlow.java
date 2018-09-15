package com.handge.hr.behavior.service.api.monitor;


import com.handge.hr.domain.entity.behavior.web.request.monitor.ProtocolFlowDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.monitor.TopOfProtocolFlowParam;

/**
 * Created by DaLu Guo on 2018/5/4.
 */
public interface ITopOfProtocolFlow {
    /**
     * 协议流量排名
     *
     * @param
     * @return
     */
    public Object listTopOfProtocolFlow(TopOfProtocolFlowParam topOfProtocolFlowParam);

    /**
     * 协议流量排名详情
     *
     * @return
     */
    public Object listProtocolFlowDetail(ProtocolFlowDetailParam protocolFlowDetailParam);
}
