package com.handge.hr.manage.service.impl.archive;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.FileConfigurationUtils;
import com.handge.hr.domain.entity.manage.excel.AttendanceFlowRecordsExcel;
import com.handge.hr.domain.entity.manage.excel.AttendanceFlowUsersExcel;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.*;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.archive.IExcelInformationImport;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.excel.DictCommonExcel;
import com.handge.hr.domain.entity.manage.excel.EmployeeExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MaJianfu on 2018/8/1.
 */
@Service
public class ExcelInformationImportImpl implements IExcelInformationImport {

    @Autowired
    EntityJobPositionMapper jobPosition;
    @Autowired
    EntityJobProfessionalLevelMapper jobProfessionalLevel;
    @Autowired
    EntityDepartmentMapper department;
    @Autowired
    EntityEmployeeMapper employee;
    @Autowired
    DictCommonTypeMapper dictCommonType;
    @Autowired
    DictCommonMapper dictCommon;
    @Autowired
    AttendanceFlowRecordMapper recordMapper;

    @Override
    public Integer importInformation() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<EmployeeExcel> list = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FileConfigurationUtils.getConfig("employeeInformation"));
        } catch (FileNotFoundException e) {
            throw new UnifiedException("员工信息模板不存在", ExceptionWrapperEnum.FileNotFoundException);
        }
        try {
            list = ExcelImportUtil.importExcel(fileInputStream, EmployeeExcel.class, params);
            fileInputStream.close();
        } catch (Exception e) {
            throw new UnifiedException(e);
        }
        for (EmployeeExcel e : list) {
            EntityEmployee entityEmployee = new EntityEmployee();
            EntityDepartment entityDepartment = new EntityDepartment();
            EntityJobProfessionalLevel entityJobProfessionalLevel = new EntityJobProfessionalLevel();
            EntityJobPosition dictJobPosition = new EntityJobPosition();
            entityEmployee.setId(UuidUtils.getUUid());
            entityEmployee.setName(e.getName());
            if (StringUtils.isEmpty(department.getId(e.getDepartment()))) {
                entityDepartment.setId(UuidUtils.getUUid());
                entityDepartment.setName(e.getDepartment());
                entityDepartment.setGmtCreate(new Date());
                entityEmployee.setDepartmentId(entityDepartment.getId());
                department.insertSelective(entityDepartment);
            } else {
                entityEmployee.setDepartmentId(department.getId(e.getDepartment()));
            }
            if (StringUtils.isEmpty(jobProfessionalLevel.getId(e.getProfessionalLevel()))) {
                entityJobProfessionalLevel.setId(UuidUtils.getUUid());
                entityJobProfessionalLevel.setProfessionalLevel(e.getProfessionalLevel());
                entityJobProfessionalLevel.setGmtCreate(new Date());
                entityEmployee.setJobProfessionalLevelId(entityJobProfessionalLevel.getId());
                jobProfessionalLevel.insertSelective(entityJobProfessionalLevel);
            } else {
                entityEmployee.setJobProfessionalLevelId(jobProfessionalLevel.getId(e.getProfessionalLevel()));
            }
            if (StringUtils.isEmpty(jobPosition.getId(e.getPosition()))) {
                dictJobPosition.setId(UuidUtils.getUUid());
                dictJobPosition.setName(e.getPosition());
                dictJobPosition.setGmtCreate(new Date());
                entityEmployee.setJobPositionId(dictJobPosition.getId());
                jobPosition.insertSelective(dictJobPosition);
            } else {
                entityEmployee.setJobPositionId(jobPosition.getId(e.getPosition()));
            }
            entityEmployee.setHiredate(e.getHiredate());
            if (StringUtils.notEmpty(e.getJobStatus())) {
                //entityEmployee.setJobStatus(Integer.valueOf(dictCommon.getId(DictCommonTypeEnum.jobStatus.getValue(),e.getJobStatus())));
            }
            entityEmployee.setBirthday(DateUtil.date2Str(e.getBirthday(), DateFormatEnum.DAY));
            if (StringUtils.notEmpty(e.getGender())) {
                //entityEmployee.setGender(Integer.valueOf(dictCommon.getId(DictCommonTypeEnum.gender.getValue(),e.getGender())));
            }
            entityEmployee.setNationality(e.getNationality());
            entityEmployee.setNativePlace(e.getNativePlace());
            entityEmployee.setIdentityCard(e.getIdentityCard());
            entityEmployee.setAddress(e.getAddress());
            if (StringUtils.notEmpty(e.getMaritalStatus())) {
                //entityEmployee.setMaritalStatus(Integer.valueOf(dictCommon.getId(DictCommonTypeEnum.maritalStatus.getValue(),e.getMaritalStatus())));
            }
            if (StringUtils.notEmpty(e.getChildrenStatus())) {
                //entityEmployee.setChildrenStatus(Integer.valueOf(dictCommon.getId(DictCommonTypeEnum.childrenStatus.getValue(),e.getChildrenStatus())));
            }
            entityEmployee.setGraduateFrom(e.getGraduateFrom());
            entityEmployee.setProfessional(e.getProfessional());
            if (StringUtils.notEmpty(e.getEducation())) {
                //entityEmployee.setEducation(Integer.valueOf(dictCommon.getId(DictCommonTypeEnum.education.getValue(),e.getEducation())));
            }
            if (StringUtils.notEmpty(e.getPoliticalStatus())) {
                //entityEmployee.setPoliticalStatus(Integer.valueOf(dictCommon.getId(DictCommonTypeEnum.politicalStatus.getValue(),e.getPoliticalStatus())));
            }
            entityEmployee.setMobile(e.getMobile());
            entityEmployee.setRemark(e.getRemark());
            entityEmployee.setGmtCreate(new Date());
            entityEmployee.setJobNumber(e.getJobNumber());
            employee.insertSelective(entityEmployee);
        }
        return 1;
    }

    @Override
    public Integer importDictCommon() {
        ImportParams params = new ImportParams();
        //params.setReadRows(7);
        List<DictCommonExcel> list = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FileConfigurationUtils.getConfig("dictCommon"));
        } catch (FileNotFoundException e) {
            throw new UnifiedException("枚举模板不存在", ExceptionWrapperEnum.FileNotFoundException);
        }
        try {
            list = ExcelImportUtil.importExcel(fileInputStream, DictCommonExcel.class, params);
            fileInputStream.close();
        } catch (Exception e) {
            throw new UnifiedException(e);
        }
        int i = 0;
        for (DictCommonExcel dict : list) {
            if (StringUtils.isEmpty(String.valueOf(dictCommonType.getId(dict.getTypeNameCh(), dict.getTypeNameEn())))) {
                dictCommonType.save(dict.getTypeNameCh(), dict.getTypeNameEn());
                i = 1;
            } else {
                i++;
            }
            dictCommon.save(dictCommonType.getId(dict.getTypeNameCh(), dict.getTypeNameEn()), dict.getName(), i);
        }
        return 1;
    }

    @Override
    public Integer importAttendanceFlowRecords() {
        ImportParams recordParams = new ImportParams();
        recordParams.setSheetNum(1);
        ImportParams userParams = new ImportParams();
        userParams.setStartSheetIndex(1);
        userParams.setSheetNum(1);
        FileInputStream fileInputStream1 = null;
        FileInputStream fileInputStream2 = null;
        List<AttendanceFlowRecordsExcel> recordsExcelList = null;
        List<AttendanceFlowUsersExcel> usersExcelList = null;
        try {
            fileInputStream1 = new FileInputStream(FileConfigurationUtils.getConfig("attendanceFlowRecord"));
            fileInputStream2 = new FileInputStream(FileConfigurationUtils.getConfig("attendanceFlowRecord"));
        } catch (FileNotFoundException e) {
            throw new UnifiedException("考勤模板不存在", ExceptionWrapperEnum.FileNotFoundException);
        }
        try {
            usersExcelList = ExcelImportUtil.importExcel(fileInputStream1, AttendanceFlowUsersExcel.class, userParams);
            recordsExcelList = ExcelImportUtil.importExcel(fileInputStream2, AttendanceFlowRecordsExcel.class, recordParams);
            fileInputStream1.close();
            fileInputStream2.close();
        } catch (Exception e) {
            throw new UnifiedException(e);
        }
        HashMap<String, String> userMap = new HashMap<>();
        for (AttendanceFlowUsersExcel user : usersExcelList) {
            userMap.put(user.getUid(), user.getName() + "|" + user.getJobNumber());
        }
        for (AttendanceFlowRecordsExcel record : recordsExcelList) {
            AttendanceFlowRecord attendanceFlowRecord = new AttendanceFlowRecord();
            attendanceFlowRecord.setId(UuidUtils.getUUid());
            attendanceFlowRecord.setUid(record.getUid());
            attendanceFlowRecord.setDkDate(record.getDkDate());
            String name = "";
            String jobNumber = "";
            if (userMap.containsKey(record.getUid())) {
                name = userMap.get(record.getUid()).split("\\|", -1)[0];
                jobNumber = userMap.get(record.getUid()).split("\\|", -1)[1];
            }
            if (StringUtils.isEmpty(name) || "null".equals(name)) {
                attendanceFlowRecord.setName(null);
            } else {
                attendanceFlowRecord.setName(name);
            }
            if (StringUtils.isEmpty(jobNumber) || "null".equals(jobNumber)) {
                attendanceFlowRecord.setJobNumber(null);
            } else {
                attendanceFlowRecord.setJobNumber(jobNumber);
            }
            recordMapper.insertSelective(attendanceFlowRecord);
        }
        return 1;
    }
}
