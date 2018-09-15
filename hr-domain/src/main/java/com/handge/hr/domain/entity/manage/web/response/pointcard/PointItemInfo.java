package com.handge.hr.domain.entity.manage.web.response.pointcard;

import java.util.List;
import java.util.Map;

/**
 * @author liuqian
 * @date 2018/8/23
 * @Description:积分项信息
 */
public class PointItemInfo {
    /**
     * 积分项ID
     */
    String itemId;
    /**
     * 积分项名称
     */
    String itemName;

    /**
     * 输入类型
     */
    String inputType;

    /**
     * 选项值
     */
     List<Map<String,String>> options;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public List<Map<String, String>> getOptions() {
        return options;
    }

    public void setOptions(List<Map<String, String>> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "PointItemInfo{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", inputType='" + inputType + '\'' +
                ", options=" + options +
                '}';
    }
}
