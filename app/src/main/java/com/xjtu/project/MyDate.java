package com.xjtu.project;

import java.util.Calendar;

public class MyDate {
    public static final int MORNING = 0;
    public static final int NOON = 1;
    public static final int AFTERNOON = 2;
    public static final int EVENING = 3;
    public int year;
    public int month;
    public int day;
    public int hour;

    public MyDate() {
        Calendar calendar = Calendar.getInstance();
        this.year =calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
    }

    public String getTimeDescription(){
        String timeDescriptive ;
        String str_mon = String.valueOf(this.month);
        String str_day = String.valueOf(this.day);
        String str_hour = String.valueOf(this.hour);
        if(this.month<10){
            str_mon = "0" + str_mon;
        }
        if (this.day<10){
            str_day = "0" + str_day;
        }
        if(this.hour<10){
            str_day = "0" +str_hour;
        }
        timeDescriptive =str_mon+str_day+str_hour+defineHour();
        return timeDescriptive;
    }
    private int defineHour(){
        if(this.hour>=7&&this.hour<=11){
            return MORNING;
        }
        else if(this.hour>=12&&this.hour<=14){
            return NOON;
        }
        else if(this.hour>=15&&this.hour<=18){
            return AFTERNOON;
        }
        else {
            return EVENING;
        }
    }
}
