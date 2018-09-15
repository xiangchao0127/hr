package com.handge.hr.manage.service.impl.archive;

import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.EntityDepartment;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.archive.IEmployee;
import com.handge.hr.common.utils.PageResults;
import com.handge.hr.common.utils.PageUtils;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.employee.EmployeeParam;
import com.handge.hr.domain.entity.manage.web.request.archive.InformationParam;
import com.handge.hr.domain.entity.manage.web.response.archive.InformationRes;
import com.handge.hr.domain.repository.pojo.DictCommon;
import com.handge.hr.domain.repository.pojo.EntityEmployee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class EmployeeImpl implements IEmployee {
    private static final Log logger = LogFactory.getLog(EmployeeImpl.class);
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    DictCommonMapper dictCommon;
    @Autowired
    EntityDepartmentMapper entityDepartmentMapper;
    @Autowired
    EntityJobProfessionalLevelMapper dictJobProfessionalLevelMapper;
    @Autowired
    EntityJobPositionMapper entityJobPositionMapper;

    @Override
    public Integer addEmployee(EmployeeParam employeeParam) {
        //数据唯一性验证
        String idCard = employeeParam.getIdentityCard();
        Example example = new Example(EntityEmployee.class);
        example.createCriteria().andEqualTo("identityCard",idCard);
        List<EntityDepartment> entityDepartments = entityDepartmentMapper.selectByExample(example);
        if(entityDepartments != null){
            throw  new UnifiedException("该员工已存在", ExceptionWrapperEnum.IllegalArgumentException);
        }
        EntityEmployee entityEmployee = new EntityEmployee();
        entityEmployee.setId(UuidUtils.getUUid());
        entityEmployee.setDepartmentId(employeeParam.getDepartmentId()!=null?entityDepartmentMapper.selectByPrimaryKey(employeeParam.getDepartmentId()).getName():"");
        entityEmployee.setName(employeeParam.getName());
        entityEmployee.setEducation(employeeParam.getEducation());
        entityEmployee.setGender(employeeParam.getGender());
        entityEmployee.setJobNumber(employeeParam.getJobNumber());
        entityEmployee.setBirthday(employeeParam.getBirthday());
        entityEmployee.setJobStatus(employeeParam.getJobStatus());
        entityEmployee.setNationality(employeeParam.getNationality());
        entityEmployee.setNativePlace(employeeParam.getNativePlace());
        entityEmployee.setIdentityCard(employeeParam.getIdentityCard());
        entityEmployee.setAddress(employeeParam.getAddress());
        entityEmployee.setDegree(employeeParam.getDegree());
        entityEmployee.setPoliticalStatus(employeeParam.getPoliticalStatus());
        entityEmployee.setEmergencyName(employeeParam.getEmergencyName());
        entityEmployee.setEmergencyRelation(employeeParam.getEmergencyRelation());
        entityEmployee.setEmergencyPhone(employeeParam.getEmergencyPhone());
        entityEmployee.setTalentAbility(employeeParam.getTalentAbility());
        entityEmployee.setRemark(employeeParam.getRemark());
        entityEmployee.setDepartmentId(employeeParam.getDepartmentId());
        entityEmployee.setJobProfessionalLevelId(employeeParam.getJobProfessionalLevelId()!=null?dictJobProfessionalLevelMapper.selectByPrimaryKey(employeeParam.getJobProfessionalLevelId()).getProfessionalLevel():"");
        entityEmployee.setSalaryId(employeeParam.getSalaryId());
        entityEmployee.setJobTitleId(employeeParam.getJobTitleId());
        entityEmployee.setJobPostId(employeeParam.getJobPostId());
        entityEmployee.setJobPositionId(employeeParam.getJobPositionId());
        entityEmployee.setGmtCreate(new Date());
        entityEmployeeMapper.insert(entityEmployee);
        return 1;
    }

    @Override
    public Integer deleteEmployeeById(ArrayList<String> ids) {
        entityEmployeeMapper.deleteByIdList(ids);
        return 1;
    }

    @Override
    public Integer updateEmployee(EmployeeParam employeeParam) {
        EntityEmployee entityEmployee = entityEmployeeMapper.selectByPrimaryKey(employeeParam.getId());
        entityEmployee.setGmtModified(new Date());
        entityEmployee.setDepartmentId(employeeParam.getDepartmentId());
        entityEmployee.setName(employeeParam.getName());
        entityEmployee.setEducation(employeeParam.getEducation());
        entityEmployee.setGender(employeeParam.getGender());
        entityEmployee.setJobNumber(employeeParam.getJobNumber());
        entityEmployee.setBirthday(employeeParam.getBirthday());
        entityEmployee.setJobStatus(employeeParam.getJobStatus());
        entityEmployee.setNationality(employeeParam.getNationality());
        entityEmployee.setNativePlace(employeeParam.getNativePlace());
        entityEmployee.setIdentityCard(employeeParam.getIdentityCard());
        entityEmployee.setAddress(employeeParam.getAddress());
        entityEmployee.setDegree(employeeParam.getDegree());
        entityEmployee.setPoliticalStatus(employeeParam.getPoliticalStatus());
        entityEmployee.setEmergencyName(employeeParam.getEmergencyName());
        entityEmployee.setEmergencyRelation(employeeParam.getEmergencyRelation());
        entityEmployee.setEmergencyPhone(employeeParam.getEmergencyPhone());
        entityEmployee.setTalentAbility(employeeParam.getTalentAbility());
        entityEmployee.setRemark(employeeParam.getRemark());
        entityEmployee.setDepartmentId(employeeParam.getDepartmentId());
        entityEmployee.setJobProfessionalLevelId(employeeParam.getJobProfessionalLevelId());
        entityEmployee.setSalaryId(employeeParam.getSalaryId());
        entityEmployee.setJobTitleId(employeeParam.getJobTitleId());
        entityEmployee.setJobPostId(employeeParam.getJobPostId());
        entityEmployee.setJobPositionId(employeeParam.getJobPositionId());
        entityEmployeeMapper.updateByPrimaryKey(entityEmployee);
        return 1;
    }

    @Override
    public InformationRes getEmployeeDetails(String id) {
        EntityEmployee entityEmployee = entityEmployeeMapper.selectByPrimaryKey(id);
        System.out.println(entityEmployee);
        InformationRes res = new InformationRes();
        //工号
        res.setJobNumber(entityEmployee.getJobNumber());
        //姓名
        res.setName(entityEmployee.getName());
        //部门
        res.setDepartment(entityEmployee.getDepartmentId()!=null?entityDepartmentMapper.selectByPrimaryKey(entityEmployee.getDepartmentId()).getName():"");
        //职级
        res.setProfessionalLevel(entityEmployee.getJobProfessionalLevelId()!=null?dictJobProfessionalLevelMapper.selectByPrimaryKey(entityEmployee.getJobProfessionalLevelId()).getProfessionalLevel():"");
        //职务
        res.setPosition(entityEmployee.getJobPositionId()!=null?entityJobPositionMapper.selectByPrimaryKey(entityEmployee.getJobPositionId()).getName():"");
        //入职时间
        res.setHireDate(DateUtil.date2Str(entityEmployee.getHiredate(), DateFormatEnum.DAY));
        //工龄
        res.setSeniority(getSeniority(DateUtil.date2Str(entityEmployee.getHiredate(),DateFormatEnum.DAY)));
        //就职状态
        res.setJobStatus(entityEmployee.getJobStatus().toString());
        //出生日期
        res.setBirthday(entityEmployee.getBirthday());
        //性别
        res.setGender(entityEmployee.getGender().toString());
        //民族
        res.setNationality(entityEmployee.getNationality());
        //籍贯
        res.setNativePlace(entityEmployee.getNativePlace());
        //身份证号
        res.setIdentityCard(entityEmployee.getIdentityCard());
        //现居住址
        res.setAddress(entityEmployee.getAddress());
        //婚姻状况
        res.setMaritalStatus(entityEmployee.getMaritalStatus().toString());
        //子女情况
        res.setChildrenStatus(entityEmployee.getChildrenStatus().toString());
        //毕业学校
        res.setGraduateFrom(entityEmployee.getGraduateFrom());
        //专业
        res.setProfessional(entityEmployee.getProfessional());
        //学历
        res.setEducation(entityEmployee.getEducation().toString());
        //政治面貌
        res.setPoliticalStatus(entityEmployee.getPoliticalStatus().toString());
        //手机
        res.setMobile(entityEmployee.getMobile());
        //备注
        res.setRemark(entityEmployee.getRemark());
        System.out.println(res);
        logger.info(res);
        logger.debug(res);
        return res;
    }

    private String getSeniority(String hireDate){
        int i = 0;
        try {
            i = DateUtil.differentDays(hireDate, DateUtil.date2Str(new Date(), DateFormatEnum.DAY), DateFormatEnum.DAY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double hireDateD = Double.valueOf(i) / 365;
        return new BigDecimal(String.valueOf(hireDateD)).setScale(2,1).toString();
    }

    @Override
    public PageResults getInformation(InformationParam information) {
        PageUtils.startPage(information.getPageNo(), information.getPageSize());
        List<InformationRes> list = entityEmployeeMapper.getInformation(information);
        Map<Integer, DictCommon> dictCommonMap = dictCommon.getDictCommonMap();
        for (InformationRes e : list) {
            if (StringUtils.notEmpty(e.getJobStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getJobStatus()))) {
                e.setJobStatus(dictCommonMap.get(Integer.parseInt(e.getJobStatus())).getName());
            }
            if (StringUtils.notEmpty(e.getGender()) && dictCommonMap.containsKey(Integer.parseInt(e.getGender()))) {
                e.setGender(dictCommonMap.get(Integer.parseInt(e.getGender())).getName());
            }
            if (StringUtils.notEmpty(e.getMaritalStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getMaritalStatus()))) {
                e.setMaritalStatus(dictCommonMap.get(Integer.parseInt(e.getMaritalStatus())).getName());
            }
            if (StringUtils.notEmpty(e.getChildrenStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getChildrenStatus()))) {
                e.setChildrenStatus(dictCommonMap.get(Integer.parseInt(e.getChildrenStatus())).getName());
            }
            if (StringUtils.notEmpty(e.getEducation()) && dictCommonMap.containsKey(Integer.parseInt(e.getEducation()))) {
                e.setEducation(dictCommonMap.get(Integer.parseInt(e.getEducation())).getName());
            }
            if (StringUtils.notEmpty(e.getPoliticalStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getPoliticalStatus()))) {
                e.setPoliticalStatus(dictCommonMap.get(Integer.parseInt(e.getPoliticalStatus())).getName());
            }
        }
        PageResults<InformationRes> results = PageUtils.getPageInfo(list);
        return results;
    }

}
