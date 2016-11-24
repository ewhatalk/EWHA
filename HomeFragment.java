package com.tutorials.hp.bottomnavrecycler.mFragments;

import android.content.Intent;
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
import com.tutorials.hp.bottomnavrecycler.Database.DBManager3;
import com.tutorials.hp.bottomnavrecycler.Home.HomeListAdapter;
import com.tutorials.hp.bottomnavrecycler.Home.HomeListItem;
import com.tutorials.hp.bottomnavrecycler.DetailActivity;
import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/08
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class HomeFragment extends Fragment {
    ListView HomeListView;
    HomeListAdapter adapter;

    DBManager3 dbManager3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        HomeListView = (ListView) rootView.findViewById(R.id.home_list);
        adapter = new HomeListAdapter (this.getActivity());

        Resources res = getResources();

        Cursor c= dbManager3.queryclass_Table1();
        c.moveToFirst();
        for(int i=0;i<15;i++) {
            adapter.addItem(new HomeListItem(res.getDrawable(R.drawable.qicon), c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
            c.moveToNext();
        }

        HomeListView.setAdapter(adapter);

        HomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeListItem curItem = (HomeListItem) adapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString("subj_name", curItem.getData(0));
                bundle.putString("class_num", curItem.getData(1));
                bundle.putString("user_id", curItem.getData(2));
                bundle.putString("date", curItem.getData(3));
                bundle.putString("title", curItem.getData(4));
                bundle.putString("content", curItem.getData(5));
                bundle.putString("post_id", curItem.getData(6));

                Intent intent = new Intent( getActivity(), DetailActivity.class );
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        return rootView;
    }
}