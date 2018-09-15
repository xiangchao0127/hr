package com.handge.hr.domain.entity.manage.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * Created by MaJianfu on 2018/7/27.
 */
public class DictCommonExcel implements Serializable {

    @Excel(name ="TYPE_NAME_CH")
    private String typeNameCh;
    @Excel(name ="TYPE_NAME_EN")
    private String typeNameEn;
    @Excel(name ="NAME")
    private String name;

    public String getTypeNameCh() {
        return typeNameCh;
    }

    public void setTypeNameCh(String typeNameCh) {
        this.typeNameCh = typeNameCh;
    }

    public String getTypeNameEn() {
        return typeNameEn;
    }

    public void setTypeNameEn(String typeNameEn) {
        this.typeNameEn = typeNameEn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
