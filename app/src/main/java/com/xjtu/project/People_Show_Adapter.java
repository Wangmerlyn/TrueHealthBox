package com.xjtu.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
// 通过对数据适配器的重写来形成针对于应用数据的布局
public class People_Show_Adapter extends BaseAdapter {
    private List<People_Item> list;//数据
    private LayoutInflater layoutInflater;//布局装载
    public People_Show_Adapter(Context context,List<People_Item>list){
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }
    public void Reset(Context context,List<People_Item>list){
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.people_show_login,null);
        }
        View view=layoutInflater.inflate(R.layout.people_show_login,null);
        TextView textView=(TextView)view.findViewById(R.id.textView_People_Show_Login);
        People_Item people_item=list.get(position);
        textView.setText(people_item.Name);
        return view;
    }
}
