package com.tutorials.hp.bottomnavrecycler;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager3;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager5;
import com.tutorials.hp.bottomnavrecycler.mFragments.AlarmFragment;
import com.tutorials.hp.bottomnavrecycler.mFragments.HomeFragment;
import com.tutorials.hp.bottomnavrecycler.mFragments.FindFragment;
import com.tutorials.hp.bottomnavrecycler.mFragments.StarFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim, NaYeon Kim All Rights Reserved.
*/

public class MainMenuActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{
    AHBottomNavigation bottomNavigation;

    public static final int REQUEST_CODE_MYINFO = 1002;

    DBManager3 dbManager3;
    DBManager dbManager;
    DBManager5 dbManager5;

    String val;
    String val2;
    String val3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager3 = new DBManager3(this);
        dbManager =new DBManager(this);
        dbManager5=new DBManager5(this);

        try {
            val = new phpDown().execute("http://121.131.185.173/posting_table_up.php").get();
            val2=new phpDown().execute("http://121.131.185.173/class_table_up.php").get();
            val3=new phpDown().execute("http://121.131.185.173/comment_table_up.php").get();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            e.printStackTrace();
        }
        nyExecute(val);
        hiExecute(val2);
        CommentExecute(val3);

        Cursor c= dbManager3.queryclass_Table1();
        c.moveToFirst();

        c.moveToFirst();
        Log.d(c.getString(1),c.getString(1));
        c.moveToNext();
        Log.d(c.getString(1),c.getString(1));

        bottomNavigation= (AHBottomNavigation) findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
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

    protected void hiExecute(String str) {
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

    private void createNavItems() {
        AHBottomNavigationItem homeItem=new AHBottomNavigationItem("홈",R.drawable.hp);
        AHBottomNavigationItem findItem=new AHBottomNavigationItem("글 찾기",R.drawable.fp);
        AHBottomNavigationItem alarmItem=new AHBottomNavigationItem("알람",R.drawable.ap);
        AHBottomNavigationItem starItem=new AHBottomNavigationItem("즐겨찾기",R.drawable.sp);

        bottomNavigation.addItem(homeItem);
        bottomNavigation.addItem(findItem);
        bottomNavigation.addItem(alarmItem);
        bottomNavigation.addItem(starItem);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#fefefe"));
        bottomNavigation.setCurrentItem(0);
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if(position==0) {
            HomeFragment homeFragment =new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, homeFragment).commit();
        } else  if(position==1) {
            FindFragment findFragment =new FindFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, findFragment).commit();
        } else if(position==2) {
            AlarmFragment alarmFragment=new AlarmFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,alarmFragment).commit();
        } else if(position==3) {
            StarFragment starFragment=new StarFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,starFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent writeIntent = null;

        switch (item.getItemId()) {
            case R.id.writeBtn:
                Toast.makeText(getApplicationContext(), "글쓰기", Toast.LENGTH_LONG).show();
                writeIntent = new Intent(this, WriteActivity.class);
                startActivity(writeIntent);
                finish();
                return true;

            case R.id.infoBtn:
                Toast.makeText(getApplicationContext(), "내정보", Toast.LENGTH_LONG).show();
                writeIntent = new Intent(this, MyInfoActivity.class);
                startActivityForResult(writeIntent, REQUEST_CODE_MYINFO);
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_MYINFO) {}
    }
}
















