package com.handge.hr.domain.entity.behavior.web.request.monitor;


import com.handge.hr.domain.entity.base.web.request.PageParam;

import java.io.Serializable;

/**
 * Created by DaLu Guo on 2018/6/1.
 */
public class ProtocolFlowDetailParam extends PageParam implements Serializable {

    /**
     * 协议名称
     */
    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "ProtocolFlowDetailParam{" +
                "protocol='" + protocol + '\'' +
                '}';
    }
}
