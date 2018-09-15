package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "privilege_permission")
public class PrivilegePermission {
    @Id
    private String id;

    private String buttonId;

    private String url;

    private String pattern;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId == null ? null : buttonId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern == null ? null : pattern.trim();
    }

    @Override
    public String toString() {
        return "PrivilegePermission{" +
                "id='" + id + '\'' +
                ", buttonId='" + buttonId + '\'' +
                ", url='" + url + '\'' +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}