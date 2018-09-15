package com.handge.hr.behavior.service.impl.common;

import com.handge.hr.domain.entity.behavior.web.request.common.IpsByNumberParam;
import com.handge.hr.domain.entity.behavior.web.request.common.UserInfoByNameParam;
import com.handge.hr.domain.entity.behavior.web.response.common.UserInfo;
import com.handge.hr.behavior.service.api.common.ICommon;
import com.handge.hr.domain.repository.mapper.BehaviorEntityDeviceBasicMapper;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DaLu Guo on 2018/5/29.
 */
@Component
public class CommonImpl implements ICommon {
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    BehaviorEntityDeviceBasicMapper behaviorEntityDeviceBasicMapper;

    @Override
    public Object listUserInfoByName(UserInfoByNameParam userInfoByNameParam) {
        List<UserInfo> userInfoList = entityEmployeeMapper.listUserInfoByName(userInfoByNameParam.getName());
        return userInfoList;
    }

    @Override
    public Object getIpsByNumber(IpsByNumberParam ipsByNumberParam) {
        List<String> ipList = behaviorEntityDeviceBasicMapper.getIpsByNumber(ipsByNumberParam.getNumber());
        return ipList;
    }
}
