package com.handge.hr.domain.entity.behavior.web.request.base;


import com.handge.hr.domain.entity.behavior.web.response.UserContext;

import java.io.Serializable;

/**
 * Created by DaLu Guo on 2018/5/31.
 */
public class UserContextParam implements Serializable {
    private UserContext userContext;

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public String toString() {
        return "UserContextParam{" +
                "userContext=" + userContext +
                '}';
    }
}
