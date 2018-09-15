package com.handge.hr.statistics.service.api;

import com.handge.hr.statistics.entity.request.AddAttendanceStatisticsParam;

import java.util.Map;

/**
 * @author liuqian
 * @date 2018/9/5
 * @Description:考勤统计接口
 */
public interface IAttendanceStatistics {
    /**
     * 月平均人数
     * @return {年：{月：月平均人数}}
     */
    Map<String, Map<String, String>> getAvgStaffOfMonth();

    /**
     * 添加考勤统计信息
     * @param addAttendanceStatisticsParam
     * @return
     */
    Object addAttendanceStatistics(AddAttendanceStatisticsParam addAttendanceStatisticsParam);

}
