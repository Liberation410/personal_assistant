package com.zjgsu.shenyilin.personal_assistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 2017/5/20.
 */

public class SqliteDBConnect extends SQLiteOpenHelper {

    public SqliteDBConnect(Context context){
        super(context,"NotePad",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Table before Create");
        db.execSQL("create table note(noteId Integer primary key,noteName varchar(20),noteTime varchar(20),noteContent varchar(400) ");
        System.out.println("Table after Create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
