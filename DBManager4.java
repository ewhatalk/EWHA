package com.tutorials.hp.bottomnavrecycler.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 2016-10-10.
 */

public class DBManager4 {
    private static final String DB_NAME = "ETALK.db";
    public static final int dbVersion = 1;

    public static SQLiteDatabase db;

    String createSql = "create table if not exists star_table("
            +"user_id varchar(8) ,"
            + " subj_name varchar(50) , "
            + " class_num_s varchar(2) " +
            ",PRIMARY KEY (user_id,subj_name,class_num_s) )";

    private OpenHelper opener; // DB opener
    private Context context;

    public DBManager4(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, DB_NAME, null, dbVersion);
        db = opener.getWritableDatabase();
        db.execSQL(createSql);
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

    public void insert(String u_id,String subj_name, String c_id){
        db.execSQL("insert into star_table values ( '"+u_id+"','"+subj_name +"','"+c_id +"');");
    }

    public void delete(String u_id,String subj_name,String c_id){
        db.execSQL("delete from star_table where user_id ='"+u_id +"' and subj_name= '"+subj_name+"' and class_num_s= '"+c_id+"';");
    }

    public static Cursor queryclass_Table1(String u_id) {
        String aSQL = "select subj_name,class_num_s,class_id from star_table " +
                "left join class_table on class_table.subj_name_s=star_table.subj_name where user_id =?" ;

        Cursor outCursor=null;
        String[] args={u_id};
        outCursor= db.rawQuery(aSQL,args);

        return (outCursor);
    }

    public static Cursor queryclass_Table2(String u_id,String subj,String c_no) {
        String aSQL = "select subj_name from star_table where user_id =? and subj_name=? and class_num_s=?" ;

        Cursor outCursor=null;
        String[] args={u_id,subj,c_no};
        outCursor= db.rawQuery(aSQL,args);

        return (outCursor);
    }
}
