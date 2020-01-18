package com.xjtu.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate {
    public static final int MORNING = 0;
    public static final int NOON = 1;
    public static final int AFTERNOON = 2;
    public static final int EVENING = 3;
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int DAY = 3;
    public static final int HOUR = 4;
    public int year;
    public int month;
    public int day;
    public int hour;

    // 无参数
    // 获取现在时间
    public MyDate() {
        Calendar calendar = Calendar.getInstance();
        this.year =calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH)+1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
    }
    // SQL中储存的时间字符串
    public MyDate(String date){
        this.year = Integer.parseInt(date.substring(0,3));
        this.month = Integer.parseInt(date.substring(4,5));
        this.day = Integer.parseInt(date.substring(6,7));
        this.hour = Integer.parseInt(date.substring(8,9));
    }

    //将此对象设置为当前时间
    public void refresh(){
        Calendar calendar = Calendar.getInstance();
        this.year =calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH)+1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
    }

    //将此事件对象转换成SQL中的时间语句
    public String getTimeDescription(){
        String timeDescriptive ;
        String str_year = String.valueOf(this.year);
        String str_mon = String.valueOf(this.month);
        String str_day = String.valueOf(this.day);
        String str_hour = String.valueOf(this.hour);
        if(str_mon.length()<2){
            str_mon = "0" + str_mon;
        }
        if (str_day.length()<2){
            str_day = "0" + str_day;
        }
        if(str_hour.length()<2){
            str_hour = "0" +str_hour;
        }
        timeDescriptive =str_year+str_mon+str_day+str_hour+defineHour();
        return timeDescriptive;
    }
    // 对于时间的描述
    // 用在时间语句的最后一位
    // 表示上午，中午，下午，或晚上
    public int defineHour(){
        if(this.hour>=0&&this.hour<=10)
            return MORNING;
        else if(this.hour>=12&&this.hour<=14)
            return NOON;
        else if(this.hour>=15&&this.hour<=18)
            return AFTERNOON;
        else
            return EVENING;
    }
    //用来判断目标时间语句所代表的时间与现在的MyDate对象时间相隔天数
    public int compareRecent(String date) throws ParseException {
        MyDate myDate_cmp = new MyDate(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhh");
        Date date_past = simpleDateFormat.parse(date.substring(0,10));
        Date date_now = simpleDateFormat.parse(this.getTimeDescription().substring(0,10));
        return daysBetween(date_past,date_now);
    }

    /**
     * using Year,Month and Day to calculate the days between two Date instances
     * @param date_past
     * @param date_now
     * @return days
     */
    private int  daysBetween(Date date_past , Date date_now){
        int totalDays = 0;
        totalDays += daysBetweenInYears(date_past,date_now) ;
        totalDays += daysBetweenInMonths(date_past,date_now) ;
        totalDays += daysBetweenInDays(date_past,date_now);
        return totalDays;

    }
    /**
     * Calculate the days of total years between two Daye instance
     * @param date_past
     * @param date_now
     * @return int representing the days in total
     */
    private static int daysBetweenInYears(Date date_past,Date date_now){
        int year_past = getInDate(date_past,YEAR);
        int year_now = getInDate(date_now,YEAR);
        int leapYear = 0;
        for(int i = year_past+1;i<year_now;i++){
            if (i % 4 ==0 &&i%400!=0)
                leapYear++;
        }
        return (year_now - year_past - 1) > 0 ? (year_now-year_past-1)*365+leapYear : 0;
    }

    /**
     * Calculates how many days are there between two Date instances Ignoring their Year
     * @param date_past
     * @param date_now
     * @return days between two Date ignoring Year
     */
    private static int  daysBetweenInMonths(Date date_past,Date date_now){

        int months_past = getInDate(date_past,MONTH);
        int months_now = getInDate(date_now,MONTH);
        if(getInDate(date_past,YEAR)==getInDate(date_now,YEAR)){
            int days_total = 0;
            for(int i = months_past+1;i< months_now;i++){
                days_total+=getDaysInMonth(i,getInDate(date_past,YEAR));
            }
            return  days_total;
        }
        else if (getInDate(date_past,YEAR)<getInDate(date_now,YEAR)){
            int days_past =0;
            int days_now = 0;
            for(int i= months_past+1;i<=12;i++){
                days_past+=getDaysInMonth(i,getInDate(date_past,YEAR));
            }
            for(int i = months_now-1;i>=1;i++){
                days_now+=getDaysInMonth(i,getInDate(date_now,YEAR));
            }
            return days_past+days_now;
        }
        return -1;
    }

    private static int daysBetweenInDays( Date date_past , Date date_now ){
        int days_past = getInDate(date_past,DAY);
        int days_now = getInDate(date_now,DAY);
        if(getInDate(date_past,YEAR) ==getInDate(date_now,YEAR)&&getInDate(date_past,MONTH)==getInDate(date_past,MONTH)){
            return days_now-days_past;
        }
        else {
            return getDaysInMonth(getInDate(date_past,MONTH),getInDate(date_past,YEAR)) - days_past + days_now;
        }


    }
    /**
     * This function gets the data of the target Date in the requested field
     * @param date
     * @param field
     * @return Year or Month or Day or Hour in int
     * if the requested field doesn't exist
     * @return -1 in int
     */
    private static int getInDate(Date date,int field){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field){
            case YEAR:{
                return calendar.get(Calendar.YEAR);
            }
            case MONTH:{
                // Calendar 类 获取月份需要加1
                return calendar.get(Calendar.MONTH)+1;
            }
            case DAY:{
                return calendar.get(Calendar.DAY_OF_MONTH);
            }
            case HOUR:{
                return calendar.get(Calendar.HOUR);
            }
            default:{
                return -1;
            }
        }
    }

    /**
     * returns how many days are there in a month
     * @param month
     * @param year
     * @return the days in month
     */
    private static int getDaysInMonth(int month,int year){
        if( month % 2 == 1){
            if(month>=9)
                return 30 ;
            else
                return 31 ;
        }
        else{
            if ( month == 2 ){
                if(year%4==0&&year%400!=0)
                    return 28 ;
                else
                    return 29 ;
            }
            else{
                if(month>=8)
                    return 31 ;
                else
                    return 30 ;
            }
        }
    }
}
