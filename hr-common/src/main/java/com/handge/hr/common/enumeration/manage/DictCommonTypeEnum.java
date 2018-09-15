package com.handge.hr.common.enumeration.manage;

/**
 * Created by MaJianfu on 2018/6/25.
 */
public enum DictCommonTypeEnum {
    //就职状态
    JOB_STATUS("jobStatus"),
    //性别
    GENDER("gender"),
    //婚姻状况
    MARITAL_STATUS("maritalStatus"),
    //子女情况
    CHILDREN_STATUS("childrenStatus"),
    //学历
    EDUCATION("education"),
    //政治面貌
    POLITICAL_STATUS("politicalStatus"),
    //部门状态
    DEPARTMENT_STATUS("departmentStatus"),
    //职位状态
    POSITION_STATUS("positionStatus"),
    //职务状态
    POST_STATUS("postStatus"),
    //项目状态
    PROJECT_STATUS("projectStatus"),
    //事件类型
    EVENT_TYPE("eventType"),
    //任务状态
    TASK_STATUS("taskStatus"),
    //项目类型
    PROJECT_TYPE("projectType"),
    //任务类型
    TASK_TYPE("taskType"),
    //工作量
    WORKLOAD("workload"),
    //难度
    DIFFICULT("difficult"),
    //紧急程度
    URGENCY("urgency");

    private String value;

    DictCommonTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
