package com.example.android.timemanagement.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.timemanagement.data.Contract;

import static com.example.android.timemanagement.data.Contract.TABLE_PROJECT.TABLE_NAME;
import static com.example.android.timemanagement.data.Contract.TABLE_PROJECT.TABLE_NAME;

/**
 * Created by Siriporn on 7/24/2017.
 */

public class DatabaseUtils {

    private static final String TAG = "dbUtils";

    public static Cursor getAllProject(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_PROJECT.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public static Cursor getAllSubject(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_SUBJECT.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }


    public static Cursor getDailyTask(SQLiteDatabase db, String date) {
        //add some spaces in front of aliases to avoid typo
        String taskAlias = " " + "T";
        String subjectAlias = " " + "S";
        String projectAlias = " " + "P";

        //SELECT T._id, s.title AS subjectTitle, p.title AS projectTitle,
        //date, startHour, startMinute, startMidDay
        //endHour, endMinute, endMidDay, taskTotalMinutes
        //FROM tasks T
        //INNER JOIN subjects S ON T.subjectId = S._id
        //INNER JOIN projects P ON T.projectId = P._id
        //WHERE T.date = ? ;
        String query = "SELECT " + taskAlias + "." + Contract.TABLE_TASK._ID +
                ", " + subjectAlias + "." + Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE + " AS " + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE +
                ", " + projectAlias + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE + " AS " + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_DATE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES +
                " FROM " + Contract.TABLE_TASK.TABLE_NAME + taskAlias +
                " INNER JOIN " + Contract.TABLE_SUBJECT.TABLE_NAME + subjectAlias + " ON " +
                taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = " + subjectAlias + "." + Contract.TABLE_SUBJECT._ID +
                " INNER JOIN " + Contract.TABLE_PROJECT.TABLE_NAME + projectAlias + " ON " +
                taskAlias + "." +Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + projectAlias + "." + Contract.TABLE_PROJECT._ID +
                " WHERE " + taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE + " = '" + date + "';";
                //" WHERE 0;";

        Log.d(TAG, "Select table SQL: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));
/*
        try {
            String[] columnNames = cursor.getColumnNames();
            for(String name: columnNames)
                Log.d(TAG, "column "+name);
        } finally {
            cursor.close();
        }
*/
        return cursor;
    }

    public static long addTask(SQLiteDatabase db, String date, String subject, String project
            , int startHour, int startMinute, String startMidday
            , int endHour, int endMinute, String endMidday
            , int totalMinutes) {

        //check if subject not exist then insert
        int subjectId = findData(db,Contract.TABLE_SUBJECT.TABLE_NAME, Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        if(subjectId < 0){
            subjectId = addSubject(db, subject);
        }
        //check if project not exist then insert
        int projectId = findData(db,Contract.TABLE_PROJECT.TABLE_NAME, Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        if(projectId < 0){
            projectId = addProject(db, project);
        }

        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_DATE, date);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID, subjectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID, projectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR, startHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE, startMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY, startMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR, endHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE, endMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY, endMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES, totalMinutes);

        return db.insert(Contract.TABLE_TASK.TABLE_NAME, null, cv);
    }

    public static int addSubject(SQLiteDatabase db, String subject) {
        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        return (int) db.insert(Contract.TABLE_SUBJECT.TABLE_NAME, null, cv);
    }

    public static int addProject(SQLiteDatabase db, String project) {
        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        return (int) db.insert(Contract.TABLE_PROJECT.TABLE_NAME, null, cv);
    }

    public static int findData(SQLiteDatabase db, String tableName, String dbField, String fieldValue) {
        String[] columns = { "_id", dbField };
        String selection = dbField + " =?";
        String[] selectionArgs = { fieldValue };
        String limit = "1";
        int id = -1;

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null, limit);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));
        if ( cursor.moveToFirst() ) {
            id = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        cursor.close();
        return id;

    }
    public static boolean removeTask(SQLiteDatabase db, long id) {
        Log.d(TAG, "deleting id: " + id);
        return db.delete(Contract.TABLE_TASK.TABLE_NAME, Contract.TABLE_TASK._ID + "=" + id, null) > 0;
    }

    public static int updateTask(SQLiteDatabase db, String date, String subject, String project
            , int startHour, int startMinute, String startMidday
            , int endHour, int endMinute, String endMidday
            , int totalMinutes, long id) {

        //check if subject not exist then insert
        int subjectId = findData(db,Contract.TABLE_SUBJECT.TABLE_NAME, Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        if(subjectId < 0){
            subjectId = addSubject(db, subject);
        }
        //check if project not exist then insert
        int projectId = findData(db,Contract.TABLE_PROJECT.TABLE_NAME, Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        if(projectId < 0){
            projectId = addProject(db, project);
        }

        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_DATE, date);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID, subjectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID, projectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR, startHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE, startMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY, startMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR, endHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE, endMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY, endMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES, totalMinutes);

        return db.update(Contract.TABLE_TASK.TABLE_NAME, cv, Contract.TABLE_TASK._ID + "=" + id, null);
    }
}
