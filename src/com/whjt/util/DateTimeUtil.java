package com.whjt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtil {

    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 私有构造方法，禁止对该类进行实例化
     */
    private DateTimeUtil() {
    }

    /**
     * 返回秒数的时间戳
     * 
     * @param cal
     * @return
     */
    public static long getCurrentTimeStamp() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    /**
     * 将字符串日期转换为 秒时间戳
     * 
     * @param date
     * @param pattern
     *            "yyyy-MM-dd HH:mm:ss"
     * @return
     * @throws ParseException
     */
    public static long getCurrentTimeStamp(String date, String pattern)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = sdf.parse(date);
        return d.getTime() / 1000;
    }

    /**
     * 返回日期时间
     * 
     * @param timestamp
     *            秒时间戳
     * @return
     */
    public static String getDatetime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        String date = "";
        try {
            date = sdf.format(timestamp * 1000);
        } catch (Exception e) {
            return null;
        }
        return date.toString();
    }

    /**
     * 得到系统当前日期时间
     * 
     * @return 当前日期时间
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到用缺省方式格式化的当前日期 缺省的日期显示格式： yyyy-MM-dd
     * 
     * @return 当前日期
     */
    public static String getDate() {
        return getDateTime(DEFAULT_DATE_FORMAT);
    }

    /**
     * 得到用缺省方式格式化的当前日期及时间 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     * 
     * @return 当前日期及时间
     */
    public static String getDateTime() {
        return getDateTime(DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 得到系统当前日期及时间，并用指定的方式格式化
     * 
     * @param pattern
     *            显示格式
     * @return 当前日期及时间
     */
    public static String getDateTime(String pattern) {
        Date datetime = Calendar.getInstance().getTime();
        return getDateTime(datetime, pattern);
    }

    /**
     * 得到用指定方式格式化的日期
     * 
     * @param date
     *            需要进行格式化的日期
     * @param pattern
     *            显示格式
     * @return 日期时间字符串
     */
    public static String getDateTime(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 得到当前年份
     * 
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 得到当前月份
     * 
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        // 用get得到的月份数比实际的小1，需要加上
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前日
     * 
     * @return 当前日
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 得到当前时
     * 
     * @return 当前时
     */
    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到当前分
     * 
     * @return 当前分
     */
    public static int getCurrentMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 得到当前周几
     * 
     * @return 当前周几
     */
    public static int getCurrentWeekDay() {
        int weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (weekDay) {
        case 1:
            weekDay = 7;
            break;
        case 2:
            weekDay = 1;
            break;
        case 3:
            weekDay = 2;
            break;
        case 4:
            weekDay = 3;
            break;
        case 5:
            weekDay = 4;
            break;
        case 6:
            weekDay = 5;
            break;
        case 7:
            weekDay = 6;
            break;
        default:
            break;
        }
        return weekDay;
    }

    /**
     * 得到当前凌晨时间戳
     * 
     * @return 当前周几
     */
    public static long getCurrentWeeHoursTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
     * 
     * @param days
     *            增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(int days) {
        return add(getNow(), days, Calendar.DATE);
    }

    /**
     * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。
     * 
     * @param date
     *            基准日期
     * @param days
     *            增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
     * 
     * @param months
     *            增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(int months) {
        return add(getNow(), months, Calendar.MONTH);
    }

    /**
     * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
     * 
     * @param date
     *            基准日期
     * @param months
     *            增加的月份数
     * 
     * @return 增加以后的日期
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }

    /**
     * 内部方法。为指定日期增加相应的天数或月数
     * 
     * @param date
     *            基准日期
     * @param amount
     *            增加的数量
     * @param field
     *            增加的单位，年，月或者日
     * @return 增加以后的日期
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     * 
     * @param one
     *            第一个日期数，作为基准
     * @param two
     *            第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
     * 
     * @param one
     *            第一个日期数，作为基准
     * @param two
     *            第二个日期数，作为比较
     * @return 两个日期相差月份数
     */
    public static int diffMonths(Date one, Date two) {
        Calendar calendar = Calendar.getInstance();
        // 得到第一个日期的年分和月份数
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONDAY);
        // 得到第二个日期的年份和月份
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONDAY);
        return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
    }

    /**
     * 将一个字符串用给定的格式转换为日期类型。<br>
     * 注意：如果返回null，则表示解析失败
     * 
     * @param datestr
     *            需要解析的日期字符串
     * @param pattern
     *            日期字符串的格式，默认为“yyyy-MM-dd”的形式
     * @return 解析后的日期
     */
    public static Date parse(String datestr, String pattern) {
        Date date = null;
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date parseForGMT(String datestr, String pattern) {
        Date date = null;
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(DateTimeUtil.getGMTTimeZone());
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 返回本月的最后一天
     * 
     * @return 本月最后一天的日期
     */
    public static Date getMonthLastDay() {
        return getMonthLastDay(getNow());
    }

    /**
     * 返回给定日期中的月份中的最后一天
     * 
     * @param date
     *            基准日期
     * @return 该月最后一天的日期
     */
    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将日期设置为下一月第一天
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, 1);
        // 减去1天，得到的即本月的最后一天
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(DateTimeUtil.getDateTime(
                DateTimeUtil.addDays(new Date(), -180), "yyyy-MM-dd"));
        // String sss = getDateTime("yyyy年MM月dd日");
        String test = "2003-1-31";
        Date date;
        try {
            date = parse(test, "");

            System.out.println("得到当前日期 － getDate():" + DateTimeUtil.getDate());
            System.out.println("得到当前日期时间 － getDateTime():"
                    + DateTimeUtil.getDateTime());

            System.out.println("得到当前年份 － getCurrentYear():"
                    + DateTimeUtil.getCurrentYear());
            System.out.println("得到当前月份 － getCurrentMonth():"
                    + DateTimeUtil.getCurrentMonth());
            System.out.println("得到当前日子 － getCurrentDay():"
                    + DateTimeUtil.getCurrentDay());

            System.out.println("解析 － parse(" + test + "):"
                    + getDateTime(date, "yyyy-MM-dd"));

            System.out.println("自增月份 － addMonths(3):"
                    + getDateTime(addMonths(3), "yyyy-MM-dd"));
            System.out.println("增加月份 － addMonths(" + test + ",3):"
                    + getDateTime(addMonths(date, 3), "yyyy-MM-dd"));
            System.out.println("增加日期 － addDays(" + test + ",3):"
                    + getDateTime(addDays(date, 3), "yyyy-MM-dd"));
            System.out.println("自增日期 － addDays(3):"
                    + getDateTime(addDays(3), "yyyy-MM-dd"));

            System.out.println("比较日期 － diffDays():"
                    + DateTimeUtil.diffDays(DateTimeUtil.getNow(), date));
            System.out.println("比较月份 － diffMonths():"
                    + DateTimeUtil.diffMonths(DateTimeUtil.getNow(), date));

            System.out.println("得到" + test + "所在月份的最后一天:"
                    + getDateTime(getMonthLastDay(date), "yyyy-MM-dd"));
            System.out.println(DateTimeUtil.getCurrentWeekDay());
            System.out.println(DateTimeUtil.getCurrentDay());
            System.out.println(DateTimeUtil.getCurrentMinute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将本机时间转换为GMT时区
     * 
     * @param d
     * @return
     */
    public static String toGMTTime(Date d) {
        SimpleDateFormat outputFormat = new SimpleDateFormat(
                DEFAULT_DATETIME_FORMAT, Locale.US);
        outputFormat.setTimeZone(getGMTTimeZone());
        return outputFormat.format(d);
        // return outputFormat.getCalendar().getTime();

    }

    /**
     * 将GMT时区转换为本机时区
     * 
     * @param d
     * @return
     */
    public static Date toLocalTime(Date d) {
        SimpleDateFormat outputFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat outputFormat1 = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);
        outputFormat.setTimeZone(getLocalTimeZone());

        try {

            return outputFormat1.parse(outputFormat.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        // return outputFormat.getCalendar().getTime();
    }

    /****
     * 获取本机时区
     * 
     * @return
     */
    public static TimeZone getLocalTimeZone() {
        // 获取本地默认时区对象
        return TimeZone.getDefault();
    }

    /**
     * 获取GMT时区
     * 
     * @return
     */
    public static TimeZone getGMTTimeZone() {
        return TimeZone.getTimeZone("GMT");
    }

    /**
     * 取得指定日期所在周的第一天
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    public static long getCurrentTimeStamp(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        Date d = sdf.parse(date);
        return d.getTime() / 1000;
    }

    public static int getDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = "";
        try {
            date = sdf.format(timestamp * 1000);
        } catch (Exception e) {
            return 0;
        }
        return Integer.parseInt(date);
    }

    public static int getMonth(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = "";
        try {
            date = sdf.format(timestamp * 1000);
        } catch (Exception e) {
            return 0;
        }
        return Integer.parseInt(date);
    }

    public static String getShortDate() {
        Date myDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        String date = formatter.format(myDate);
        return date;
    }

    /***
     * 获取离当前最近的指定时间的时间戳（hours=12.5，即返回下一个12:30时刻的时间戳）
     * 
     * @param hours
     * @return
     */
    public static long getTime(double hours) {
        String date = DateTimeUtil.getDateTime(new Date(),
                DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        long time = DateTimeUtil.parse(date, DateTimeUtil.DEFAULT_DATE_FORMAT)
                .getTime() + (long) (hours * 3600 * 1000);
        if (System.currentTimeMillis() > time) {
            date = DateTimeUtil.getDateTime(
                    DateTimeUtil.addDays(new Date(), 1),
                    DateTimeUtil.DEFAULT_DATE_FORMAT);
            time = DateTimeUtil.parse(date, DateTimeUtil.DEFAULT_DATE_FORMAT)
                    .getTime() + (long) (hours * 3600 * 1000);
        }

        return time;
    }

}
