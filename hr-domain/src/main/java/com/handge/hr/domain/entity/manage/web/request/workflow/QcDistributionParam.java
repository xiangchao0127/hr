package com.handge.hr.domain.entity.manage.web.request.workflow;

import javax.validation.constraints.NotEmpty;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public class QcDistributionParam {
    /**
     * 项目/任务id
     */
    @NotEmpty
    private String id;
    /**
     * 质检人员
     */
    @NotEmpty
    private String qcPersonId;
    /**
     * 是否跳过QC分配(0:跳过)
     */
    private String isQC;

    public String getIsQC() {
        return isQC;
    }

    public void setIsQC(String isQC) {
        this.isQC = isQC;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQcPersonId() {
        return qcPersonId;
    }

    public void setQcPersonId(String qcPersonId) {
        this.qcPersonId = qcPersonId;
    }
}
