package com.tutorials.hp.bottomnavrecycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.tutorials.hp.bottomnavrecycler.Comment.CommentListAdapter;
import com.tutorials.hp.bottomnavrecycler.Comment.CommentListItem;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager3;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager5;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 2016-10-06.
 */

public class DetailActivity extends AppCompatActivity {
    private final String UrlPath = "http://121.131.185.173/comment_add.php";
    private final String UrlPath2 = "http://121.131.185.173/comment_update.php";

    ListView CommentListView;
    CommentListAdapter adapter;
    ScrollView detail_scroll;

    TextView subj_name;
    TextView class_num;
    TextView user_id;
    TextView date;
    TextView title;
    TextView content;
    String subj_name2;
    String class_num2;
    String user_id2;//게시글작성자id
    String date2;
    String title2;
    String content2;

    InputMethodManager imm;
    RelativeLayout detaillayout;

    EditText comment;
    Button save;

    String post_id;
    String comment_user_id;
    String commentStr;
    String time;

    DBManager5 dbManager5;
    DBManager3 dbManager3;
    String s;
    String s2;
    Cursor c;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbManager3=new DBManager3(this);
        dbManager5=new DBManager5(this);

        try {
            s = new phpDown().execute("http://121.131.185.173/comment_table_up.php").get();
            s2 = new phpDown().execute("http://121.131.185.173/posting_table_up.php").get();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            e.printStackTrace();
        }
        CommentExecute(s);
        nyExecute(s2);

        CommentListView = (ListView) findViewById(R.id.comment_list);
        adapter = new CommentListAdapter (this);
        detail_scroll = (ScrollView) findViewById(R.id.detail_scroll);

        CommentListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detail_scroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        detaillayout = (RelativeLayout)findViewById(R.id.detail_layout);
        detaillayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(comment.getWindowToken(), 0);
            }
        });

        subj_name = (TextView)findViewById(R.id.subj_name_text);
        class_num = (TextView)findViewById(R.id.class_num_text);
        user_id = (TextView)findViewById(R.id.userid_text2);
        date = (TextView)findViewById(R.id.date_text);
        title = (TextView)findViewById(R.id.title_text2);
        content = (TextView)findViewById(R.id.content_text);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        subj_name2 = bundle.getString("subj_name");
        class_num2 = bundle.getString("class_num");
        user_id2 = bundle.getString("user_id");
        date2 = bundle.getString("date");
        title2 = bundle.getString("title");
        content2 = bundle.getString("content");
        post_id = bundle.getString("post_id");

        subj_name.setText(subj_name2);
        class_num.setText(class_num2);
        user_id.setText(user_id2);
        date.setText(date2);
        title.setText(title2);
        content.setText(content2);

        comment = (EditText) findViewById(R.id.comment);

        save = (Button)findViewById(R.id.comment_save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                commentStr = comment.getText().toString();
                comment_user_id = loadID();
                time=time();
                adapter.addItem(new CommentListItem(comment_user_id,time,commentStr));
                CommentListView.setAdapter(adapter);

                if (commentStr.length() < 1) {
                    Toast.makeText(getApplicationContext(), "댓글을 써주세요", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        new PostComment().execute().get();
                        new PostBool().execute().get();
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    } catch ( ExecutionException e ) {
                        e.printStackTrace();
                    }
                    comment.setText("");
                }
                dbManager3.update(post_id);
            }
        });

        c = dbManager5.queryclass_Table1(post_id);
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++){
            adapter.addItem(new CommentListItem(c.getString(0),c.getString(1),c.getString(2)));
            c.moveToNext();
        }

        CommentListView.setAdapter(adapter);
    }

    class PostComment extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            try {
                URL url = new URL(UrlPath); // Set url

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoInput(true); // Available Write
                con.setDoOutput(true); // Available Read
                con.setUseCaches(false); // No cash
                con.setRequestMethod("POST");

                String param = "user_id=" + comment_user_id + "&post_id=" + post_id + "&content=" + commentStr;

                OutputStream outputStream = con.getOutputStream();
                outputStream.write(param.getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader rd = null;
                rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    Log.d("BufferedReader:", line);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(ArrayList<String> qResults) {
            super.onPostExecute(qResults);
        }
    }

    class PostBool extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            try {
                URL url = new URL(UrlPath2);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");

                String param = "post_id=" + post_id;

                OutputStream outputStream = con.getOutputStream();
                outputStream.write(param.getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader rd = null;
                rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    Log.d("BufferedReader:", line);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(ArrayList<String> qResults) {
            super.onPostExecute(qResults);
        }
    }

    public void CommentExecute(String str) {
        int c_id;
        String u_id;
        int p_id;
        String cont;
        String date;

        try {
            JSONObject root = new JSONObject(str);
            JSONArray ja = root.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                c_id = jo.getInt("comment_id");
                u_id= jo.getString("user_id");
                p_id= jo.getInt("post_id");
                cont=jo.getString("content");
                date=jo.getString("date");

                dbManager5.insert(c_id,u_id,p_id,cont,date);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void nyExecute(String str) {
        int p_id;
        String u_id;
        String c_id;
        String title;
        String cont;
        String date;
        String bool;

        try {
            JSONObject root = new JSONObject(str);
            JSONArray ja = root.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                p_id = jo.getInt("post_id");
                u_id= jo.getString("user_id");
                c_id = jo.getString("class_id");
                title=jo.getString("title");
                cont=jo.getString("content");
                date=jo.getString("date");
                bool=jo.getString("boolean");

                dbManager3.insert(p_id,u_id,c_id,title,cont,date,bool);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        protected void onPostExecute(String str) {}
    }

    private String loadID() {
        SharedPreferences pref = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String str = pref.getString("user_id", "");

        return str;
    }

    public String time(){
        long curTime=System.currentTimeMillis();
        SimpleDateFormat timeFormat= new SimpleDateFormat("MM-dd hh:mm:ss");
        String str=timeFormat.format(new Date(curTime));

        return str;
    }
}
