package com.xjtu.project;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppendNew extends Fragment {


    public AppendNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_append_new, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button_Complete;
        final EditText editText_Name,editText_Birth;
        editText_Name=getActivity().findViewById(R.id.editText_Name);
        editText_Birth=getActivity().findViewById(R.id.editText_birth);
        //Toast.makeText(getActivity(),editText_Birth.getText().toString(),Toast.LENGTH_LONG).show();
        button_Complete=getActivity().findViewById(R.id.button_Complete);
        button_Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                People_Control people_control=new People_Control(getActivity());
                String Name=editText_Name.getText().toString();
                //Toast.makeText(getContext(),getContext().getResources().getString(R.string.NameIsEmpty),Toast.LENGTH_LONG).show();
                if(Name.length()==0){
                    Toast.makeText(getContext(),getContext().getResources().getString(R.string.NameIsEmpty),Toast.LENGTH_LONG).show();
                }
                else if(people_control.SearchName(Name)){
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.NameExists),Toast.LENGTH_LONG).show();
                }
                else {
                    people_control.Save(Name);
                    NavController navController;
                    navController= Navigation.findNavController(v);
                    navController.navigate(R.id.action_appendNew_to_login);
                }

            }
        });

    }
}
