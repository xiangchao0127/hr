package com.handge.hr.domain.entity.manage.web.response.pointcard;

import java.util.List;
import java.util.Map;

/**
 * @author liuqian
 * @date 2018/8/23
 * @Description:积分卡信息
 */
public class PointCardInfo {
    /**
     * 积分卡ID
     */
    String cardId;
    /**
     * 积分卡名称
     */
    String cardName;
    /**
     * 积分项
     */
    List<PointItemInfo> items;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public List<PointItemInfo> getItems() {
        return items;
    }

    public void setItems(List<PointItemInfo> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PointCardInfo{" +
                "cardId='" + cardId + '\'' +
                ", cardName='" + cardName + '\'' +
                ", items=" + items +
                '}';
    }
}
