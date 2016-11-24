package com.tutorials.hp.bottomnavrecycler.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2016-10-04.
 */

public class DBManager3 {
    private static final String DB_NAME = "ETALK.db";
    public static final int dbVersion = 1;

    public static SQLiteDatabase db;

    String createSql = "create table posting_table ("
            + " post_id integer NOT NULL PRIMARY KEY autoincrement, "
            + " user_id varchar(8) NOT NULL references member_table(user_id), "
            + " class_id integer NOT NULL references class_table(class_id), "
            + " title varchar(200) NOT NULL, "
            + " content text NOT NULL ,"
            + " date timestamp default current_timestamp," +
            "boolean int) ";

    private OpenHelper opener; // DB opener
    private Context context;

    public DBManager3(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, DB_NAME, null, dbVersion);
        db = opener.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name from sqlite_master where type='table' and name='posting_table';", null);
        c.moveToFirst();

        if (c.getCount() == 0) {
            db.execSQL(createSql);
        } else {
            db.execSQL("drop table posting_table;");
            db.execSQL(createSql);
        }
    }

    public class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {
            Cursor c = db.rawQuery("SELECT name from sqlite_master where type='table' and name='posting_table';", null);
            c.moveToFirst();

            if (c.getCount() == 0) {
                db.execSQL(createSql);
            } else {
                db.execSQL("drop table posting_table;");
                db.execSQL(createSql);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {}
    }

    public void insert(int p_id, String u_id, String c_id, String title, String cont, String date,String bool) {
        db.execSQL("insert into posting_table values ( '" + p_id + "','" + u_id + "','" + c_id + "','" + title + "','" + cont + "','" + date +"','"+bool+ "');");
    }

    public void delete(int p_id){
        db.execSQL("delete from posting_table where post_id="+p_id+";");
    }

    public void update(String p_id){
        db.execSQL("update posting_table set boolean ='1' where post_id= "+p_id+";");
    }

    public static Cursor queryclass_Table1() {
        String aSQL = "select subj_name_s,class_num,user_id,date,title,content,post_id" +
                " from posting_table " +
                "left join class_table on class_table.class_id=posting_table.class_id order by post_id desc limit 15;";

        Cursor outCursor = null;
        outCursor = db.rawQuery(aSQL, null);

        return (outCursor);
    }

    public static Cursor queryclass_Table2(String u_id) {
        String aSQL ="select subj_name_s,class_num,user_id,date,title,content,post_id from posting_table " +
                "left join class_table on class_table.class_id=posting_table.class_id where user_id =? order by post_id desc;";

        Cursor outCursor = null;
        String[] args = {u_id};
        outCursor = db.rawQuery(aSQL,args);

        return (outCursor);
    }
}
