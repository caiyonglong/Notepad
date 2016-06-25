package com.hkcyl.notepad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yonglong on 2016/6/24.
 */
public class NoteDBHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "notepad.db";
    public static final String TABLE_NAME = "notes";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String ID = "_id";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";

    public NoteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE
                + " VARCHAR(100) NOT NULL," + CONTENT + " VARCHAR(100)," + START_TIME
                + " VARCHAR(100) NOT NULL," + END_TIME + " VARCHAR(100) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
