package com.handge.hr.manage.service.api.attendance;

import com.handge.hr.domain.entity.manage.web.request.attendance.GetAttendanceInfoParam;
import com.handge.hr.domain.entity.manage.web.response.attendance.GetAttendanceInfo;

import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/31
 * @Description:考勤管理接口
 */
public interface IAttendance {
    /**
     * 查询某人某月考勤信息
     * @param getAttendanceInfoParam
     * @return
     */
    List<GetAttendanceInfo> getAttendanceInfo(GetAttendanceInfoParam getAttendanceInfoParam);
}
