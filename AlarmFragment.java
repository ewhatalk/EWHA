package com.tutorials.hp.bottomnavrecycler.mFragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.tutorials.hp.bottomnavrecycler.Alarm.AlarmListAdapter;
import com.tutorials.hp.bottomnavrecycler.Alarm.AlarmListItem;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager5;
import com.tutorials.hp.bottomnavrecycler.DetailActivity;
import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class AlarmFragment extends Fragment {
    ListView alarmListView;
    AlarmListAdapter adapter;

    DBManager5 dbManager5;
    String u_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.alarm_fragment,container,false);

        u_id=loadID();

        alarmListView = (ListView) rootView.findViewById(R.id.alarm_list);
        adapter = new AlarmListAdapter (this.getActivity());

        Cursor c= dbManager5.queryclass_Table2(u_id);
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++){
            adapter.addItem(new AlarmListItem(c.getString(4), c.getString(3),"",c.getString(0),c.getString(1),c.getString(2),c.getString(5),c.getString(6)));
            c.moveToNext();
        }

        alarmListView.setAdapter(adapter);

        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlarmListItem curItem = (AlarmListItem) adapter.getItem(position);
                String[] curData = curItem.getData();

                Bundle bundle = new Bundle();
                bundle.putString("subj_name", curData[3]);
                bundle.putString("class_num", curData[4]);
                bundle.putString("user_id", curData[5]);
                bundle.putString("date", curData[1]);
                bundle.putString("title", curData[0]);
                bundle.putString("content", curData[6]);
                bundle.putString("post_id",curData[7]);

                Intent intent = new Intent( getActivity().getApplicationContext(), DetailActivity.class );
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        return rootView;
    }

    private String loadID() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String str = pref.getString("user_id", "");

        return str;
    }
}
