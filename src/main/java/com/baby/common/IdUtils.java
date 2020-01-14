package com.baby.common;

import java.util.UUID;

/**
 * ID生成器
 */
public class IdUtils {
    /**
     * 生成随机32位UUID
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

}
