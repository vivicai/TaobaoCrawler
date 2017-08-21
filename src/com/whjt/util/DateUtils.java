package com.whjt.util;

public class DateUtils {

    /**
     * 根据月份和天得到星座 未对负数和超过月份最大天数进行异常判断
     * 
     * @param month
     * @param day
     * @return
     */
    public static String getConstellationByMonthAndDay(int month, int day) {

        String constellation = "";

        String[] consArr = { "魔羯座", "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座",
                "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };
        // 两个星座分割日，即上面数组对应星座索引的最后一天
        int[] dayArr = { 20, 19, 20, 20, 21, 21, 22, 23, 22, 23, 22, 21 };

        /*
         * dayArr数组索引+1即代表月份，索引的数值即为天
         * 用传入的月份-1，找到对应的天数，如果天数小于等于数组中的天数，则利用月份-1作为索引中到星座数组中取星座
         * 如果天数大于数组中的天数，则利用月份作为索引中到星座数组中取星座
         */
        int index = month - 1;

        if (day <= dayArr[index]) {

        } else {

            index = month;
        }

        // 返回索引指向的星座string
        constellation = consArr[index];

        return constellation;
    }
}
