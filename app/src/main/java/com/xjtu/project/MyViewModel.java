package com.xjtu.project;

import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    public String User_Name;
    public String PathToFile;
    public ImageView myImageView;
    public picHandler pic = new picHandler();
    public float BloodGlucose ;
}
