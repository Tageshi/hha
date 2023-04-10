package com.bin.common.core.context;

import cn.dev33.satoken.stp.StpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tageshi
 * @date 2023/3/15 16:29
 */
public class BaseContextHandler {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_username = "currentUserName";
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.put(key, value);
        threadLocal.set(map);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 传入的Object类型的数据，判断是否为空，不为空则转换为T类型的数据
     * @param value
     * @return
     */
    private static <T> T returnObjectValue(Object value) {
        return value == null ? null : (T) value;
    }

    public static void remove() {
        threadLocal.remove();
    }


    public static void setUserID(Integer userID) {
        set(CONTEXT_KEY_USER_ID, userID);
    }

    public static Long getUserID() {
        Object value = StpUtil.getLoginIdAsLong();
        return returnObjectValue(value);
    }

    public static void setUserName(String userName) {
        set(CONTEXT_KEY_username, userName);
    }
    public static String getUserName() {
        Object value = get(CONTEXT_KEY_username);
        return returnObjectValue(value);
    }
}
