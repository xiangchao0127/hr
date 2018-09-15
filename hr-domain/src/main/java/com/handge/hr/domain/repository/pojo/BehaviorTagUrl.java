package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "behavior_tag_url")
public class BehaviorTagUrl {

    @Id
    private Integer appId;

    private String appDomainName;

    private String appName;

    private String appCategory;

    private String webUrl;

    private String appAssociation;

    private String siteTagSet;

    private String appDesc;

    private String keyword2;

    private String keyword3;

    private String versionNumber;

    private String handgeClass;

    private String basicClass;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppDomainName() {
        return appDomainName;
    }

    public void setAppDomainName(String appDomainName) {
        this.appDomainName = appDomainName == null ? null : appDomainName.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppCategory() {
        return appCategory;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory == null ? null : appCategory.trim();
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl == null ? null : webUrl.trim();
    }

    public String getAppAssociation() {
        return appAssociation;
    }

    public void setAppAssociation(String appAssociation) {
        this.appAssociation = appAssociation == null ? null : appAssociation.trim();
    }

    public String getSiteTagSet() {
        return siteTagSet;
    }

    public void setSiteTagSet(String siteTagSet) {
        this.siteTagSet = siteTagSet == null ? null : siteTagSet.trim();
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc == null ? null : appDesc.trim();
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2 == null ? null : keyword2.trim();
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3 == null ? null : keyword3.trim();
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
    }

    public String getHandgeClass() {
        return handgeClass;
    }

    public void setHandgeClass(String handgeClass) {
        this.handgeClass = handgeClass == null ? null : handgeClass.trim();
    }

    public String getBasicClass() {
        return basicClass;
    }

    public void setBasicClass(String basicClass) {
        this.basicClass = basicClass == null ? null : basicClass.trim();
    }
}