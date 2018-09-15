package com.handge.hr.manage.service.impl.attendance;

import com.handge.hr.common.enumeration.manage.AttendanceStatusEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.domain.entity.manage.web.request.attendance.GetAttendanceInfoParam;
import com.handge.hr.domain.entity.manage.web.response.attendance.GetAttendanceInfo;
import com.handge.hr.domain.repository.mapper.AttendanceConfigMapper;
import com.handge.hr.domain.repository.mapper.AttendanceFlowRecordMapper;
import com.handge.hr.domain.repository.pojo.AttendanceConfig;
import com.handge.hr.manage.service.api.attendance.IAttendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuqian
 * @date 2018/8/31
 * @Description:考勤管理接口实现
 */
@Component
public class AttendanceImpl implements IAttendance {
    @Autowired
    AttendanceConfigMapper configMapper;
    @Autowired
    AttendanceFlowRecordMapper flowRecordMapper;

    @Override
    public List<GetAttendanceInfo> getAttendanceInfo(GetAttendanceInfoParam getAttendanceInfoParam) {
        String workingTime = "上班时间";
        String closingTime = "下班时间";
        String workingDay = "工作日";
        String startTimeOfAfternoon = "下午开始时间";
        //打卡时间
        String punchCardTime = "record_time";
        //考勤日期
        String attendanceDate = "attendance_time";
        //考勤结果（每一天）
        List<GetAttendanceInfo> attendanceResultOfMonth = new ArrayList<>();
        //考勤结果（去掉没有上班的日期）
        List<GetAttendanceInfo> attendanceResults = new ArrayList<>();

        //获取数据库考勤参数配置
        List<AttendanceConfig> attendanceConfigs = configMapper.selectAll();
        Map<String, String> configMap = new HashMap<>(10);
        attendanceConfigs.forEach(config -> configMap.put(config.getName(), config.getValue()));
        //工作日配置
        String workDay = configMap.get(workingDay);
        String[] split = workDay.split(",");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());

        //获取数据库打卡时间记录
        List<Map<String, Object>> punchCardTimeRecord = flowRecordMapper.getPunchCardTimeRecord(getAttendanceInfoParam.getNumber(), getAttendanceInfoParam.getMonth());


        //需要查询考勤信息的年月，形如:"2018-08"
        String yearMonth = getAttendanceInfoParam.getMonth();
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(6));

        //获取某月所有日期
        List<String> dayOfMonth = DateUtil.getDayOfMonth(year, month);
        //获取某月所有工作日期
        List<String> workDayOfMonth = DateUtil.getWorkDayOfMonth(year, month, collect);

        //每一天创建一个实体，打卡状态默认为'未打卡'
        dayOfMonth.forEach(day -> {
            GetAttendanceInfo attendanceInfo = new GetAttendanceInfo();
            attendanceInfo.setAttendanceDate(day.toString());
            attendanceInfo.setEarlyStatus(AttendanceStatusEnum.NOT_PUNCH_CARD);
            attendanceInfo.setEveningStatus(AttendanceStatusEnum.NOT_PUNCH_CARD);
            attendanceResultOfMonth.add(attendanceInfo);
        });
        //根据数据库打卡记录完善各个实体
        punchCardTimeRecord.forEach(oneRecord -> {
            //工作日考勤
            attendanceResultOfMonth.forEach(attendanceInfo -> {
                //判断打卡记录是否属于该实体
                boolean isBelongToTheEntity = attendanceInfo.getAttendanceDate().equals(oneRecord.get(attendanceDate));
                if (isBelongToTheEntity) {
                    String startTime = configMap.get(startTimeOfAfternoon);
                    String recordTimeStr = oneRecord.get(punchCardTime).toString();
                    //是否早上打卡
                    boolean isMorningPunchCard = DateUtil.isPrevious(recordTimeStr.substring(11), startTime);
                    //是否迟到
                    boolean isComeLate = DateUtil.isPrevious(configMap.get(workingTime), recordTimeStr.substring(11));
                    //是否早退
                    boolean isLeaveEarly = DateUtil.isPrevious(recordTimeStr.substring(11), configMap.get(closingTime));
                    if (isMorningPunchCard) {
                        attendanceInfo.setFirstPunchCardTime(recordTimeStr);
                        if (isComeLate) {
                            attendanceInfo.setEarlyStatus(AttendanceStatusEnum.BE_LATE);
                        } else {
                            attendanceInfo.setEarlyStatus(AttendanceStatusEnum.NORMAL);
                        }
                    } else {
                        attendanceInfo.setLastPunchCardTime(recordTimeStr);
                        if (isLeaveEarly) {
                            attendanceInfo.setEveningStatus(AttendanceStatusEnum.LEAVE_EARLY);
                        } else {
                            attendanceInfo.setEveningStatus(AttendanceStatusEnum.NORMAL);
                        }
                    }
                }
            });
        });

        //计算加班时长
        attendanceResultOfMonth.forEach(attendanceInfo -> {
            //上班打卡时间
            String firstPunchCardTime = attendanceInfo.getFirstPunchCardTime();
            //下班打卡时间
            String lastPunchCardTime = attendanceInfo.getLastPunchCardTime();
            //是否是工作日
            boolean isWorkDay = workDayOfMonth.contains(attendanceInfo.getAttendanceDate());
            //下班是否打卡
            boolean isPunchCardOffWork = lastPunchCardTime != null;
            //是否早退
            boolean isLeaveEarly = AttendanceStatusEnum.LEAVE_EARLY.equals(attendanceInfo.getEveningStatus());
            if (isWorkDay) {
                if (isPunchCardOffWork && !isLeaveEarly) {
                    attendanceInfo.setOverTime(DateUtil.getTimeDifference(configMap.get(closingTime), lastPunchCardTime.substring(11)));
                }
                attendanceResults.add(attendanceInfo);
            }
            //是否是休息日
            boolean isRestDay = !workDayOfMonth.contains(attendanceInfo.getAttendanceDate());
            if (isRestDay) {
                //休息日是否加班
                boolean isOverTimeOfRestDay = firstPunchCardTime != null && lastPunchCardTime != null;
                if (isOverTimeOfRestDay) {
                    attendanceInfo.setOverTime(DateUtil.getTimeDifference(firstPunchCardTime, lastPunchCardTime));
                    attendanceResults.add(attendanceInfo);
                }
            }
        });

        return attendanceResults;
    }
}
