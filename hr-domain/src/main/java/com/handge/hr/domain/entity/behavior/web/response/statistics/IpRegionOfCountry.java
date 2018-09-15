package com.handge.hr.domain.entity.behavior.web.response.statistics;

/**
 * @author liuqian
 * @date 2018/8/9
 * @Description:
 */
public class IpRegionOfCountry {
    /**
     * 国家名称
     */
    private String name;
    /**
     * 起始ip
     */
    private String start;
    /**
     * 结束ip
     */
    private String end;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "IpRegionOfCountry{" +
                "name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", END='" + end + '\'' +
                '}';
    }
}
