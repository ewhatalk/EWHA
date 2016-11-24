package com.tutorials.hp.bottomnavrecycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.tutorials.hp.bottomnavrecycler.Database.DBManager2;
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
* 2016/09/13
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class JoinActivity extends AppCompatActivity {
    EditText idInput;
    EditText passInput;
    EditText passInput2;
    EditText email;

    Button joinButton;
    Button closeButton2;

    String idStr = null;
    String passStr = null;
    String passStr2 = null;
    String emailStr = null;

    DBManager2 dbManager2;

    InputMethodManager imm3;
    RelativeLayout joinLayout;

    private final String signup_user_information_UrlPath = "http://121.131.185.173/join.php";//에뮬레이터용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idInput = (EditText) findViewById(R.id.idInput);
        passInput = (EditText) findViewById(R.id.passInput);
        passInput2 = (EditText) findViewById(R.id.passInput2);
        email = (EditText) findViewById(R.id.email);

        imm3 = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        joinLayout = (RelativeLayout)findViewById(R.id.joinLayout);
        joinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm3 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm3.hideSoftInputFromWindow(idInput.getWindowToken(), 0);
                imm3.hideSoftInputFromWindow(passInput.getWindowToken(), 0);
                imm3.hideSoftInputFromWindow(email.getWindowToken(), 0);
                imm3.hideSoftInputFromWindow(passInput2.getWindowToken(), 0);
            }
        });

        idInput.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popup();

            }
        });

        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                idStr = idInput.getText().toString();
                passStr = passInput.getText().toString();
                passStr2 = passInput2.getText().toString();

                if (idStr.length()<1) {
                    Toast.makeText(getApplicationContext(), "ID가 공백입니다!", Toast.LENGTH_SHORT).show();

                } else {
                    if (passStr.length()<1) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 공백입니다!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!passStr.equals(passStr2)) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        idStr = idInput.getText().toString();
                        passStr = passInput.getText().toString();
                        emailStr = email.getText().toString();

                        try {
                            new SignupUserInformation().execute().get();
                        } catch ( InterruptedException e ) {
                            e.printStackTrace();
                        } catch ( ExecutionException e ) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }
            }
        });

        closeButton2 = (Button) findViewById(R.id.closeButton2);
        closeButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    class SignupUserInformation extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            // TODO Auto-generated method
            try {
                URL url = new URL(signup_user_information_UrlPath); // Set url

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoInput(true); // Available Write
                con.setDoOutput(true); // Available Read
                con.setUseCaches(false); // No cash
                con.setRequestMethod("POST");

                String param = "user_id=" + idStr + "&user_password=" + passStr + "&user_email=" + emailStr;

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

    public void popup () {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.iddialog);

        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.idsearch_dialog, relativeLayout, true);

        AlertDialog.Builder aDialog = new AlertDialog.Builder(this);

        aDialog.setTitle("아이디 중복조회");
        aDialog.setView(v); //dialog.xml 파일을 뷰로 셋팅
        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = (EditText) ((AlertDialog) dialog).findViewById(R.id.idInputdialog); //팝업창에 EditText부분에 아이디
                String id = edit.getText().toString();
                Cursor c= dbManager2.duplicate(id);

                if(c.getCount()==0) {
                    Toast.makeText(getApplicationContext(), "사용가능한 아이디 입니다!", Toast.LENGTH_SHORT).show();
                    idInput.setText(id);
                }
                else {
                    Toast.makeText(getApplicationContext(), "사용 불가능한 아이디 입니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog ad = aDialog.create();
        ad.show();
    }
}
















