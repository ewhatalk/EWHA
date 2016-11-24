package com.tutorials.hp.bottomnavrecycler.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2016-10-04.
 */

public class DBManager5 {
    private static final String DB_NAME = "ETALK.db";
    public static final int dbVersion = 1;

    public static SQLiteDatabase db;

    String createSql = "create table if not exists comment_table (" +
            "comment_id int primary key," +
            "comment_user_id varchar(8) not null," +
            "post_id int ," +
            "comment_content text ," +
            "date_comment timestamp default current_timestamp," +
            "FOREIGN KEY (post_id) REFERENCES posting_table (post_id) on update cascade on delete cascade" +
            ")";

    private OpenHelper opener; // DB opener
    private Context context;

    public DBManager5(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, DB_NAME, null, dbVersion);
        db = opener.getWritableDatabase();
        if (db.isOpen()) {
            Cursor c = db.rawQuery("SELECT name from sqlite_master where type='table' and name='comment_table';", null);
            c.moveToFirst();
            if (c.getCount() == 0) {
                db.execSQL(createSql);
            } else {
                db.execSQL("drop table comment_table;");
                db.execSQL(createSql);
            }
        }
    }

    public class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {}

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {}
    }

    public void insert(int c_id, String u_id, int p_id,  String cont, String date) {
        db.execSQL("insert into comment_table values ( '" + c_id + "','" + u_id + "','" + p_id + "','" + cont + "','" + date + "');");
    }

    public void insert2(int a){
        db.execSQL("insert into comment_table (boolean) values ("+a+"); ");
    }

    public void delete(int c_id){
        db.execSQL("delete from comment_table where comment_id="+c_id+";");
    }

    public static Cursor queryclass_Table1(String p_id) {
        String aSQL = "select comment_user_id,date_comment,comment_content" +
                " from comment_table where post_id=?";

        Cursor outCursor = null;
        String[] args={p_id};
        outCursor = db.rawQuery(aSQL, args);

        return (outCursor);
    }

    public static Cursor queryclass_Table2(String u_id) {
        String bSQL="select subj_name_s,class_num,user_id,date,title,content,post_id from posting_table " +
                "left join class_table on class_table.class_id=posting_table.class_id where user_id =? and boolean='1';";

        String aSQL ="select subj_name,class_num,user_id,date_comment,title,content " +
                "from comment_table as a " +
                "left outer join posting_table as b " +
                "on a.post_id=b.post_id " +
                "left outer join class_table as c " +
                "on b.class_id=c.class_id " +
                "where b.user_id=? and boolean='1' ;";

        Cursor outCursor = null;
        String[] args = {u_id};
        outCursor = db.rawQuery(bSQL,args);

        return (outCursor);

    }
}
