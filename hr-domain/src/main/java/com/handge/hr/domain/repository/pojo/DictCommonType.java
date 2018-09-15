package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="dict_common_type")
public class DictCommonType {
    @Id
    private Integer id;

    private String nameCh;

    private String nameEn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh == null ? null : nameCh.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    @Override
    public String toString() {
        return "DictCommonType{" +
                "id=" + id +
                ", nameCh='" + nameCh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                '}';
    }
}