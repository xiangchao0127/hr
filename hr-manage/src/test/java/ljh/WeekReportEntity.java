package ljh;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @author Liujuhao
 * @date 2018/7/27.
 */
public class WeekReportEntity implements Serializable{

    @Excel(name = "姓名", mergeVertical = true)
    private String name;

    @Excel(name = "预计工作安排")
    private String planContent;

    @Excel(name = "实际完成工作内容")
    private String actualContent;

    @Excel(name = "完成报情况(%)")
    private String rate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    public String getActualContent() {
        return actualContent;
    }

    public void setActualContent(String actualContent) {
        this.actualContent = actualContent;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
