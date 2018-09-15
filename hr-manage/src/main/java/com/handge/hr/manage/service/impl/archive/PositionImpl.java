package com.handge.hr.manage.service.impl.archive;

import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.archive.IPosition;
import com.handge.hr.domain.entity.manage.web.request.archive.AddPositionParam;
import com.handge.hr.domain.entity.manage.web.request.archive.DeletePositionParam;
import com.handge.hr.domain.entity.manage.web.request.archive.FindPositionParam;
import com.handge.hr.domain.entity.manage.web.request.archive.ModifyPositionParam;
import com.handge.hr.domain.repository.mapper.DictCommonMapper;
import com.handge.hr.domain.repository.mapper.EntityJobPositionMapper;
import com.handge.hr.domain.repository.pojo.EntityJobPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuqian
 * @date 2018/7/31
 * @Description: 岗位CRUD实现类
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class PositionImpl implements IPosition {
    @Autowired
    EntityJobPositionMapper entityJobPositionMapper;
    @Autowired
    DictCommonMapper dictCommonMapper;

    @Override
    public int addPosition(AddPositionParam addPositionParam) {
        EntityJobPosition entityJobPosition = new EntityJobPosition();
        //id
        entityJobPosition.setId(UuidUtils.getUUid());
        //添加时间
        entityJobPosition.setGmtCreate(new Date());
        //岗位名称
        entityJobPosition.setName(addPositionParam.getName());
        //岗位描述
        entityJobPosition.setDescription(addPositionParam.getDescription());
        //状态
        int statusId = dictCommonMapper.getIdByCommonTypeAndName(addPositionParam.getStatus());
        entityJobPosition.setStatus(statusId);
        //上级部门
        String id = entityJobPositionMapper.getId(addPositionParam.getHigherPositionName());
        entityJobPosition.setHigherPositionId(id);
        entityJobPositionMapper.insert(entityJobPosition);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePosition(DeletePositionParam deletePositionParam) {
        List<String> ids = deletePositionParam.getIds();
        List<EntityJobPosition> list = new ArrayList<>();
        //判断要删除的岗位是否存在下级岗位，如果存在则不能删除
        ids.forEach(o -> {
            EntityJobPosition entityJobPosition = entityJobPositionMapper.getPositionByHigherPositionId(o);
            if (entityJobPosition != null) {
                list.add(entityJobPosition);
            }
        });
        //如果要删除的岗位存在下级岗位,list不为空
        if (list.isEmpty()) {
            entityJobPositionMapper.deleteByIdList(ids);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<EntityJobPosition> findPosition(FindPositionParam findPositionParam) {
        return entityJobPositionMapper.selectPositions(findPositionParam);
    }

    @Override
    public int modifyPosition(ModifyPositionParam modifyPositionParam) {
        EntityJobPosition entityJobPosition = entityJobPositionMapper.getPositionById(modifyPositionParam.getId());
        //修改时间
        entityJobPosition.setGmtModified(new Date());
        //岗位名称
        entityJobPosition.setName(modifyPositionParam.getName());
        //岗位描述
        entityJobPosition.setDescription(modifyPositionParam.getDescription());
        //状态
        int statusId = dictCommonMapper.getIdByCommonTypeAndName(modifyPositionParam.getStatus());
        entityJobPosition.setStatus(statusId);
        //上级岗位
        String id = entityJobPositionMapper.getId(modifyPositionParam.getHigherPositionName());
        entityJobPosition.setHigherPositionId(id);
        entityJobPositionMapper.updateByPrimaryKey(entityJobPosition);
        return 1;
    }
}
