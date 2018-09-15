package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.WorkflowQc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author liuqian
 * @date 2018/8/31
 * @Description:
 */
@Mapper
public interface WorkflowQcMapper extends CommonMapper<WorkflowQc,String> {

    /**根据项目类型查询部门id
     * @param projectType:项目类型
     * @return departmentId
     */
    @Select("select department_id from workflow_qc where project_type_id =#{projectType}")
    String getDepartmentId(String projectType);
}
