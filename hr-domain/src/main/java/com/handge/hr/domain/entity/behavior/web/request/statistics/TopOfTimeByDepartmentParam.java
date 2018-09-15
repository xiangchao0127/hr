package com.handge.hr.domain.entity.behavior.web.request.statistics;


import com.handge.hr.domain.entity.base.web.request.TimeParam;

import java.io.Serializable;

/**
 * @author liuqian
 */
public class TopOfTimeByDepartmentParam extends TimeParam implements Serializable {

    /**
     * 排名数量（1,2,3,4,5...）
     */
    private int n = 5;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "TopOfTimeByDepartmentParam{" +
                "n=" + n +
                '}';
    }
}
