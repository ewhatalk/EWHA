package com.tutorials.hp.bottomnavrecycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorials.hp.bottomnavrecycler.SearchDialog.SearchDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/*
* 2016/09/07
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class WriteActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SEARCH = 1004;

    private final String UrlPath = "http://121.131.185.173/article_down.php";//에뮬레이터용

    EditText QinputMessage;
    EditText inputMessage;
    EditText class_idStr;
    TextView inputCount;

    Button sendButton;
    Button closeButton;

    InputMethodManager imm;
    RelativeLayout writelayout;

    String user_id = null;/////////sharedPreference
    String class_id = null;
    String title = null;
    String content = null;

    String rtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        QinputMessage = (EditText)findViewById(R.id.QInput);
        inputMessage = (EditText)findViewById(R.id.inputMessage);

        class_idStr = (EditText) findViewById(R.id.class_id_Input);
        inputCount = (TextView)findViewById(R.id.inputCount);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        writelayout = (RelativeLayout)findViewById(R.id.writelayout);
        writelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(QinputMessage.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(inputMessage.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(class_idStr.getWindowToken(), 0);
            }
        });

        class_idStr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent writeIntent = new Intent(getApplicationContext(), SearchDialog.class);
                startActivityForResult(writeIntent, REQUEST_CODE_SEARCH);
            }
        });

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title = QinputMessage.getText().toString();
                content = inputMessage.getText().toString();
                class_id = class_idStr.getText().toString();

                if (class_id.length() < 1) {
                    Toast.makeText(getApplicationContext(), "교과목을 선택해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if (title.length()<1) {
                        Toast.makeText(getApplicationContext(), "제목이 공백입니다!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (content.length()<1) {
                            Toast.makeText(getApplicationContext(), "내용이 공백입니다!", Toast.LENGTH_SHORT).show();
                        } else {
                            user_id = loadID();
                            class_id = class_idStr.getText().toString();
                            title = QinputMessage.getText().toString();
                            content = inputMessage.getText().toString();

                            try {
                                new PostArticle().execute().get();
                            } catch ( InterruptedException e ) {
                                e.printStackTrace();
                            } catch ( ExecutionException e ) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextWatcher watcher = new TextWatcher() {
            public void onTextChanged(CharSequence str, int start, int before, int count) {
                byte[] bytes = null;
                try {
                    bytes = str.toString().getBytes("KSC5601");
                    int strCount = bytes.length;
                    inputCount.setText(strCount + " / 1024 바이트");
                } catch(UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable strEditable) {
                String str = strEditable.toString();
                try {
                    byte[] strBytes = str.getBytes("KSC5601");
                    if(strBytes.length > 1024) {
                        strEditable.delete(strEditable.length()-2, strEditable.length()-1);
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        inputMessage.addTextChangedListener(watcher);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_SEARCH) {
            rtn = intent.getExtras().getString("class_id");
            class_idStr.setText(rtn);
        }
    }

    class PostArticle extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            try {
                URL url = new URL(UrlPath); // Set url

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoInput(true); // Available Write
                con.setDoOutput(true); // Available Read
                con.setUseCaches(false); // No cash
                con.setRequestMethod("POST");

                String param = "user_id=" + user_id + "&class_id=" + class_id + "&title=" + title + "&content=" + content;

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

    private String loadID() {
        SharedPreferences pref = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String str = pref.getString("user_id", "");

        return str;
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(intent);

        finish();
    }
}
















