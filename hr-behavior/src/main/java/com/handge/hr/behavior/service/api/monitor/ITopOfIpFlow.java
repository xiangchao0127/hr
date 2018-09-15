package com.handge.hr.behavior.service.api.monitor;


import com.handge.hr.domain.entity.behavior.web.request.monitor.IpFlowDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.monitor.TopOfIpFlowParam;

/**
 * Created by DaLu Guo on 2018/5/4.
 */
public interface ITopOfIpFlow {

    /**
     * IP流量
     *
     * @return
     */
    public Object listTopOfIpFlow(TopOfIpFlowParam topOfIpFlowParam);

    /**
     * IP流量详情
     *
     * @return
     */
    public Object listIpFlowDetail(IpFlowDetailParam ipFlowDetailParam);
}
