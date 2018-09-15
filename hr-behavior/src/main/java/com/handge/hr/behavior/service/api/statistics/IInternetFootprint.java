package com.handge.hr.behavior.service.api.statistics;


import com.handge.hr.domain.entity.behavior.web.request.statistics.FootPrintParam;

/**
 * Created by DaLu Guo on 2018/5/4.
 */
public interface IInternetFootprint {
    /**
     * 上网足迹查询
     *
     * @param footPrintParam
     * @return
     */
    public Object listInternetFootprint(FootPrintParam footPrintParam);
}
