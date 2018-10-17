package com.example.cxf24.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String NOTE_DATABASE_NAME = "notebook";

    public static final String NOTE_TABLE_NAME = "note";

    public static final String CREATE_NOTE_TABLE = "create table "
            + NOTE_TABLE_NAME
            + " (_id integer primary key autoincrement, objectid text,"
            + "date varchar(10),  username varchar(10), title varchar(10), content text)";

    public DatabaseHelper(Context context) {
        super(context, NOTE_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}