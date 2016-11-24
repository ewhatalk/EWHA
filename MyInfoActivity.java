package com.tutorials.hp.bottomnavrecycler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager3;
import com.tutorials.hp.bottomnavrecycler.info.InfoListAdapter;
import com.tutorials.hp.bottomnavrecycler.info.InfoListItem;

/*
* 2016/
* Copyright ⓒ NaYeon Kim All Rights Reserved.
*/

public class MyInfoActivity extends AppCompatActivity {
    String user_id;
    TextView usertext;

    DBManager3 dbManager3;

    ListView InfoListView;
    InfoListAdapter adapter;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        user_id = loadID();
        usertext = (TextView) findViewById(R.id.text2);

        InfoListView = (ListView) findViewById(R.id.MyInfoList);
        adapter = new InfoListAdapter(this);

        usertext.setText(user_id);
        TabHost mTabHost = (TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();

        TabHost.TabSpec mSpec = mTabHost.newTabSpec("내가 쓴 글");
        mSpec.setContent(R.id.first_Tab);
        mSpec.setIndicator("내가 쓴 글");
        mTabHost.addTab(mSpec);
        Cursor c = dbManager3.queryclass_Table2(user_id);
        c.moveToFirst();
        Resources res = getResources();

        if (c.getCount() == 0) {
            adapter.addItem(new InfoListItem(null, "", "", "", "", "작성한 게시글이 없습니다.", "", ""));
        } else {
            for (int i = 0; i < c.getCount(); i++) {

                adapter.addItem(new InfoListItem(res.getDrawable(R.drawable.qicon), c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
                c.moveToNext();
            }
        }

        InfoListView.setAdapter(adapter);

        InfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoListItem curItem = (InfoListItem) adapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString("subj_name", curItem.getData(0));
                bundle.putString("class_num", curItem.getData(1));
                bundle.putString("user_id", curItem.getData(2));
                bundle.putString("date", curItem.getData(3));
                bundle.putString("title", curItem.getData(4));
                bundle.putString("content", curItem.getData(5));
                bundle.putString("post_id",curItem.getData(6));

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String loadID() {
        SharedPreferences pref = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String str = pref.getString("user_id", "");

        return str;
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MyInfo Page", // TODO: Define a title for the content shown.
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.tutorials.hp.bottomnavrecycler/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MyInfo Page", // TODO: Define a title for the content shown.
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.tutorials.hp.bottomnavrecycler/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
















