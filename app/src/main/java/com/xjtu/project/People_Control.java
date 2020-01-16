package com.xjtu.project;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;
// 用来储存家庭中所有人的大致信息
// 使用SharedPreferences实现
public class People_Control {
    private Context context;
    //初始化，获取context
    public People_Control(Context contextIn){
        context=contextIn;
    }
    //储存给定的家人信息
    public void Save(String Name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.FamilyInfo),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String> Family=new HashSet<>();
        Family= sharedPreferences.getStringSet(this.context.getResources().getString(R.string.FamilyName),new HashSet<String>());
        Family.add(Name);
        editor.remove(this.context.getResources().getString(R.string.FamilyName)).commit();
        editor.putStringSet(this.context.getResources().getString(R.string.FamilyName),Family).commit();
        editor.apply();
    }
    // 判断家庭列表中是否存在某个人
    public boolean SearchName(String Name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.FamilyInfo),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String> Family=new HashSet<>();
        Family=sharedPreferences.getStringSet(this.context.getResources().getString(R.string.FamilyName),new HashSet<String>());
        return Family.contains(Name);
    }
    // 获取全部信息
    public Set<String> GetNameSet(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.FamilyInfo),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String> Family=new HashSet<>();
        Family=sharedPreferences.getStringSet(context.getResources().getString(R.string.FamilyName),new HashSet<String>());
        return Family;
    }
    // 删除一位成员
    public void Delete(String Name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.FamilyInfo),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String>Family=sharedPreferences.getStringSet(context.getResources().getString(R.string.FamilyName),new HashSet<String>());
        Family.remove(Name);
        editor.remove(context.getResources().getString(R.string.FamilyName)).commit();
        editor.putStringSet(context.getResources().getString(R.string.FamilyName),Family).commit();
        editor.apply();
    }

}
