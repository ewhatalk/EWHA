package com.tutorials.hp.bottomnavrecycler.mFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tutorials.hp.bottomnavrecycler.R;
import com.tutorials.hp.bottomnavrecycler.SearchDialog2.SearchDialog2;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/*
* 2016/09/07
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class FindFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "EWHATALK";

    String f_type = "";
    String f_college = "";
    String f_major = "";
    String f_grade = "";

    String[] items1 = { "전체", "교양", "전공선택(교직)", "전공기초", "전공" };
    String[] items2 = { "공과대학" };
    String[] items3 = { "전체", "컴퓨터공학과", "전자공학과", "건축학", "건축공학", "환경공학", "식품공학", "화학신소재공학" };
    String[] items4 = { "전체", "1", "2", "3", "4", "5" };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.find_frgament,container,false);

        Button SearchBtn = (Button) rootView.findViewById(R.id.SearchBtn);
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", f_type);
                bundle.putString("college", f_college);
                bundle.putString("major", f_major);
                bundle.putString("grade", f_grade);

                Intent intent = new Intent(getActivity(), SearchDialog2.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        Spinner spin1 = (Spinner) rootView.findViewById(R.id.spinner1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_type = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spin2 = (Spinner) rootView.findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_college = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spin3 = (Spinner) rootView.findViewById(R.id.spinner3);
        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_major = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spin4 = (Spinner) rootView.findViewById(R.id.spinner4);
        spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_grade = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin1.setAdapter(adapter1);
        spin2.setAdapter(adapter2);
        spin3.setAdapter(adapter3);
        spin4.setAdapter(adapter4);

        return rootView;
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {}

    public void onNothingSelected(AdapterView<?> parent) {}
}




















