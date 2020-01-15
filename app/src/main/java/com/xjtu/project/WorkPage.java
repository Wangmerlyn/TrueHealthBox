package com.xjtu.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkPage extends Fragment {
    private String imgFilePath;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");


    public WorkPage() {
        // Required empty public constructor
    }
    public static final int PICK_IMAGE = 11;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView_User_Name;
        textView_User_Name=getActivity().findViewById(R.id.textView_User_Name);
        // 获取问候语
        String HelloWord = welcomeWord();
        final MyViewModel myViewModel;
        myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.myImageView=getActivity().findViewById(R.id.textView_Preview);
        textView_User_Name.setText(HelloWord+myViewModel.User_Name);
        Button button_Analyze,button_TakePicture,button_Import;
        // 分析按钮


        button_Analyze=getActivity().findViewById(R.id.button_Analyze);
        button_Analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // 传入当前图片处理的Context
                    myViewModel.pic.initAct(getActivity());
                    // 尝试识别二维码
                    myViewModel.pic.decode();

                    // 在图片上绘制相应的点
                    myViewModel.pic.paintPoints(getActivity().getResources().getInteger(R.integer.pic_size));
                    //makeText(getActivity(),myViewModel.pic.fetchResult(),Toast.LENGTH_LONG).show();
                    myViewModel.myImageView.setImageBitmap(myViewModel.pic.bMap);
                    myViewModel.BloodGlucose = myViewModel.pic.fetchResult();
                }
                catch (Exception e){
                    try {
                        //排除相机间的误差
                        myViewModel.pic.initPic(myViewModel.pic.o_bMap,getActivity().getResources().getInteger(R.integer.pic_height),getActivity().getResources().getInteger(R.integer.pic_width));
                        myViewModel.pic.initAct(getActivity());
                        myViewModel.pic.decode();
                        myViewModel.pic.paintPoints(getActivity().getResources().getInteger(R.integer.pic_size));
                        //Toast.makeText(getActivity(),myViewModel.pic.fetchResult(),Toast.LENGTH_LONG).show();
                        myViewModel.myImageView.setImageBitmap(myViewModel.pic.bMap);
                        myViewModel.BloodGlucose = myViewModel.pic.fetchResult();
                    }
                    catch (Exception E){
                        Toast.makeText(getActivity(),"解析失败",Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"wqnmd我也不知道是啥错误啊啊啊",Toast.LENGTH_LONG).show();
                    }
                }
                //跳转到分析结果界面
                if (myViewModel.BloodGlucose!=0){
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_workPage_to_resultShow);
                }
                else{
                    Toast.makeText(getActivity(),"请识别图片",Toast.LENGTH_SHORT).show();
                }
            }
        });


        button_TakePicture=getActivity().findViewById(R.id.button_TakePicture);
        button_TakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有权限，若无则进行索取权限
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED||
                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                    PackageManager.PERMISSION_DENIED){
                        String[] Permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(Permission,2);
                    }
                }
                dispatchPictureTakerAction();
               /* NavController navController;
                navController=Navigation.findNavController(v);
                navController.navigate(R.id.action_workPage_to_takePicture);*/
            }
        });


        button_Import=getActivity().findViewById(R.id.button_Import);
        button_Import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent,"Select Picture"),2);
            }
        });
        final Button button_Debug;
        button_Debug = getActivity().findViewById(R.id.button_Debug);
        button_Debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_Debug.setText(String.valueOf(myViewModel.pic.fetchResult()));
            }
        });
    }

    private void dispatchPictureTakerAction(){
        Intent takePic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getActivity().getPackageManager())!=null){
            File PhotoFile=null;
            PhotoFile=createPhotoFile();
            if(PhotoFile!=null){
                MyViewModel myViewModel;
                myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
                myViewModel.PathToFile=PhotoFile.getAbsolutePath();
                Uri PhotoUri= FileProvider.getUriForFile(getActivity(),"com.xjtu.project",PhotoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,PhotoUri);
                getActivity().startActivityForResult(takePic,1);
            }
        }
    }


    private File createPhotoFile(){
        String Name=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //将拍照所有文件存在该文件夹下
        File storageDir=new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"HealthBox");
        if(!storageDir.isDirectory()){
            //如果路径不存在的话创造路径
            storageDir.mkdir();
        }
        File image=null;
        try {
            //在系统相册HealthBox目录下创建临时图片文件
            image=File.createTempFile(Name,".jpg",storageDir);
        }catch (IOException e){
            Log.i("mylog","Excep:"+e.toString());
        }
        return image;
    }

    private String welcomeWord(){
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        if(hour>=6&&hour<11){
            return getActivity().getResources().getString(R.string.GoodMorning);
        }
        else if(hour>=11&&hour<=13){
            return getActivity().getResources().getString(R.string.GoodNoon);
        }
        else if(hour>13&&hour<=17){
            return getActivity().getResources().getString(R.string.GoodAfternoon);
        }
        else if(hour>17&&hour<=23){
            return getActivity().getResources().getString(R.string.GoodEvening);
        }
        else {
            return getActivity().getResources().getString(R.string.LateNight);
        }
    }





}
