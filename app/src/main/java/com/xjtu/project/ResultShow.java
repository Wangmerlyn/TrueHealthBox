package com.xjtu.project;


import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultShow extends Fragment {

    Button Button_SaveResult;
    BarChart barChart;
    Button Button_Debug ;



    public ResultShow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_show, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textView_Show_BloodGlucose;
        final MyViewModel myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        Button_SaveResult = getActivity().findViewById(R.id.button_SaveResult);
        Button_SaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

                MyDate myDate = new MyDate();
                boolean insertInfo = dataBaseHelper.insertData(myViewModel.User_Name,myViewModel.BloodGlucose,myDate.getTimeDescription());
                if(insertInfo){
                    Toast.makeText(getContext(),"Saving Successful",Toast.LENGTH_SHORT).show();
                    Button_SaveResult.setClickable(false);
                    Button_SaveResult.setText(getResources().getString(R.string.button_ResultSaved));
                }
                else{
                    Toast.makeText(getContext(),"Saving Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
        float BloodGlucose =  myViewModel.BloodGlucose;
        // 显示血糖的文本框
        textView_Show_BloodGlucose = getActivity().findViewById(R.id.textView_Show_BloodGlucose);
        textView_Show_BloodGlucose.setText(BloodGlucose(BloodGlucose));
        barChart = getActivity().findViewById(R.id.barChart);



        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        Cursor cursor = dataBaseHelper.getAllData();
        cursor.moveToLast();
        float[][] data = new float[4][7];
        data[myViewModel.CurrentDate.defineHour()][0] = myViewModel.BloodGlucose;
        int j=0;
        while  (cursor.moveToPrevious()){

            j++;

            Log.d("INDEX",cursor.getString(0));
            Log.d("INDEX",cursor.getString(1));
            Log.d("INDEX",cursor.getString(2));
            Log.d("Times",String.valueOf(j));
            try {
                Log.d("INDEX",String.valueOf(myViewModel.CurrentDate.compareRecent(cursor.getString(2))));
            }
            catch (ParseException e){
                //
            }






            try {
                if(myViewModel.CurrentDate.compareRecent(cursor.getString(2))>=7){
                    Log.d("REASON","1");
                    break;
                }
                else if(!cursor.getString(0).equals(myViewModel.User_Name)){
                    Log.d("REASON","2");
                    Log.d("REASON",cursor.getString(0));
                    Log.d("REASON",myViewModel.User_Name);

                    continue;
                }
                else {
                    int index = Integer.parseInt(cursor.getString(2).substring(10,11));

                    data[index][myViewModel.CurrentDate.compareRecent(cursor.getString(2))] = Float.parseFloat(cursor.getString(1));
                    Log.d("shitData",cursor.getString(1));
                }
            }
            catch (ParseException e){
                Toast.makeText(getContext(),"DataBase break down",Toast.LENGTH_LONG).show();
            }
        }


        debugSession();
        Log.d("STEP","SHIW");
        // 早，中午，下午，晚的数据集
        List<BarEntry> entries1 = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();
        List<BarEntry> entries3 = new ArrayList<>();
        List<BarEntry> entries4 = new ArrayList<>();

        for(int i =0;i<7;i++){
            entries1.add(new BarEntry(i,data[0][6-i]));
            entries2.add(new BarEntry(i,data[1][6-i]));
            entries3.add(new BarEntry(i,data[2][6-i]));
            entries4.add(new BarEntry(i,data[3][6-i]));
        }
        // 根据给定数据集设定图表
        BarDataSet barDataSet1 = new BarDataSet(entries1,"Morning");
        BarDataSet barDataSet2 = new BarDataSet(entries2,"Noon");
        BarDataSet barDataSet3 = new BarDataSet(entries3,"Afternoon");
        BarDataSet barDataSet4 = new BarDataSet(entries4,"Evening");
        barDataSet1.setColor(Color.rgb(54,141,24));
        barDataSet2.setColor(Color.rgb(70,120,30));
        barDataSet3.setColor(Color.rgb(90,110,35));
        barDataSet4.setColor(Color.rgb(100,100,45));
        BarData barData = new BarData(barDataSet1,barDataSet2,barDataSet3,barDataSet4);
        float groupSpace = 0.12f;
        float barSpace = 0.04f; // x4 dataset
        float barWidth = 0.10f; // x4 dataset
        barData.setBarWidth(barWidth);
        barData.groupBars(0f,groupSpace,barSpace);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        String a =getActivity().getString(R.string.barChart_Description);
        Description description = barChart.getDescription();
        description.setText(a);
        //给barChart设定LimitLine
        BarChart_SetLimitLine(barChart);
        barChart.setData(barData);
        barChart.invalidate();

    }
    // 给BarChart设置LimitLine
    private void BarChart_SetLimitLine(BarChart barChart){
        YAxis leftAxis = barChart.getAxisLeft();
        TypedValue BloodGlucose_TooLow=new TypedValue(),BloodGlucose_TooHigh = new TypedValue() ;
        getActivity().getResources().getValue(R.dimen.BloodGlucose_Low,BloodGlucose_TooLow,true);
        getActivity().getResources().getValue(R.dimen.BloodGlucose_High,BloodGlucose_TooHigh,true);
        LimitLine limitLine_TooLow = new LimitLine(BloodGlucose_TooLow.getFloat(),getString(R.string.limitLine_BluudGlucoseTooLow));
        LimitLine limitLine_TooHigh = new LimitLine(BloodGlucose_TooHigh.getFloat(),getString(R.string.limitLine_BloodGlucoseTooHigh));
        limitLine_TooLow.setLineColor(Color.RED);
        limitLine_TooHigh.setLineColor(Color.GREEN);
        leftAxis.addLimitLine(limitLine_TooLow);
        leftAxis.addLimitLine(limitLine_TooHigh);
    }
    //  显示血糖具体状态的语句
    private String BloodGlucose(float BloodGlucose){
        MyViewModel myViewModel=ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        TypedValue BloodGlucose_High = new TypedValue(),BloodGlucose_Low = new TypedValue();
        String Result=new String(myViewModel.User_Name);
        Result+=",\n";
        getActivity().getResources().getValue(R.dimen.BloodGlucose_High,BloodGlucose_High,true);
        getActivity().getResources().getValue(R.dimen.BloodGlucose_Low,BloodGlucose_Low,true);
        if(BloodGlucose>BloodGlucose_High.getFloat()){
            Result+=getActivity().getResources().getString(R.string.BloodGlucose_TooHigh);
        }
        else if(BloodGlucose<BloodGlucose_Low.getFloat()){
            Result+=getActivity().getResources().getString(R.string.BloodGlucose_TooLow);
        }
        else{
            Result+=getActivity().getResources().getString(R.string.BloodGlucose_Normal);
        }
        Result +=BloodGlucose+".";
        return Result;
    }
    // 显示当前数据的所有相关信息
    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void debugSession(){

            Button_Debug = getActivity().findViewById(R.id.button_View);
            Button_Debug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHelper db = new DataBaseHelper(getContext());
                    Cursor cursor = db.getAllData();
                    if (cursor.getCount() == 0) {
                        showMessage("error", "shit");
                        return;
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    while (cursor.moveToNext()) {
                        stringBuffer.append("Name :" + cursor.getString(0) + "\n");
                        stringBuffer.append("Date :"+cursor.getString(2)+"\n");
                    }
                    showMessage("boiiii", stringBuffer.toString());

                }
            });


    }

}
