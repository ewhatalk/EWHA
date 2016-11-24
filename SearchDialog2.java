package com.tutorials.hp.bottomnavrecycler.SearchDialog2;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager;
import com.tutorials.hp.bottomnavrecycler.R;
import com.tutorials.hp.bottomnavrecycler.SearchDialog.SearchDialogListAdapter;
import com.tutorials.hp.bottomnavrecycler.SearchDialog.SearchDialogListItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/*
* 2016/10/04
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class SearchDialog2 extends AppCompatActivity {
    ListView SearchDialogListView;
    SearchDialogListAdapter adapter;

    DBManager dbManager;

    String val;
    Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dialog2);

        SearchDialogListView = (ListView) findViewById(R.id.SearchDialogList2);
        adapter = new SearchDialogListAdapter(this);
        dbManager = new DBManager(this);

        try {
            val = new phpDown().execute("http://121.131.185.173/class_table_up.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        nyExecute(val);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String type = bundle.getString("type");
        String college = bundle.getString("college");
        String major = bundle.getString("major");
        String grade = bundle.getString("grade");

        Cursor cursor = dbManager.queryclass_Table(type, college, major, grade);

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "해당 과목이 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            AddCursorData(cursor);
            SearchDialogListView.setAdapter(adapter);


            SearchDialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SearchDialogListItem curItem = (SearchDialogListItem) adapter.getItem(position);

                        Intent intent = new Intent(getApplicationContext(), SearchDialog3.class);
                        intent.putExtra("class_id", curItem.getData(0));
                    intent.putExtra("subj_name",curItem.getData(1));
                    intent.putExtra("class_num",curItem.getData(2));
                        startActivity(intent);
                        finish();

                }

            });

            exitBtn = (Button) findViewById(R.id.exitBtn);
            exitBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("class_id", "close");
                    setResult(RESULT_OK, resultIntent);

                    finish();
                }
            });
        }
    }

    public void AddCursorData(Cursor outCursor) {
        int recordCount = outCursor.getCount();

        adapter.clear();

        for (int i = 0; i < recordCount; i++) {
            outCursor.moveToNext();
            int c_id = outCursor.getInt(0);
            String sj_name = outCursor.getString(1);
            String class_num = outCursor.getString(2);
            String pr = outCursor.getString(3);

            adapter.addItem(new SearchDialogListItem(Integer.toString(c_id), sj_name, class_num, pr));
        }
        outCursor.close();
    }

    private class phpDown extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();

            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);

                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // 연결되었으면.
                if (conn != null) {


                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();

                            if (line == null) break;

                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        Log.d("여기까지", "여기까지");
                        br.close();
                        Log.d("여기까지", "1");
                    }
                    conn.disconnect();
                    Log.d("여기까지", "2");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Log.d("여기까지", "99");
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {

        }
    }

    protected void nyExecute(String str) {
        int c_id;
        String type;
        String col;
        String maj;
        int grd;
        String sn;
        int cn;
        String pro;


        try {
            JSONObject root = new JSONObject(str);
            JSONArray ja = root.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                c_id = jo.getInt("class_id");
                type = jo.getString("type");
                col = jo.getString("college");
                maj = jo.getString("major");
                grd = jo.getInt("grade");
                sn = jo.getString("subj_name");
                cn = jo.getInt("class_num");
                pro = jo.getString("prof");

                dbManager.insert(c_id, type, col, maj, grd, sn, cn, pro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}