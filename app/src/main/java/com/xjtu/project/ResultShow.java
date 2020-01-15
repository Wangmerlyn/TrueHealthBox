package com.xjtu.project;


import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultShow extends Fragment {



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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button Button_SaveResult;
        BarChart barChart;
//        LineChart lineChart;
        TextView textView_Show_BloodGlucose;
        final MyViewModel myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        Button_SaveResult = getActivity().findViewById(R.id.button_SaveResult);
        Button_SaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //虚拟数据，仅供测试
        float BloodGlucose =  myViewModel.BloodGlucose;
        // 显示血糖的文本框
        textView_Show_BloodGlucose = getActivity().findViewById(R.id.textView_Show_BloodGlucose);
        textView_Show_BloodGlucose.setText(BloodGlucose(BloodGlucose));

        String days[] = getResources().getStringArray(R.array.Days);
        float data[] =  {4.1f,4.2f,4.5f,4.3f,4.6f,4.4f,4.3f};

        barChart = getActivity().findViewById(R.id.barChart);
//        List<BarEntry> entries = new ArrayList<>();
//        for(int i=0;i<7;i++){
//            entries.add(new BarEntry(Integer.parseInt(days[i]),data[i]));
//        }
//        BarDataSet barDataSet = new BarDataSet(entries,"shit");
//        barDataSet.setBarBorderColor(Color.rgb(120,120,120));
//        BarData barData = new BarData(barDataSet);
//        barChart.setData(barData);
        // BarChart边角注释


        //虚假数据构造
        //使用Grouped BarChart
        Float Morning[] = {3.1f,4.2f,5.6f,6.7f,7.1f,2.5f,3.9f};
        Float Noon[] = Morning.clone();
        Float Afternoon[] = Morning.clone();
        Float Evening[] = Morning.clone();

        List<BarEntry> entries1 = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();
        List<BarEntry> entries3 = new ArrayList<>();
        List<BarEntry> entries4 = new ArrayList<>();
        for(int i =0;i<7;i++){
            entries1.add(new BarEntry(i,Morning[i]));
            entries2.add(new BarEntry(i,Morning[i]));
            entries3.add(new BarEntry(i,Morning[i]));
            entries4.add(new BarEntry(i,Morning[i]));
        }
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

    //  ===
    //  === 显示血糖具体状态的语句
    //  ===
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
}
