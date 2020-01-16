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

    public MyDate() {
        Calendar calendar = Calendar.getInstance();
        this.year =calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH)+1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
    }
    public MyDate(String date){
        this.year = Integer.parseInt(date.substring(0,3));
        this.month = Integer.parseInt(date.substring(4,5));
        this.day = Integer.parseInt(date.substring(6,7));
        this.hour = Integer.parseInt(date.substring(8,9));
    }

    public void refresh(){
        Calendar calendar = Calendar.getInstance();
        this.year =calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH)+1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
    }

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
    public int compareRecent(String date) throws ParseException {
        MyDate myDate_cmp = new MyDate(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhh");
        Date date1 = simpleDateFormat.parse(date.substring(0,9));
        Date date2 = simpleDateFormat.parse(this.getTimeDescription().substring(0,9));
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        int days = (int) ((l2-l1)/(1000*60*60*24));
        return days;
    }
}
