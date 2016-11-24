package com.tutorials.hp.bottomnavrecycler.mFragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager4;
import com.tutorials.hp.bottomnavrecycler.R;
import com.tutorials.hp.bottomnavrecycler.SearchDialog2.SearchDialog3;
import com.tutorials.hp.bottomnavrecycler.Star.StarListAdapter;
import com.tutorials.hp.bottomnavrecycler.Star.StarListItem;

/*
* 2016/09/08
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class StarFragment extends Fragment {
    ListView starListView;
    StarListAdapter adapter;

    String user_id;
    String c_id;
    String s_name;
    String c_num;

    DBManager4 dbManager4;
    Cursor c=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbManager4=new DBManager4(getActivity().getBaseContext());

        View rootView=inflater.inflate(R.layout.star_fragment,container,false);

        starListView = (ListView) rootView.findViewById(R.id.star_list);
        adapter = new StarListAdapter(this.getActivity());

        user_id=loadID();
        c= dbManager4.queryclass_Table1(user_id);
        c.moveToFirst();


        if(c.getCount()==0){
        }
        else {
            s_name=c.getString(0);
            c_id=c.getString(2);
            c_num=c.getString(1);

            for (int i = 0; i < c.getCount(); i++) {
                adapter.addItem(new StarListItem(c.getString(0), c.getString(1),c.getString(2)));
                c.moveToNext();
            }
        }

        starListView .setAdapter(adapter);

        starListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StarListItem curItem=(StarListItem) adapter.getItem(position);
                String[] curData= curItem.getData();

                Intent intent = new Intent(getActivity().getApplicationContext(), SearchDialog3.class);
                intent.putExtra("class_id", curData[2]);
                intent.putExtra("subj_name",curData[0]);
                intent.putExtra("class_num",curData[1]);
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
