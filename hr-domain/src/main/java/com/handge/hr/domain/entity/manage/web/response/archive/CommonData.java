package com.handge.hr.domain.entity.manage.web.response.archive;

/**
 * create by xc in 2018/09/04
 */
public class CommonData {
    private String id;
    private String nameEn;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DictCommon{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
