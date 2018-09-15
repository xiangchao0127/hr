package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="behavior_dict_country_basic")
public class BehaviorDictCountryBasic {
    @Id
    private Integer id;

    private String name;

    private String nickName;

    private String capitalGeo;

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
        this.name = name == null ? null : name.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getCapitalGeo() {
        return capitalGeo;
    }

    public void setCapitalGeo(String capitalGeo) {
        this.capitalGeo = capitalGeo == null ? null : capitalGeo.trim();
    }
}