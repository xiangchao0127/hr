package com.handge.hr.manage.service.api.archive;


import com.handge.hr.domain.entity.manage.web.request.archive.AddPositionParam;
import com.handge.hr.domain.entity.manage.web.request.archive.DeletePositionParam;
import com.handge.hr.domain.entity.manage.web.request.archive.FindPositionParam;
import com.handge.hr.domain.entity.manage.web.request.archive.ModifyPositionParam;
import com.handge.hr.domain.repository.pojo.EntityJobPosition;

import java.util.List;

/**
 * @author liuqian
 * @date 2018/7/31
 * @Description: 岗位CRUD接口
 */
public interface IPosition {
    /**
     * 添加岗位
     * @param addPositionParam
     * @return
     */
    int addPosition(AddPositionParam addPositionParam);

    /**
     * 删除岗位
     * @param deletePositionParam
     * @return
     */
    int deletePosition(DeletePositionParam deletePositionParam);

    /**
     * 查询岗位（模糊查询）
     * @param findPositionParam
     * @return
     */
    List<EntityJobPosition> findPosition(FindPositionParam findPositionParam);

    /**
     * 修改岗位
     * @param modifyPositionParam
     * @return
     */
    int modifyPosition(ModifyPositionParam modifyPositionParam);
}
