/**
 * Copyright 2020 mcu32.com(http://mcu32.com)
 */

package com.nbai.utils;

import io.swagger.models.auth.In;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lideguang
 * @since 2020/5/13
 */
public class DateUtil {

    public static final String formatStr_yyyyMMddHHmmssS_ = "yyyyMMddHHmmss";
    public static final String formatStr_yyyyMMddHHmmssS = "yyyy-MM-dd HH:mm:ss.S";
    public static final String formatStr_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String formatStr_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String formatStr_yyyyMMddHH = "yyyy-MM-dd HH";
    public static final String formatStr_yyyyMMdd = "yyyy-MM-dd";
    public static final String formatStr_yyyy = "yyyy";
    public static final String formatStr_yyyy_MM_dd = "yyyyMMdd";
    public static final String formatStr_yyyyMMddDelimiter = "-";

    public static String getYYYYMMDDHHMMSS(Date date){
        if (date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static String getCurrentTime() {
        return getYYYYMMDDHHMMSS(new Date());
    }

    public static Long dateToStamp(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(dateStr).getTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * String类型格式化时间转localDateTime
     * @param localDateTime string时间
     * @return localDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String localDateTime) {
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(formatStr_yyyyMMddHHmmss);
        return LocalDateTime.parse(localDateTime,dateTimeFormatter);
    }

    /**
     * String类型格式化时间转localDate
     * @param date string时间
     * @return localDate
     */
    public static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(formatStr_yyyyMMdd);
        return LocalDate.parse(date,dateTimeFormatter);
    }

    /**
     * 获取前一天日期
     * @return 前一天日期字符串
     */
    public static String yesterdayDate(){
        SimpleDateFormat sdf=new SimpleDateFormat(formatStr_yyyyMMdd);
        Date date = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH, -1);

        return sdf.format(now.getTime());
    }

    /**
     * 获取后x天日期
     * @param datePara 日期参数
     * @param x 天数
     * @return 后x天日期字符串
     */
    public static String afterDaysDate(String datePara,int x){

        DateFormat df=new SimpleDateFormat(formatStr_yyyyMMdd);
        Date date = null;
        try {
            date = df.parse(datePara);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        assert date != null;
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH, x);

        SimpleDateFormat sdf=new SimpleDateFormat(formatStr_yyyyMMdd);
        return sdf.format(now.getTime());
    }

    /**
     * 获取前七天日期
     * @return 前七天日期字符串
     */
    public static LocalDateTime sevenDaysAgoDate(){
        return LocalDateTime.now().minusDays(90);
        //SimpleDateFormat sdf=new SimpleDateFormat(formatStr_yyyyMMddHHmmss);
        //Date date = new Date();
        //Calendar now = Calendar.getInstance();
        //now.setTime(date);
        //now.add(Calendar.DAY_OF_MONTH, -6);
        //
        //return stringToLocalDateTime(sdf.format(now.getTime()));
    }

    /**
     * 获取前半个小时时间
     * @param time 时间
     * @return 处理后时间
     */
    public static LocalDateTime beforeHalfHourTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr_yyyyMMddHHmmss);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取毫秒数
        long longDate = date.getTime()-30*60*1000;
        Date resDate=new Date(longDate);
        return stringToLocalDateTime(sdf.format(resDate));
    }

    /**
     * 获取今日00:00:00 localDateTime
     * @return localDateTime
     */
    public static LocalDateTime todayBeginTime(){

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr_yyyyMMdd);
        String todayDate = sdf.format(new Date());

        String beginTime=todayDate+" 00:00:00";
        return stringToLocalDateTime(beginTime);
    }

    /**
     * 获取今日23:59:59 localDateTime
     * @return localDateTime
     */
    public static LocalDateTime todayEndTime(){

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr_yyyyMMdd);
        String todayDate = sdf.format(new Date());

        String endTime = todayDate+" 23:59:59";
        return stringToLocalDateTime(endTime);
    }

    /**
     * 获取昨天00:00:00 localDateTime
     * @return localDateTime
     */
    public static LocalDateTime yesterdayBeginTime(){

        String beginTime=yesterdayDate()+" 00:00:00";
        return stringToLocalDateTime(beginTime);
    }

    /**
     * 获取前x天日期
     * @param x 天数
     * @return string日期
     */
    public  static String beforeDaysDate(int x){
        Date date = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH, x);

        SimpleDateFormat sdf=new SimpleDateFormat(formatStr_yyyyMMdd);
        return sdf.format(now.getTime());
    }

    /**
     * 计算时间差
     * @param beginTimePara 开始时间
     * @param endTimePara 结束时间
     * @return 相差秒数
     */
    public  static int timeSecond(String beginTimePara, String endTimePara){
        //获取排队总时间
        DateFormat simpleDateFormat = new SimpleDateFormat(formatStr_yyyyMMddHHmmss);

        try {
            Date beginTime = simpleDateFormat.parse(beginTimePara);
            Date endTime = simpleDateFormat.parse(endTimePara);
            return (int)( endTime.getTime()- beginTime.getTime())/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
