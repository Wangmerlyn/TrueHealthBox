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
//        // 根据数据绘图
//        // 以下爱数据皆为虚构
//        LineChartView lineChartView = getActivity().findViewById(R.id.lineChart_BloodGlucose);
        String days[] = getResources().getStringArray(R.array.Days);
        float data[] =  {4.1f,4.2f,4.5f,4.3f,4.6f,4.4f,4.3f};
//        List xAxis = new ArrayList();
//        List yAxis = new ArrayList();
//        Line line = new Line(yAxis).setColor(Color.parseColor("#9c27b0"));
//        for(int i=0;i<days.length;i++){
//            xAxis.add(i,new AxisValue(i).setLabel(days[i]));
//            yAxis.add(new PointValue(i,data[i]));
//        }
//        List Lines = new ArrayList();
//        Lines.add(line);
//        LineChartData lineChartData = new LineChartData();
//        lineChartData.setLines(Lines);
//        lineChartView.setLineChartData(lineChartData);
//        Axis axisX =new Axis();
//        axisX.setValues(xAxis);
//        lineChartData.setAxisXBottom(axisX);
//        Axis axisY= new Axis();
//        axisX.setTextColor(Color.parseColor("#9c27b0"));
//        axisX.setTextSize(16);
//        axisY.setTextColor(Color.parseColor("#9c27b0"));
//        axisY.setTextSize(16);
//        axisY.setName("Blood Glucose");
//        axisX.setName("Days");
//        lineChartData.setAxisYLeft(axisY);
//        lineChart = getActivity().findViewById(R.id.lineChart);
//        List<Entry> entries1 = new ArrayList<>();
//        for(int i=0;i<6;i++){
//            entries1.add(new Entry(data[i],Integer.parseInt(days[i])));
//        }
//        LineDataSet lineDataSet = new LineDataSet(entries1,"shit");
//        LineData lineData = new LineData(lineDataSet);
//        lineChart.setData(lineData);
//        lineChart.invalidate();
//        保证数据完整显示
//        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
//        viewport.top =7.0f;
//        viewport.bottom = 3.5f;
//        lineChartView.setMaximumViewport(viewport);
//        lineChartView.setCurrentViewport(viewport);
        barChart = getActivity().findViewById(R.id.barChart);
        List<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<7;i++){
            entries.add(new BarEntry(Integer.parseInt(days[i]),data[i]));
        }
        BarDataSet barDataSet = new BarDataSet(entries,"shit");
        barDataSet.setBarBorderColor(Color.rgb(120,120,120));
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        // BarChart边角注释
        String a =getActivity().getString(R.string.barChart_Description);
        Description description = barChart.getDescription();
        description.setText(a);

        //给barChart设定LimitLine
        BarChart_SetLimitLine(barChart);
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
