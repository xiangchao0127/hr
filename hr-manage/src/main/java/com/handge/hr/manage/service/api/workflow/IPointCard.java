package com.handge.hr.manage.service.api.workflow;

import com.handge.hr.domain.entity.manage.web.request.pointcard.AddPointCardParam;

import com.handge.hr.domain.entity.manage.web.request.pointcard.ListPointCardParam;
import com.handge.hr.domain.entity.manage.web.request.pointcard.UpdatePointCardParam;
import com.handge.hr.domain.entity.manage.web.request.pointcard.ViewPointCardParam;
import com.handge.hr.domain.entity.manage.web.response.pointcard.PointCardInfo;

import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/23
 * @Description: 积分卡接口
 */
public interface IPointCard {

    /**
     * 添加一个积分卡实例（项目和任务调用）
     *
     * @param addPointCardParam
     * @return
     */
    Object addPointCard(AddPointCardParam addPointCardParam);

    /**
     * 查看积分卡实例
     *
     * @param viewPointCardParam
     * @return
     */
    Object viewPointCard(ViewPointCardParam viewPointCardParam);

    /**
     * 积分卡列表
     *
     * @param listPointCardParam
     * @return
     */
    List<PointCardInfo> listPointCard(ListPointCardParam listPointCardParam);

    /**
     * 更新积分卡
     *
     * @param updatePointCardParam
     * @return
     */
    Object updatePointCard(UpdatePointCardParam updatePointCardParam);
}
