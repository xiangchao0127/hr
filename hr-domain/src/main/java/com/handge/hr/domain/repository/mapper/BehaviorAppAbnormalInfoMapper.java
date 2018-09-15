package com.handge.hr.domain.repository.mapper;

import com.handge.hr.common.enumeration.behavior.EmployeeStatusEnum;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.behavior.web.response.monitor.Alarm;
import com.handge.hr.domain.repository.pojo.BehaviorAppAbnormalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/8
 * @Description:
 */
@Mapper
public interface BehaviorAppAbnormalInfoMapper extends CommonMapper<BehaviorAppAbnormalInfo,Integer>{
    /**
     * 根据ips查询报警信息
     */
    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetAlarmInfoByIds")
    ArrayList<Alarm> getAlarmInfoByIds(@Param("startTime") final String startTime,@Param("endTime") final String endTime,@Param("ips")ArrayList<String> ips);

    class UserSqlBuilder{
        public static String buildGetAlarmInfoByIds(@Param("ips") final List<String> ips ){
            return new SQL(){{
                SELECT("localIP,appName,accessTime,appClass");
                FROM(" behavior_app_abnormal_info");
                WHERE(" accessTime >= #{startTime}");
                AND();
                WHERE("accessTime <= #{endTime}");
                AND();
                WHERE("attr = '0' ");
                if (ips.size() > 0) {
                    AND();
                    WHERE("localIP in "+ ips);
                }
                ORDER_BY("accessTime DESC");
            }}.toString();
        }
    }
}
