package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.EmployeeDepartmentPostionView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeDepartmentPostionViewMapper extends CommonMapper<EmployeeDepartmentPostionView,String> {
    @Select("select * from employee_department_postion_view where name = #{name}")
    public List<EmployeeDepartmentPostionView> getEmployeeInfoByName(String name);
}
