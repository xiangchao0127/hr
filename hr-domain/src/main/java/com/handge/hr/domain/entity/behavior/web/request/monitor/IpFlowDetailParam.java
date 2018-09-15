package com.handge.hr.domain.entity.behavior.web.request.monitor;


import com.handge.hr.domain.entity.base.web.request.PageParam;

import java.io.Serializable;

/**
 * Created by DaLu Guo on 2018/6/1.
 */
public class IpFlowDetailParam extends PageParam implements Serializable {

    /**
     * ip地址
     */
    // TODO: 2018/6/1 判断
    private String Ip;

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    @Override
    public String toString() {
        return "IpFlowDetailParam{" +
                "Ip='" + Ip + '\'' +
                '}';
    }
}
