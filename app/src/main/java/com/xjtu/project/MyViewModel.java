package com.xjtu.project;

import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    public String User_Name;
    public String PathToFile;
    public ImageView myImageView;
    public picHandler pic ;
    public float BloodGlucose ;
    public MyDate CurrentDate;
    public MyViewModel(){
        pic =new picHandler();
        BloodGlucose = 0;
        CurrentDate = new MyDate();
    }
    public float calculateBloodGlucose(float grey_Shade){
        return grey_Shade;
    }
}
