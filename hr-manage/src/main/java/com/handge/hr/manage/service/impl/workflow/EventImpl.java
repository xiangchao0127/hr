package com.handge.hr.manage.service.impl.workflow;

import com.handge.hr.domain.repository.mapper.WorkflowProjectEventMapper;
import com.handge.hr.domain.repository.mapper.WorkflowTaskEventMapper;
import com.handge.hr.domain.repository.pojo.WorkflowProjectEvent;
import com.handge.hr.domain.repository.pojo.WorkflowTaskEvent;
import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.workflow.IEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by MaJianfu on 2018/9/5.
 */
@Service
public class EventImpl implements IEvent {

    @Autowired
    WorkflowTaskEventMapper workflowTaskEventMapper;

    @Autowired
    WorkflowProjectEventMapper workflowProjectEventMapper;

    @Override
    public void addProjectEvent(String projectId, String projectType, String description) {
        WorkflowProjectEvent event = new WorkflowProjectEvent();
        event.setId(UuidUtils.getUUid());
        event.setGmtCreate(new Date());
        event.setProjectId(projectId);
        event.setType(projectType);
        event.setDescription(description);
        workflowProjectEventMapper.insertSelective(event);
    }

    @Override
    public void addTaskEvent(String taskId, String taskType, String description) {
        WorkflowTaskEvent event = new WorkflowTaskEvent();
        event.setId(UuidUtils.getUUid());
        event.setGmtCreate(new Date());
        event.setTaskId(taskId);
        event.setType(taskType);
        event.setDescription(description);
        workflowTaskEventMapper.insertSelective(event);
    }
}
