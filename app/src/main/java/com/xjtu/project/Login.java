package com.xjtu.project;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Button button_Append,button_Edit;
        button_Append=getActivity().findViewById(R.id.button_Append);
        button_Edit=getActivity().findViewById(R.id.button_Edit);
        //设定添加按钮
        button_Append.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController;
                navController= Navigation.findNavController(v);
                navController.navigate(R.id.action_login_to_appendNew);
            }
        });
        //设定删除按钮
        button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_Edit.getText().toString()==getContext().getResources().getString(R.string.button_Edit)){
                    button_Edit.setText(R.string.button_Edit_Deleting);
                }
                else {
                    button_Edit.setText(R.string.button_Edit);
                }
            }
        });
        //设定listview的显示内容
        final ListView listView_Login=getView().findViewById(R.id.listView_Login);
        final People_Control people_control=new People_Control(getContext());
        final List<People_Item>list =new ArrayList<>();
        Set<String>set=people_control.GetNameSet();
        String string[]=set.toArray(new String[set.size()]);
        for(int i=0,j=string.length;i<j;i++){
            list.add(new People_Item(string[i]));
        }
        final People_Show_Adapter people_show_adapter=new People_Show_Adapter(getContext(),list);
        listView_Login.setAdapter(people_show_adapter);
        //设定listview中item按键
        listView_Login.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(button_Edit.getText().toString()==getContext().getResources().getString(R.string.button_Edit)){
                    MyViewModel myViewModel;
                    myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
                    myViewModel.User_Name=list.get(position).Name;
                    NavController navController;
                    navController=Navigation.findNavController(view);
                    navController.navigate(R.id.action_login_to_workPage);
                }
                else{
                    people_control.Delete(list.get(position).Name);
                    list.remove(position);
                    people_show_adapter.Reset(getContext(),list);
                    listView_Login.setAdapter(people_show_adapter);

                }
            }
        });
    }
}
