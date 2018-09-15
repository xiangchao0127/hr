package com.handge.hr.manage.common.utils;

import java.util.UUID;

public class UuidUtils {
    public static String getUUid(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
