package com.whjt.util;

public class NumberUtils {

    /**
     * 将数字型字符串转换为整型，进行字符为空的判断
     * 
     * @param integerStr
     * @return
     */
    public static int parseStringToInteger(String integerStr) {

        int parsedInteger = -1;

        if (!"".equals(integerStr) && null != integerStr) {

            parsedInteger = Integer.parseInt(integerStr);
        }

        return parsedInteger;
    }
}
