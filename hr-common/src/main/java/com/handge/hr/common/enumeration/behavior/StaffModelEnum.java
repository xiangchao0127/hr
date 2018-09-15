package com.handge.hr.common.enumeration.behavior;

/**
 * Created by MaJianfu on 2018/6/13.
 */
public enum StaffModelEnum {
    //优秀员工
    EXCELLENT_STAFF ("1"),
    //差劲员工
    POOR_STAFF("2");
    private String model;

    private StaffModelEnum(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
}
