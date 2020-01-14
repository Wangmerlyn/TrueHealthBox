package com.xjtu.project;


import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


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
        TextView textView_Show_BloodGlucose;
        final MyViewModel myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        //虚拟数据，仅供测试
        myViewModel.BloodGlucose = (float) 4.3;
        float BloodGlucose =  myViewModel.BloodGlucose;
        // 显示血糖的文本框
        textView_Show_BloodGlucose = getActivity().findViewById(R.id.textView_Show_BloodGlucose);
        textView_Show_BloodGlucose.setText(BloodGlucose(BloodGlucose));
        // 根据数据绘图
        // 以下爱数据皆为虚构
        LineChartView lineChartView = getActivity().findViewById(R.id.lineChart_BloodGlucose);
        String days[] = getResources().getStringArray(R.array.Days);
        float data[] =  {4.1f,4.2f,4.5f,4.3f,4.6f,4.4f,4.3f};
        List xAxis = new ArrayList();
        List yAxis = new ArrayList();
        Line line = new Line(yAxis).setColor(Color.parseColor("#9c27b0"));
        for(int i=0;i<days.length;i++){
            xAxis.add(i,new AxisValue(i).setLabel(days[i]));
            yAxis.add(new PointValue(i,data[i]));
        }
        List Lines = new ArrayList();
        Lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(Lines);
        lineChartView.setLineChartData(lineChartData);
        Axis axisX =new Axis();
        axisX.setValues(xAxis);
        lineChartData.setAxisXBottom(axisX);
        Axis axisY= new Axis();
        axisX.setTextColor(Color.parseColor("#9c27b0"));
        axisX.setTextSize(16);
        axisY.setTextColor(Color.parseColor("#9c27b0"));
        axisY.setTextSize(16);
        axisY.setName("Blood Glucose");
        axisX.setName("Days");
        lineChartData.setAxisYLeft(axisY);
        //保证数据完整显示
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top =7.0f;
        viewport.bottom = 3.5f;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
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
