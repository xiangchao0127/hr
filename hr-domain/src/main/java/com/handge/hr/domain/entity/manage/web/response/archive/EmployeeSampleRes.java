package com.handge.hr.domain.entity.manage.web.response.archive;

/**
 * create by xc in 2018/09/05
 */
public class EmployeeSampleRes {
    /**
     * 员工id
     */
    private String id;
    /**
     * 员工名称
     */
    private String name;
    /**
     * 员工编号
     */
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "EmployeeSampleRes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
