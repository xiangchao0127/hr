package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "behavior_tag_property")
public class BehaviorTagProperty {
    @Id
    private Integer id;

    private String tagName;

    private String property;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }
}