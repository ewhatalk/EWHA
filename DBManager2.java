package com.tutorials.hp.bottomnavrecycler.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2016-10-04.
 */
public class DBManager2 {
    private static final String DB_NAME = "ETALK.db";
    public static final int dbVersion = 1;

    public static SQLiteDatabase db;

    String createSql = "create table member_table ("
            + " user_id varchar(8) PRIMARY KEY , "
            + " user_password varchar(8) NOT NULL, "
            + " user_email varchar(50) NOT NULL) ";

    private OpenHelper opener; // DB opener

    private Context context;

    public DBManager2(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, DB_NAME, null, dbVersion);
        db = opener.getWritableDatabase();

        if(db.isOpen()) {
            Cursor c= db.rawQuery("SELECT name from sqlite_master where type='table' and name='member_table';", null);
            c.moveToFirst();
            if (c.getCount()==0){
                db.execSQL(createSql);
            } else {
                db.execSQL("drop table member_table;");
                db.execSQL(createSql);
            }
        }
    }

    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {}

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {}
        }

    public void insert(String u_id,String u_pw,String u_email){
        db.execSQL("insert into member_table values ( '"+u_id +"','"+u_pw +"','"+u_email+"');");
    }

    public static Cursor duplicate(String u_id){
       String aSQL= "select user_id from member_table where user_id =?";
       Cursor outCursor=null;
       String[] args={u_id};
       outCursor=db.rawQuery(aSQL,args);

       return outCursor;
    }

    public static Cursor queryclass_Table(String u_id, String u_pw ) {
        String aSQL = "select user_id,user_password"
                   + " from member_table "
                   + "where user_id= ? and user_password= ?";

        Cursor outCursor=null;
        String[] args={u_id,u_pw};
        outCursor= db.rawQuery(aSQL,args);

        return (outCursor);
    }
}