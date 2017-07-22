package com.example.android.timemanagement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vahedi on 7/19/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_TASK.TABLE_NAME + " ("+
                Contract.TABLE_TASK._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_TASK.COLUMN_NAME_DATE + " DATE NOT NULL, " +
                Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " INTEGER NOT NULL, " +
                Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " INTEGER NOT NULL, " +
                Contract.TABLE_TASK.COLUMN_NAME_START_HOUR + " INTEGER NOT NULL," +
                Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE + " INTEGER NOT NULL," +
                Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY + " INTEGER NOT NULL," +
                Contract.TABLE_TASK.COLUMN_NAME_END_HOUR + " INTEGER NOT NULL," +
                Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE + " INTEGER NOT NULL," +
                Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY + " INTEGER NOT NULL,"+
                Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES + "INTEGER NULL); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);

        queryString = "CREATE TABLE " + Contract.TABLE_SUBJECT.TABLE_NAME + " ("+
                Contract.TABLE_SUBJECT._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE + " TEXT NOT NULL); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);

        queryString = "CREATE TABLE " + Contract.TABLE_PROJECT.TABLE_NAME + " ("+
                Contract.TABLE_PROJECT._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_PROJECT.COLUMN_NAME_TITLE + " TEXT NOT NULL); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table " + Contract.TABLE_TODO.TABLE_NAME + " if exists;");

    }
}