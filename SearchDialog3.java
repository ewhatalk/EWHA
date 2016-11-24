package com.tutorials.hp.bottomnavrecycler.SearchDialog2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager4;
import com.tutorials.hp.bottomnavrecycler.DetailActivity;
import com.tutorials.hp.bottomnavrecycler.R;
import com.tutorials.hp.bottomnavrecycler.info.InfoListAdapter;
import com.tutorials.hp.bottomnavrecycler.info.InfoListItem;

/*
* 2016/10/04
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class SearchDialog3 extends AppCompatActivity {
    ListView InfoListView;
    InfoListAdapter adapter;

    DBManager dbManager;
    DBManager4 dbManager4;
    TextView subj_text;

    String s;
    String user_id;
    String subj_name;
    String class_id;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dialog3);

        CheckBox checkbox1=(CheckBox)findViewById(R.id.star);
        dbManager4=new DBManager4(this);
        user_id=loadID();
        subj_text=(TextView) findViewById(R.id.subj_text);

        InfoListView = (ListView) findViewById(R.id.SearchDialogList3);
        adapter = new InfoListAdapter(this);

        Intent intent = getIntent();
        s = intent.getExtras().getString("class_id");
        subj_name=intent.getExtras().getString("subj_name");
        class_id=intent.getExtras().getString("class_num");

        c = dbManager.queryclass_Table1(s);
        c.moveToFirst();

        Cursor cur=dbManager4.queryclass_Table2(user_id,subj_name,class_id);
        cur.moveToFirst();
        if(cur.getCount()==0){
            checkbox1.setChecked(false);
        }else {
            checkbox1.setChecked(true);
        }

        Resources res = getResources();
        c.moveToFirst();

        subj_text.setText(subj_name);

        if(c.getCount()==0){
            adapter.addItem(new InfoListItem(null,"","","","","작성한 게시글이 없습니다.","",""));
        } else {
            for (int i = 0; i < c.getCount(); i++) {

                adapter.addItem(new InfoListItem(res.getDrawable(R.drawable.qicon), c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5),c.getString(6)));
                c.moveToNext();
            }
        }

        c.moveToFirst();
        checkbox1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()){
                    dbManager4.insert(user_id,subj_name,class_id);
                } else {
                    dbManager4.delete(user_id, subj_name,class_id);
                }
            }
        });

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
                bundle.putString("post_id", curItem.getData(6));

                Intent intent = new Intent( getApplicationContext(), DetailActivity.class );
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

    }

    private String loadID() {
        SharedPreferences pref = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String str = pref.getString("user_id", "");

        return str;
    }
}





