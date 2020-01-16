package com.xjtu.project;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndividualDataControl {
    private String Name;
    private Context context;
    private String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String string = simpleDateFormat.format(date);
        return string;
    }
    public  IndividualDataControl(Context context,String name){
        this.Name = name;
        this.context = context;
    }
    public void Save(float BloodGlucose){
        SharedPreferences sharedPreferences = context.getSharedPreferences(this.Name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> AllInfo = new HashSet<>();
        AllInfo = sharedPreferences.getStringSet(context.getResources().getString(R.string.individual_BloodGlucose),new HashSet<String>());

        String Date = getDate();
        String BloodGlucoseInfo = Float.toString(BloodGlucose);

    }
    public Set<String> getAllInfo(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(this.Name,Context.MODE_PRIVATE);
        Set<String> AllInfo = new HashSet<>();
        AllInfo = sharedPreferences.getStringSet(this.Name,new HashSet<String>());
        return AllInfo;
    }
    public List<String> getRecent(){
        return null;
    }

}


