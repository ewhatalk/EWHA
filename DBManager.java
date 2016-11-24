package com.tutorials.hp.bottomnavrecycler.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by robinitic on 2016. 5. 13..
 */

public class DBManager {
    private static final String DB_NAME = "ETALK.db";
    public static final int dbVersion = 1;
    private static int flag=0;

    public static SQLiteDatabase db;

    String createSql = "create table class_table("
            + " class_id INTEGER  NOT NULL PRIMARY KEY, "
            + " type varchar(20)  NOT NULL, "
            + " college varchar(20)  NOT NULL, "
            + " major varCHAR(20)  NOT NULL, "
            + " grade INTEGER  , "
            + " subj_name_s varchar(50)  NOT NULL, "
            + " class_num INTEGER  NOT NULL, "
            + " prof varchar(50) )";

    private OpenHelper opener; // DB opener
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, DB_NAME, null, dbVersion);
        db = opener.getWritableDatabase();
        if(db.isOpen()) {
            Cursor c= db.rawQuery("SELECT name from sqlite_master where type='table' and name='class_table';", null);
            c.moveToFirst();

            if (c.getCount()==0) {
                db.execSQL(createSql);
            } else {
                db.execSQL("drop table class_table;");
                db.execSQL(createSql);
            }
        }
    }

    private class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {
            db.execSQL(createSql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

    public void insert(int c_id,String type,String col,String maj, int grd,String sn,int cn,String pro){
        db.execSQL("insert into class_table values ( '"+c_id +"','"+type +"','"+col+"','"+maj+"','"+grd+"','"+sn+"','"+cn+"','"+pro+"');");
    }

    public static String getQuery(String w_type,String w_college,String w_major, String w_grade) {
        String aSQL = "select class_id,subj_name_s,class_num,prof" + " from class_table ";

        if (w_type.equals("전체")) {
            if (w_major.equals("전체")) {
                if (w_grade.equals("전체")) {
                    flag = 1;
                    return aSQL + "where college=?";
                } else {
                    flag = 2;
                    return aSQL + "where college =? and grade= ?";
                }
            } else {
                if (w_grade.equals("전체")) {
                    flag = 3;
                    return aSQL + "where college=? and major= ?";
                } else {
                    flag = 4;
                    return aSQL + "where college=? and major=? and grade=?";
                }
            }
        } else {
            if (w_major.equals("전체")) {
                if (w_grade.equals("전체")) {
                    flag = 5;
                    return aSQL + "where type=? and college=?";
                } else {
                    flag = 6;
                    return aSQL + "where type=? and college =? and grade= ?";
                }
            } else {
                if (w_grade.equals("전체")) {
                    flag = 7;
                    return aSQL + "where type =? and college=? and major= ?";
                } else {
                    flag = 8;
                    return aSQL + "where type=? and college=? and major=? and grade=?";
                }
            }
        }
    }

    public static Cursor queryclass_Table(String w_type,String w_college,String w_major,String w_grade ) {
        String[] args1={w_college};
        String[] args2={w_college,w_grade};
        String[] args3={w_college,w_major};
        String[] args4={w_college,w_major,w_grade};
        String[] args5={w_type,w_college};
        String[] args6={w_type,w_college,w_grade};
        String[] args7={w_type,w_college,w_major};
        String[] args8={w_type,w_college,w_major,w_grade};

        String aSQL=getQuery(w_type,w_college,w_major,w_grade);
        Cursor outCursor=null;

        switch(flag) {
            case 1:
                outCursor = db.rawQuery(aSQL, args1);
                break;
            case 2:
                outCursor = db.rawQuery(aSQL, args2);
                break;
            case 3:
                outCursor = db.rawQuery(aSQL, args3);
                break;
            case 4:
                outCursor = db.rawQuery(aSQL, args4);
                break;
            case 5:
                outCursor = db.rawQuery(aSQL, args5);
                break;
            case 6:
                outCursor = db.rawQuery(aSQL, args6);
                break;
            case 7:
                outCursor = db.rawQuery(aSQL, args7);
                break;
            case 8:
                outCursor = db.rawQuery(aSQL, args8);
                break;
        }        return (outCursor);
    }

    public static Cursor queryclass_Table1(String c_id) {
        String aSQL = "select subj_name_s,class_num,user_id,date,title,content,post_id from posting_table " +
                "left join class_table  on class_table.class_id =posting_table.class_id  where class_table.class_id=? order by post_id desc;";

        Cursor outCursor=null;
        String[] args={c_id};
        outCursor= db.rawQuery(aSQL,args);

        return (outCursor);
    }
}