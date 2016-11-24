package com.tutorials.hp.bottomnavrecycler.SearchDialog;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager;
import com.tutorials.hp.bottomnavrecycler.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
* 2016/09/28
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class SearchDialog extends Activity {
    private static final String TAG = "EWHATALK";

    String[] items5 = {"전체", "교양", "전공선택(교직)", "전공기초", "전공"};
    String[] items6 = {"공과대학"};
    String[] items7 = {"전체", "컴퓨터공학과", "전자공학과", "건축학", "건축공학", "환경공학", "식품공학", "화학신소재공학"};
    String[] items8 = {"전체", "1", "2", "3", "4", "5"};

    String w_type = null;
    String w_college = null;
    String w_major = null;
    String w_grade = null;

    Button confirmBtn;

    ListView SearchDialogListView;
    SearchDialogListAdapter adapter;

    DBManager dbManager;

    phpDown task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);
        this.setFinishOnTouchOutside(false);

        dbManager = new DBManager(this);
        task = new phpDown();
        task.execute("http://121.131.185.173/class_table_up.php");

        SearchDialogListView = (ListView) findViewById(R.id.SearchDialogList);
        adapter = new SearchDialogListAdapter(this);

        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = dbManager.queryclass_Table(w_type, w_college, w_major, w_grade);
                AddCursorData(cursor);

                SearchDialogListView.setAdapter(adapter);
            }
        });

        SearchDialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchDialogListItem curItem = (SearchDialogListItem) adapter.getItem(position);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("class_id", curItem.getData(0));
                setResult(RESULT_OK, resultIntent);

                finish();
            }

        });

        Spinner spin5 = (Spinner) findViewById(R.id.spinner5);
        spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                w_type = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spin6 = (Spinner) findViewById(R.id.spinner6);
        spin6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                w_college = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spin7 = (Spinner) findViewById(R.id.spinner7);
        spin7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                w_major = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spin8 = (Spinner) findViewById(R.id.spinner8);
        spin8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                w_grade = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items5);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items6);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items7);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items8);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin5.setAdapter(adapter5);
        spin6.setAdapter(adapter6);
        spin7.setAdapter(adapter7);
        spin8.setAdapter(adapter8);
    }

    public void onBackPressed(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("class_id", "");
        setResult(RESULT_OK, resultIntent);

        finish();
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
                URL url = new URL(urls[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            String line = br.readLine();
                            if (line == null) break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
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
                    cn=jo.getInt("class_num");
                    pro = jo.getString("prof");

                    dbManager.insert(c_id, type, col, maj, grd, sn,cn, pro);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
