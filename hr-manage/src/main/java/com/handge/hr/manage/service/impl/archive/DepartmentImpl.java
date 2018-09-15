package com.handge.hr.manage.service.impl.archive;

import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.archive.IDepartment;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.archive.DeleteDepartmentParam;
import com.handge.hr.domain.entity.manage.web.request.archive.DepartmentParam;
import com.handge.hr.domain.entity.manage.web.request.archive.ListMemberParam;
import com.handge.hr.domain.entity.manage.web.request.employee.MoveEmployeeParam;
import com.handge.hr.domain.entity.manage.web.response.archive.DepartmentAndMembers;
import com.handge.hr.domain.repository.mapper.EntityDepartmentMapper;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import com.handge.hr.domain.repository.pojo.EntityDepartment;
import com.handge.hr.domain.repository.pojo.EntityEmployee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by DaLu Guo on 2018/7/31.
 */
@Component
public class DepartmentImpl implements IDepartment {

    @Autowired
    EntityDepartmentMapper entityDepartmentMapper;

    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;

    Log logger = LogFactory.getLog(this.getClass());

    @Override
    public Object showDepartmentStructure() {
        HashMap<String, Object> resultMap = new HashMap<>();
        //部门列表
        List<EntityDepartment> entityDepartments = entityDepartmentMapper.selectAll();
        //保存暂时不清楚层级情况的实体
        List<EntityDepartment> unsureEntityDepartments = new ArrayList<>();
        boolean isFirst = true;
        for (EntityDepartment entityDepartment : entityDepartments) {
            if (isFirst) {
                if (StringUtils.notEmpty(entityDepartment.getHigherDepartmentId())) {
                    HashMap<String, Object> map = new HashMap<>();
                    List<Map> mapList = new ArrayList<>();
                    map.put(entityDepartment.getId(), entityDepartment.getName());
                    map.put("sub", new ArrayList<>());
                    mapList.add(map);
                    resultMap.put(entityDepartment.getHigherDepartmentId(), entityDepartmentMapper.selectByPrimaryKey(entityDepartment.getHigherDepartmentId()).getName());
                    resultMap.put("sub", mapList);
                } else {
                    resultMap.put(entityDepartment.getId(), entityDepartment.getName());
                    resultMap.put("sub", new ArrayList<>());
                }
                isFirst = false;
            } else {
                List<Map> subMapList = (List<Map>) resultMap.get("sub");
                //获取当前resultMap中最高级别的id
                String resultId = null;
                for (String key : resultMap.keySet()) {
                    if (!"sub".equals(key)) {
                        resultId = key;
                    }
                }
                //第一种情况：当前部门是resultMap第一级的下级
                if (resultMap.containsKey(entityDepartment.getHigherDepartmentId())) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(entityDepartment.getId(), entityDepartment.getName());
                    map.put("sub", new ArrayList<>());
                    subMapList.add(map);
                    resultMap.put("sub", subMapList);
                    //判断不确定层级的部门list与当前部门的关系
                    for(EntityDepartment unsureEntity : unsureEntityDepartments){
                        if(unsureEntity.getHigherDepartmentId().equals(entityDepartment.getId())){
                            HashMap<String, Object> unsureSubMap = new HashMap<>();
                            List<Map> unsureSubMapList = new ArrayList<>();
                            unsureSubMap.put(unsureEntity.getId(), unsureEntity.getName());
                            unsureSubMap.put("sub", new ArrayList<>());
                            unsureSubMapList.add(unsureSubMap);
                        }
                    }
                }
                //第二种情况：当前部门是resultMap第一级的上级
                else if (entityDepartmentMapper.selectByPrimaryKey(resultId).getHigherDepartmentId() != null && entityDepartmentMapper.selectByPrimaryKey(resultId).getHigherDepartmentId().equals(entityDepartment.getId())) {
                    List<Map> resultSubMapList = new ArrayList<>();
                    resultSubMapList.add(resultMap);
                    resultMap.put(entityDepartment.getId(), entityDepartment.getName());
                    resultMap.put("sub", resultSubMapList);
                } else {
                    boolean isSub = false;
                    for (Map subMap : subMapList) {
                        //第三种情况：当前部门是resultMap第一级的下级的下级
                        if (subMap.containsKey(entityDepartment.getHigherDepartmentId())) {
                            HashMap<String, Object> map = new HashMap<>(2);
                            map.put(entityDepartment.getId(), entityDepartment.getName());
                            map.put("sub", new ArrayList<>());
                            List<Map> subSubMapList = (List<Map>) subMap.get("sub");
                            subSubMapList.add(map);
                            subMap.put("sub", subSubMapList);
                            isSub = true;
                            break;
                        }
                    }
                    //第四种情况：当前部门与resultMap无关联
                   if(!isSub){
                       unsureEntityDepartments.add(entityDepartment);
                   }
                }
            }
        }
        return resultMap;
    }

    @Override
    public Object listDepartment() {
        List<EntityDepartment> entityDepartments = entityDepartmentMapper.selectAll();
        return entityDepartments;
    }

    @Override
    public Object addDepartment(DepartmentParam departmentParam) {
        EntityDepartment entityDepartment = new EntityDepartment();
        entityDepartment.setId(UuidUtils.getUUid());
        entityDepartment.setName(departmentParam.getName());
        entityDepartment.setGmtCreate(new Date());
        entityDepartment.setDescription(departmentParam.getDescription());
        entityDepartment.setHeaderEmployeeId(departmentParam.getHeaderEmployeeId());
        entityDepartment.setStatus(departmentParam.getStatus());
        entityDepartment.setHigherDepartmentId(departmentParam.getHigherDepartmentId());
        entityDepartmentMapper.insert(entityDepartment);
        return 1;
    }

    @Override
    public Object alterDepartment(DepartmentParam departmentParam) {
        EntityDepartment entityDepartment = entityDepartmentMapper.selectByPrimaryKey(departmentParam.getId());
        if (entityDepartment == null) {
            logger.info("该部门不存在");
            return 0;
        }
        entityDepartment.setDescription(departmentParam.getDescription());
        entityDepartment.setHeaderEmployeeId(departmentParam.getHeaderEmployeeId());
        entityDepartment.setName(departmentParam.getName());
        entityDepartment.setGmtModified(new Date());
        entityDepartment.setStatus(departmentParam.getStatus());
        entityDepartment.setHigherDepartmentId(departmentParam.getHigherDepartmentId());
        entityDepartmentMapper.updateByPrimaryKey(entityDepartment);
        return 1;
    }

    @Override
    public Object listMember(ListMemberParam listMemberParam) {
        List<EntityEmployee> entityEmployees = entityEmployeeMapper.selectCustomer(null, null, listMemberParam.getId());

        return entityEmployees;
    }

    @Override
    public Object deleteDepartment(DeleteDepartmentParam deleteDepartmentParam) {
        boolean isEmpty = judgeLowerDepartmentMember(deleteDepartmentParam.getIds());
        if (isEmpty) {
            for (String id : deleteDepartmentParam.getIds()) {
                entityDepartmentMapper.deleteByPrimaryKey(id);
            }
        } else {
            logger.info("该部门或其下级部门成员不为空，无法删除");
            return 0;
        }
        return 1;
    }

    @Override
    public Object findDepartmentAndMembers() {
        List<EntityDepartment> entityDepartments = entityDepartmentMapper.selectAll();
        List<DepartmentAndMembers> findDepartmentAndMember = new ArrayList<>();
        entityDepartments.forEach(o -> {
            findDepartmentAndMember.add(new DepartmentAndMembers() {
                {
                    this.setDepartmentId(o.getId());
                    this.setDepartmentName(o.getName());
                    Map<String, String> map = new HashMap<>();
                    List<EntityEmployee> entityEmployees = entityEmployeeMapper.selectCustomer(null, null, o.getId());
                    entityEmployees.forEach(e -> {
                        map.put(String.valueOf(e.getId()), e.getName());
                    });
                    this.setEmployeeIdName(map);
                }
            });
        });
        return findDepartmentAndMember;
    }

    @Override
    public Object moveEmployee(MoveEmployeeParam moveEmployeeParam) {
        for (String eId : moveEmployeeParam.getOutEmployeeIds()) {
            boolean isSameDepartment = entityEmployeeMapper.selectByPrimaryKey(eId).getDepartmentId().equals(moveEmployeeParam.getOutEmployeeIds());
            if (!isSameDepartment) {
                EntityEmployee entityEmployee = entityEmployeeMapper.selectByPrimaryKey(eId);
                entityEmployee.setDepartmentId(moveEmployeeParam.getInDepartmentId());
                entityEmployee.setGmtModified(new Date());
                entityEmployeeMapper.updateByPrimaryKey(entityEmployee);
            } else {
                logger.info("不能移动到当前部门！");
                return 0;
            }
        }
        return 1;
    }


    /**
     * 递归判断某部门的所有下级部门是否为空
     *
     * @param entityDepartmentIds
     * @return true:为空 false:不为空
     */
    private boolean judgeLowerDepartmentMember(List<String> entityDepartmentIds) {
        boolean flag = false;
        if (entityDepartmentIds.isEmpty()) {
            flag = true;
        } else {
            for (String id : entityDepartmentIds) {
                List<EntityEmployee> employeeList = entityEmployeeMapper.selectCustomer(null, null, id);
                if (employeeList.isEmpty()) {
                    flag = judgeLowerDepartmentMember(entityDepartmentMapper.getLowerDepartmentList(id));
                } else {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
}
