package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="dict_common")
public class DictCommon {
    @Id
    private Integer id;

    private Integer commonTypeId;

    private String name;

    private Short subOrder;

    private Boolean isActive;

    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommonTypeId() {
        return commonTypeId;
    }

    public void setCommonTypeId(Integer commonTypeId) {
        this.commonTypeId = commonTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getSubOrder() {
        return subOrder;
    }

    public void setSubOrder(Short subOrder) {
        this.subOrder = subOrder;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}