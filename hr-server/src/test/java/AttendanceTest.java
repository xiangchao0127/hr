import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.domain.entity.manage.web.request.attendance.GetAttendanceInfoParam;
import com.handge.hr.domain.entity.manage.web.response.attendance.GetAttendanceInfo;
import com.handge.hr.domain.repository.mapper.AttendanceFlowRecordMapper;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import com.handge.hr.domain.repository.pojo.AttendanceFlowRecord;
import com.handge.hr.manage.service.api.attendance.IAttendance;
import com.handge.hr.server.Application;
import com.handge.hr.statistics.entity.request.AddAttendanceStatisticsParam;
import com.handge.hr.statistics.service.api.IAttendanceStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuqian
 * @date 2018/9/5
 * @Description:考勤管理
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AttendanceTest {
    @Autowired
    IAttendance attendance;
    @Autowired
    IAttendanceStatistics attendanceStatistics;

    @Test
    public void getattendanceInfoTest() {
        GetAttendanceInfoParam attendanceInfoParam = new GetAttendanceInfoParam();
        attendanceInfoParam.setMonth("2016-07");
        attendanceInfoParam.setNumber("012");
        List<GetAttendanceInfo> attendanceInfo = attendance.getAttendanceInfo(attendanceInfoParam);
        attendanceInfo.forEach(o -> {
            System.out.println(o.toString());
            System.out.println("=========================");
        });
    }

    @Test
    public void test1() {
        Map<String, Map<String, String>> avgStaffOfMonth = attendanceStatistics.getAvgStaffOfMonth();
        for (Map.Entry<String,Map<String,String>> entry1:avgStaffOfMonth.entrySet()){
            for(Map.Entry<String,String> entry2:entry1.getValue().entrySet()){
                System.out.println(entry1.getKey()+"-"+entry2.getKey()+":"+entry2.getValue());
            }
        }
    }

    @Test
    public void test2() {
        /*String establishedDate = entityEmployeeMapper.getEstablishedDate();
        System.out.println(establishedDate);*/
        /*AddAttendanceStatisticsParam attendanceStatisticsParam = new AddAttendanceStatisticsParam();
        attendanceStatisticsParam.setMonth("2018-07");
        attendanceStatistics.addAttendanceStatistics(attendanceStatisticsParam);*/
    }
}
