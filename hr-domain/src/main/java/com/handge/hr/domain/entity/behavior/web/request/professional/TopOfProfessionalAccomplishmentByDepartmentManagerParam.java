package com.handge.hr.domain.entity.behavior.web.request.professional;


import com.handge.hr.domain.entity.base.web.request.TimeParam;

import javax.validation.constraints.Digits;
import java.io.Serializable;

/**
 * Created by MaJianfu on 2018/6/12.
 */
public class TopOfProfessionalAccomplishmentByDepartmentManagerParam extends TimeParam implements Serializable {
    /**
     * 排名数量
     */
    @Digits(integer = 2, fraction = 20,message="排名数量")
    private int n = 5;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "TopOfNonWorkingDepartmentParam{" +
                "n=" + n +
                '}';
    }
}
