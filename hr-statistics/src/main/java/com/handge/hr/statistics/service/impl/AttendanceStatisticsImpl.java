package com.handge.hr.statistics.service.impl;

import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.enumeration.manage.AttendanceStatusEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.domain.repository.mapper.AttendanceConfigMapper;
import com.handge.hr.domain.repository.mapper.AttendanceFlowRecordMapper;
import com.handge.hr.domain.repository.mapper.AttendanceStatisticsMapper;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import com.handge.hr.domain.repository.pojo.AttendanceConfig;
import com.handge.hr.domain.repository.pojo.AttendanceStatistics;
import com.handge.hr.statistics.entity.request.AddAttendanceStatisticsParam;
import com.handge.hr.statistics.service.api.IAttendanceStatistics;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuqian
 * @date 2018/9/5
 * @Description:考勤统计实现类
 */
@Component
public class AttendanceStatisticsImpl implements IAttendanceStatistics {
    /**
     * 一次未打卡 = 0.5次未到岗
     */
    private static final float NOT_PUNCH_CARD_OF_NO_ARRIVAL = 0.5f;

    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    AttendanceFlowRecordMapper attendanceFlowRecordMapper;
    @Autowired
    AttendanceConfigMapper configMapper;
    @Autowired
    IAttendanceStatistics statistics;
    @Autowired
    AttendanceStatisticsMapper attendanceStatisticsMapper;

    @Override
    public Map<String, Map<String, String>> getAvgStaffOfMonth() {
        //年，月，月平均人数map
        Map<String, Map<String, String>> yearMonthCountMap = new HashMap<>(10);
        //获取公司成立日期
        String startTime = entityEmployeeMapper.getEstablishedDate();
        String firstDayOfMonth = DateUtil.getFirstDayOfMonth(startTime);
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(startTime);
        //获得当前系统月的第一天
        String nowDate = DateUtil.date2Str(new Date(), DateFormatEnum.DAY);
        String firstDayOfThisMonth = DateUtil.getFirstDayOfMonth(nowDate);
        do {
            yearMonthCountMap = getAvgMonthStaff(firstDayOfMonth, lastDayOfMonth, yearMonthCountMap);
            //获取下个月的第一天
            firstDayOfMonth = DateUtil.getFirstDayOfNextMonth(firstDayOfMonth);
            //获取下个月的最后一天
            lastDayOfMonth = DateUtil.getLastDayOfNextMonth(lastDayOfMonth);
        } while (DateUtil.compareDate(firstDayOfThisMonth, firstDayOfMonth));
        return yearMonthCountMap;
    }

    @Override
    public Object addAttendanceStatistics(AddAttendanceStatisticsParam addAttendanceStatisticsParam) {
        AttendanceStatistics attendanceStatistics = new AttendanceStatistics();
        //公司成立以来各月的月平均人数
        Map<String, Map<String, String>> avgStaffOfMonth = statistics.getAvgStaffOfMonth();
        //各员工某月的考勤状态
        Map<String, List<String>> staffAttendanceStatusOfMonth = getStaffAttendanceStatusOfMonth(addAttendanceStatisticsParam);
        //未打卡总次数
        int noPunchCardTotalTimes = 0;
        //未到岗次数
        int noArrivalTimes = 0;
        //迟到总次数
        int lateTotalTimes = 0;
        //早退总次数
        int leaveEarlyTotalTimes = 0;
        for (Map.Entry<String, List<String>> entry : staffAttendanceStatusOfMonth.entrySet()) {
            for (String status : entry.getValue()) {
                //"02"、"03"、"04"、"05" 考勤状态对应的枚举值
                switch (status) {
                    case "02":
                        lateTotalTimes += 1;
                        break;
                    case "03":
                        noPunchCardTotalTimes += 1;
                        break;
                    case "04":
                        leaveEarlyTotalTimes += 1;
                        break;
                    case "05":
                        noArrivalTimes += 1;
                        break;
                    default:
                        break;
                }
            }
        }
        //未到岗人数
        int noArrivalTotalPeople = 0;
        //迟到总人数
        int lateTotalPeople = 0;
        //早退总人数
        int leaveEarlyTotalPeople = 0;
        for (Map.Entry<String, List<String>> entry : staffAttendanceStatusOfMonth.entrySet()) {
            boolean isExistNotPunchCard = entry.getValue().contains(AttendanceStatusEnum.NOT_PUNCH_CARD.getValue());
            boolean isExistNotArrival = entry.getValue().contains(AttendanceStatusEnum.NOT_ARRIVAL.getValue());
            boolean isExistLate = entry.getValue().contains(AttendanceStatusEnum.BE_LATE.getValue());
            boolean isExistLeaveEarly = entry.getValue().contains(AttendanceStatusEnum.LEAVE_EARLY.getValue());
            // TODO: 2018/9/7 如果一天有一次未打卡不计入未到岗人数，则条件改为【isExistNotArrival】
            if (isExistNotPunchCard || isExistNotArrival) {
                noArrivalTotalPeople += 1;
            }
            if (isExistLate) {
                lateTotalPeople += 1;
            }
            if (isExistLeaveEarly) {
                leaveEarlyTotalPeople += 1;
            }
        }
        //未到岗总次数(该天上下班均未打卡记录，记【1次】未到岗；该天上下班有一次未打卡，记【0.5次--可配置】未到岗)
        float noArrivalTotalTimes = noArrivalTimes / 2 + noPunchCardTotalTimes * NOT_PUNCH_CARD_OF_NO_ARRIVAL;

        //需要查询考勤信息的年月，形如:"2018-08"
        String yearMonth = addAttendanceStatisticsParam.getMonth();
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(6);
        int averagePeopleOfMonth = Integer.valueOf(avgStaffOfMonth.get(year).get(month));

        attendanceStatistics.setId(UUID.randomUUID().toString().replace("-", ""));
        attendanceStatistics.setGmtCreate(new Date());
        //统计年月
        attendanceStatistics.setStatisticsDate(addAttendanceStatisticsParam.getMonth());
        //未到岗总次数
        attendanceStatistics.setNoArrivalTimesCount(BigDecimal.valueOf(noArrivalTotalTimes));
        //未到岗总人数
        attendanceStatistics.setNoArrivalPersonCount(noArrivalTotalPeople);
        //未到岗率
        attendanceStatistics.setNoArrivalRate(BigDecimal.valueOf((float)noArrivalTotalPeople / (float)averagePeopleOfMonth));
        //迟到总次数
        attendanceStatistics.setLateTimesCount(lateTotalTimes);
        //迟到总人数
        attendanceStatistics.setLatePersonCount(lateTotalPeople);
        //迟到率
        attendanceStatistics.setLateRate(BigDecimal.valueOf((float)lateTotalPeople / (float)averagePeopleOfMonth));
        //早退总次数
        attendanceStatistics.setLeaveEarlyTimesCount(leaveEarlyTotalTimes);
        //早退总人数
        attendanceStatistics.setLeaveEarlyPersonCount(leaveEarlyTotalPeople);
        //早退率
        attendanceStatistics.setLeaveEarlyRate(BigDecimal.valueOf((float)leaveEarlyTotalPeople / (float)averagePeopleOfMonth));

        attendanceStatisticsMapper.insert(attendanceStatistics);
        return 1;
    }

    /**
     * 统计某年某月的月平均人数
     *
     * @param firstDayOfMonth 某个月的第一天
     * @param lastDayOfMonth  某个月的最后一天
     * @param map
     * @return
     */
    private Map<String, Map<String, String>> getAvgMonthStaff(String firstDayOfMonth, String lastDayOfMonth, Map<String, Map<String, String>> map) {
        //月初在职人数
        int startMonth = 0;
        //月末在职人数
        int endMonth = 0;
        try {
            startMonth = entityEmployeeMapper.getEarlyMonthStaffCount(DateUtil.str2Date(DateFormatEnum.DAY, firstDayOfMonth));
            endMonth = entityEmployeeMapper.getEndOfMonthStaffCount(DateUtil.str2Date(DateFormatEnum.DAY, lastDayOfMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //月平均人数，如果结果为小数则采用向上取整
        int avgMonthStaff = (int) Math.ceil((double) (startMonth + endMonth) / 2);
        //年
        String year = firstDayOfMonth.substring(0, 4);
        //月
        String month = String.valueOf(Integer.parseInt(firstDayOfMonth.substring(5, 7)));
        if (map.containsKey(year)) {
            map.get(year).put(month, String.valueOf(avgMonthStaff));
        } else {
            Map<String, String> monthCountMap = new HashMap<>(12);
            monthCountMap.put(month, String.valueOf(avgMonthStaff));
            map.put(year, monthCountMap);
        }
        return map;
    }

    /**
     * 统计各员工某月考勤信息
     *
     * @param addAttendanceStatisticsParam
     * @return
     */
    private Map<String, List<String>> getStaffAttendanceStatusOfMonth(AddAttendanceStatisticsParam addAttendanceStatisticsParam) {
        String workingTime = "上班时间";
        String closingTime = "下班时间";
        String workingDay = "工作日";
        String startTimeOfAfternoon = "下午开始时间";
        //打卡时间
        String punchCardTime = "record_time";
        //考勤日期
        String attendanceDate = "attendance_date";
        //工号
        String jobNumber = "job_number";
        //一天打卡次数
        String punchCardTimesOfDay = "count";

        Map<String, List<String>> staffAttendanceStatusOfMonth = new HashMap<>(100);

        //需要查询考勤信息的年月，形如:"2018-08"
        String yearMonth = addAttendanceStatisticsParam.getMonth();
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(6));

        //获取数据库考勤参数配置
        List<AttendanceConfig> attendanceConfigs = configMapper.selectAll();
        Map<String, String> configMap = new HashMap<>(10);
        attendanceConfigs.forEach(config -> configMap.put(config.getName(), config.getValue()));

        //获取配置的工作日
        String workDay = configMap.get(workingDay);
        String[] split = workDay.split(",");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());

        //某月工作日日期集合
        List<String> workDayOfMonth = DateUtil.getWorkDayOfMonth(year, month, collect);
        //某月工作日天数
        int workDatCount = workDayOfMonth.size();

        //某个月所有员工打卡记录
        List<Map<String, Object>> punchCardTimeRecordOfMonth = attendanceFlowRecordMapper.getPunchCardTimeRecordOfMonth(addAttendanceStatisticsParam.getMonth());
        //员工一个月一天只打一次卡的次数
        Map<String, Integer> punchCardOnceTimesOfDayMap = new HashMap<>(100);
        punchCardTimeRecordOfMonth.forEach(record -> {
            String attendanceTime = record.get(attendanceDate).toString();
            boolean isWorkDay = workDayOfMonth.contains(attendanceTime);
            if (isWorkDay && record.get(jobNumber) != null) {
                AttendanceStatusEnum punchCardStatus;
                String number = record.get(jobNumber).toString();
                String startTime = configMap.get(startTimeOfAfternoon);
                String recordTimeStr = record.get(punchCardTime).toString();
                //是否早上打卡
                boolean isMorningPunchCard = DateUtil.isPrevious(recordTimeStr.substring(11), startTime);
                //是否迟到
                boolean isComeLate = DateUtil.isPrevious(configMap.get(workingTime), recordTimeStr.substring(11));
                //是否早退
                boolean isLeaveEarly = DateUtil.isPrevious(recordTimeStr.substring(11), configMap.get(closingTime));
                if (isMorningPunchCard) {
                    if (isComeLate) {
                        punchCardStatus = AttendanceStatusEnum.BE_LATE;
                    } else {
                        punchCardStatus = AttendanceStatusEnum.NORMAL;
                    }
                } else {
                    if (isLeaveEarly) {
                        punchCardStatus = AttendanceStatusEnum.LEAVE_EARLY;
                    } else {
                        punchCardStatus = AttendanceStatusEnum.NORMAL;
                    }
                }
                if (staffAttendanceStatusOfMonth.containsKey(number)) {
                    List<String> punchCardStatusList = staffAttendanceStatusOfMonth.get(number);
                    punchCardStatusList.add(punchCardStatus.getValue());
                } else {
                    List<String> statusList = new ArrayList<>();
                    statusList.add(punchCardStatus.getValue());
                    staffAttendanceStatusOfMonth.put(number, statusList);
                }
                //一天打一次卡
                boolean isPunchCardOnceOfDay = Integer.valueOf(record.get(punchCardTimesOfDay).toString()) == 1;
                if (isPunchCardOnceOfDay) {
                    if (punchCardOnceTimesOfDayMap.containsKey(number)) {
                        Integer old = punchCardOnceTimesOfDayMap.get(number);
                        punchCardOnceTimesOfDayMap.put(number, old + 1);
                    } else {
                        punchCardOnceTimesOfDayMap.put(number, 1);
                    }
                }
            }
        });

        //一天只打卡一次，则另一次考勤状态设置为【未打卡】
        for (Map.Entry<String, Integer> entry : punchCardOnceTimesOfDayMap.entrySet()) {
            //一天打一次卡的次数
            int timesOfPunchCardOnce = punchCardOnceTimesOfDayMap.get(entry.getKey());
            for (int i = 0; i < timesOfPunchCardOnce; i++) {
                //设置未打卡
                staffAttendanceStatusOfMonth.get(entry.getKey()).add(AttendanceStatusEnum.NOT_PUNCH_CARD.getValue());
            }
        }

        //工作日早上和下午均未打卡，将上班和下班考勤状态设置为【未到岗】
        staffAttendanceStatusOfMonth.values().forEach(statusList -> {
            //工作日均未打卡的次数
            int noPunchCardTimesOfWorkDay = workDatCount * 2 - statusList.size();
            for (int i = 0; i < noPunchCardTimesOfWorkDay; i++) {
                statusList.add(AttendanceStatusEnum.NOT_ARRIVAL.getValue());
            }
        });
        return staffAttendanceStatusOfMonth;
    }

}
