package com.xjtu.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            //Toast.makeText(this,"thisisi hsiw",Toast.LENGTH_LONG).show();
            if(requestCode==1){
                Toast.makeText(this,getString(R.string.SuccessfullyTakenPhoto),Toast.LENGTH_LONG).show();
                MyViewModel myViewModel;
                myViewModel= ViewModelProviders.of(this).get(MyViewModel.class);
                Bitmap bitmap= BitmapFactory.decodeFile(myViewModel.PathToFile);
                myViewModel.pic.initAct(this);
                try{
                    myViewModel.pic.initPic(bitmap, getApplication().getResources().getInteger(R.integer.pic_width),getApplication().getResources().getInteger(R.integer.pic_height));
                    myViewModel.myImageView.setImageBitmap(myViewModel.pic.bMap);
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"加载图片失败",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Uri imageUri = data.getData();
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
                    myViewModel.pic.initPic(bitmap, getApplication().getResources().getInteger(R.integer.pic_width),getApplication().getResources().getInteger(R.integer.pic_height));
                    myViewModel.myImageView.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }

        }
    }
}
