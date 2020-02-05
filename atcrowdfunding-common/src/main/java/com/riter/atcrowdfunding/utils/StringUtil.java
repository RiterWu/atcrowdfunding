package com.riter.atcrowdfunding.utils;

/**
 * 判断字符串是否为空
 */
public class StringUtil {

    public static Boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
}
