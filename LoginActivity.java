package com.tutorials.hp.bottomnavrecycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/*
* 2016/09/09
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class LoginActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;

    Button loginButton;
    Button joinButton;

    InputMethodManager imm2;
    RelativeLayout loginlayout;

    DBManager2 dbManager2;

    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        dbManager2= new DBManager2(this);

        try {
            s = new phpDown().execute("http://121.131.185.173/member_table_up.php").get();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            e.printStackTrace();
        }
        LoginExecute(s);

        imm2 = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        loginlayout = (RelativeLayout)findViewById(R.id.loginlayout);
        loginlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm2 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(usernameInput.getWindowToken(), 0);
                imm2.hideSoftInputFromWindow(passwordInput.getWindowToken(), 0);
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                Cursor c = dbManager2.queryclass_Table(username,password);
                if(c.getCount()==0){
                    if (username.length()<1) {
                        Toast.makeText(getApplicationContext(), "ID가 공백입니다!", Toast.LENGTH_SHORT).show();

                    } else if(password.length()<1) {
                            Toast.makeText(getApplicationContext(), "비밀번호가 공백입니다!", Toast.LENGTH_SHORT).show();
                        }
                    Toast toast = Toast.makeText(getBaseContext(), "일치하는 사용자가 없습니다.", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    c.moveToFirst();

                    if (c.getString(0).equals(username) && c.getString(1).equals(password)) {
                        saveID(username);

                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);

                        startActivity(intent);

                        finish();
                    }
                }
            }
        });

        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent joinintent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(joinintent);
                finish();
            }
        });
    }

    public void LoginExecute(String str) {
        String u_id;
        String u_pw;
        String u_em;

        try {
            JSONObject root = new JSONObject(str);
            JSONArray ja = root.getJSONArray("results");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                u_id = jo.getString("user_id");
                u_pw= jo.getString("user_password");
                u_em = jo.getString("user_email");

                dbManager2.insert(u_id,u_pw,u_em);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveID (String ID) {
        String str = ID;
        SharedPreferences pref = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_id", str);
        editor.commit();
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
}