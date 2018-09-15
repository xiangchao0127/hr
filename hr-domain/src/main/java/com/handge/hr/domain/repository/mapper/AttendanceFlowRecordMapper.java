package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.AttendanceFlowRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author  MaJianfu
 * @date  2018/8/10
 */
public interface AttendanceFlowRecordMapper extends CommonMapper<AttendanceFlowRecord, String> {
    /**
     * 查询某员工某月打卡时间记录(打卡时间，考勤日)
     * 注意：这里只获取的考勤日第一次打卡和最后一次打卡时间，所以打卡次数为2或1
     * @param number 员工工号
     * @param month 月份（形如："2018-08"）
     * @return
     */
    @Select("SELECT\n" +
            "\trecord_time,\n" +
            "\tattendance_time\n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\tr.dk_date record_time,\n" +
            "\tto_char( r.dk_date, 'yyyy-mm-dd' ) attendance_time,\n" +
            "\tROW_NUMBER ( ) OVER ( PARTITION BY to_char( r.dk_date, 'yyyy-mm-dd' ) ORDER BY dk_date ) row_asc,\n" +
            "\tROW_NUMBER ( ) OVER ( PARTITION BY to_char( r.dk_date, 'yyyy-mm-dd' ) ORDER BY dk_date DESC ) row_desc \n" +
            "FROM\n" +
            "\tattendance_flow_record r \n" +
            "WHERE\n" +
            "\tr.job_number = #{number} \n" +
            "GROUP BY\n" +
            "\tr.dk_date \n" +
            "HAVING\n" +
            "\tto_char( r.dk_date, 'yyyy-mm' ) = #{month} \n" +
            "\t) T \n" +
            "WHERE\n" +
            "\trow_asc = '1' \n" +
            "\tOR row_desc = '1'")
    List<Map<String,Object>> getPunchCardTimeRecord(@Param("number") String number, @Param("month") String month);


    /**
     * 查询某月所有员工打卡时间记录(工号，打卡时间，考勤日)
     * 注意：这里只获取的考勤日第一次打卡和最后一次打卡时间，所以打卡次数为2或1
     * @param month 月份（形如："2018-08"）
     * @return
     */
    @Select("SELECT\n" +
            "\tjob_number,\n" +
            "\trecord_time,\n" +
            "\tattendance_date,\n" +
            "\tCOUNT(*) over(PARTITION BY job_number, attendance_date)\n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\tr.job_number,\n" +
            "\tr.dk_date record_time,\n" +
            "\tto_char ( r.dk_date, 'yyyy-mm-dd' ) attendance_date,\n" +
            "\tROW_NUMBER ( ) OVER ( PARTITION BY r.job_number, to_char ( r.dk_date, 'yyyy-mm-dd' ) ORDER BY dk_date ) row_asc,\n" +
            "\tROW_NUMBER ( ) OVER ( PARTITION BY r.job_number, to_char ( r.dk_date, 'yyyy-mm-dd' ) ORDER BY dk_date DESC ) row_desc \n" +
            "FROM\n" +
            "\tattendance_flow_record r \n" +
            "GROUP BY\n" +
            "\tr.job_number,\n" +
            "\tr.dk_date \n" +
            "HAVING\n" +
            "\tto_char ( r.dk_date, 'yyyy-mm' ) = #{month} \n" +
            "\t) T \n" +
            "WHERE\n" +
            "\trow_asc = '1' \n" +
            "\tOR row_desc = '1' ")
    List<Map<String,Object>> getPunchCardTimeRecordOfMonth(String month);


}
