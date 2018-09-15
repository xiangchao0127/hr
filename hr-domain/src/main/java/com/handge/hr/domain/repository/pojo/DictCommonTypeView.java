package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="dict_common_type_view")
public class DictCommonTypeView {
    @Id
    private Integer id;

    private String name;

    private String nameCh;

    private String nameEn;

    private String isActive;

    private Integer typeId;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "DictCommonTypeView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameCh='" + nameCh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", isActive='" + isActive + '\'' +
                ", typeId=" + typeId +
                ", code='" + code + '\'' +
                '}';
    }
}
