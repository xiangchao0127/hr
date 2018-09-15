package com.handge.hr.manage.service.api.archive;

/**
 * Created by MaJianfu on 2018/8/1.
 */
public interface IExcelInformationImport {
    /***
     * excel 导入到postgresql (员工信息表)
     * @author MaJianfu
     * @date 2018/8/1 10:42
     * @param
     * @return java.lang.Integer
     **/
    Integer importInformation();

    /**
     * excel 导入到postgresql (枚举字典表)
     *
     * @param
     * @return java.lang.Integer
     * @author MaJianfu
     * @date 2018/8/2 10:34
     **/
    Integer importDictCommon();

    /**
     * excel 导入到postgresql (考勤表)
     *
     * @param
     * @return java.lang.Integer
     * @author MaJianfu
     * @date 2018/8/10 10:38
     **/
    Integer importAttendanceFlowRecords();
}
