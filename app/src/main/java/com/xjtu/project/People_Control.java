package com.xjtu.project;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class People_Control {
    private Context context;
    public People_Control(Context contextIn){
        context=contextIn;
    }

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
    public boolean SearchName(String Name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.FamilyInfo),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String> Family=new HashSet<>();
        Family=sharedPreferences.getStringSet(this.context.getResources().getString(R.string.FamilyName),new HashSet<String>());
        return Family.contains(Name);
    }
    public Set<String> GetNameSet(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.FamilyInfo),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String> Family=new HashSet<>();
        Family=sharedPreferences.getStringSet(context.getResources().getString(R.string.FamilyName),new HashSet<String>());
        return Family;
    }
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
