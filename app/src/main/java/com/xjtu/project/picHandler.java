package com.xjtu.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class picHandler {
    ResultPoint[] points;
    ResultPoint  left_top,right_top,left_bottom;
    Context context;
    Bitmap bMap;
    Bitmap o_bMap;
    BinaryBitmap bitmap;
    // 当前Context
    public void initAct(Context context){
        this.context=context;
    }
    public Bitmap changeSize(Bitmap m,int width,int height){
        if(m.getHeight()*m.getWidth()<1600000){
            return m;
        }
        float scaleWidth= ((float)width)/m.getWidth();
        float scaleHeight=((float)height)/m.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap b=Bitmap.createBitmap(m,0,0,m.getWidth(),m.getHeight(),matrix,true);
        return b;
    }
    public Boolean initPic(Bitmap b,int w,int h) {
        try{
            o_bMap = b;
            bMap = this.changeSize(b,w,h);
//            Toast.makeText(context,Boolean.toString(bMap==null), Toast.LENGTH_LONG).show(); // 是否获得到图片
//            Toast.makeText(context,Integer.toString(bMap.getHeight()), Toast.LENGTH_LONG).show();

            int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
            //copy pixel data from the Bitmap into the 'intArray' array
            bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
            LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
            this.bitmap = new BinaryBitmap(new HybridBinarizer(source));

//            Toast.makeText(context,"Pic_finished", Toast.LENGTH_LONG).show();
            return true;
        }
        catch(Exception e){
            Toast.makeText(context,"Pic_failed", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public Boolean decode(){
        QRCodeReader qrCodeReader = new QRCodeReader();
        try {
            Result result = qrCodeReader.decode(this.bitmap);
            this.points = result.getResultPoints();
            filterPoints();
            handleResult();
            return true;
        }catch (ReaderException re){
            Toast.makeText(context, "read failed", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void colorPicker(int x,int y){
        int color = bMap.getPixel(x,y);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        Toast.makeText(context,"r:"+Integer.toString(r)+"-g:"+Integer.toString(g)+"-b:"+Integer.toString(b), Toast.LENGTH_LONG).show();
        return;
    }
    public void paintPoints(int size){
        for(int i=-size;i<=size;++i){
            for(int j=-size;j<=size;++j){
                for(int k=0;k<3;++k){
                    this.bMap.setPixel((int)this.points[k].getX()+i,(int)this.points[k].getY()+j, Color.RED);
                }
            }
        }
    }
    public Boolean filterPoints(){
        left_bottom = points[0];
        left_top = points[1];
        right_top = points[2];
        return true;
    }
    public void handleResult(){
//        Toast.makeText(context,Float.toString(this.left_top.getY())+"---"+Float.toString(this.bitmap.getHeight()), Toast.LENGTH_LONG).show();
        colorPicker((int)((left_top.getX()+left_bottom.getX())/2),(int)((left_top.getX()+left_bottom.getX())/2));
        return;
    }
}
// Interesting method
