package com.example.android.timemanagement.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.timemanagement.data.Contract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.example.android.timemanagement.data.Contract.TABLE_PROJECT.TABLE_NAME;
import static com.example.android.timemanagement.data.Contract.TABLE_PROJECT.TABLE_NAME;

/**
 * Created by Siriporn on 7/24/2017.
 */

public class DatabaseUtils {

    private static final String TAG = "dbUtils";

    public static Cursor getAllTask(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_TASK.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

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

    public static Cursor getTodaysTask(SQLiteDatabase db) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        return db.query(
                Contract.TABLE_TASK.TABLE_NAME,
                null,
                Contract.TABLE_TASK.COLUMN_NAME_DATE + " = ?",
                new String[]{dbDateFormat.format(new Date())},
                null,
                null,
                null);
    }

    public static ArrayList<Cursor> getThisMonthTask(SQLiteDatabase db)
    {
        ArrayList<Cursor> weeksCursor = new ArrayList<>();
        ///*debug: tracks what is currently in the weeksCursor by date*/ ArrayList<String> weekTracker = new ArrayList<>();
        DateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Calendar c = Calendar.getInstance();

        int dayOfMonth = 1;
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), dayOfMonth);
        //*debug: corner case for this method, April 2017 [two day ahead in the next month]*/c.set(c.get(Calendar.YEAR), Calendar.APRIL, dayOfMonth);
        int thisMonth = c.get(Calendar.MONTH);

        String startDate;
        String endDate;
        startDate = dbDateFormat.format(c.getTime());
        do {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            {
                endDate = dbDateFormat.format(c.getTime());

                {// NOTE[Philip]: this bracket is just for readability for me
                    Cursor cursor = db.query(
                            Contract.TABLE_TASK.TABLE_NAME,
                            null,
                            Contract.TABLE_TASK.COLUMN_NAME_DATE + " BETWEEN ? AND ?",
                            new String[]{startDate, endDate},
                            null,
                            null,
                            Contract.TABLE_TASK.COLUMN_NAME_DATE);
                    weeksCursor.add(cursor);
                    ///*debug: tracks what is currently in the weeksCursor by date*/ weekTracker.add(startDate + " - " + endDate);
                }

                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), ++dayOfMonth);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                //IMPORTANT[philip]: startDate is only null when this loop detect that the current
                // month is a new month.
                if(thisMonth == c.get(Calendar.MONTH))
                {
                    startDate = dbDateFormat.format(c.getTime());
                }
                else
                {
                    startDate = null;
                }

            }
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), ++dayOfMonth);
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        }
        while (thisMonth == c.get(Calendar.MONTH));

        if(startDate != null)
        {
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), --dayOfMonth);
            while(thisMonth != c.get(Calendar.MONTH))
            {c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), --dayOfMonth);}
            endDate = dbDateFormat.format(c.getTime());

            Cursor cursor = db.query(
                    Contract.TABLE_TASK.TABLE_NAME,
                    null,
                    Contract.TABLE_TASK.COLUMN_NAME_DATE + " BETWEEN ? AND ?",
                    new String[]{startDate, endDate},
                    null,
                    null,
                    Contract.TABLE_TASK.COLUMN_NAME_DATE);
            weeksCursor.add(cursor);
            ///*debug: tracks what is currently in the weeksCursor by date*/ weekTracker.add(startDate + " - " + endDate);
        }

        return weeksCursor;
    }

    public static Cursor getThisWeeksTask(SQLiteDatabase db) {

        Calendar c = GregorianCalendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        String startDate = dbDateFormat.format(c.getTime());
        c.add(Calendar.DATE, 6);
        String endDate = dbDateFormat.format(c.getTime());

        return db.query(
                Contract.TABLE_TASK.TABLE_NAME,
                null,
                Contract.TABLE_TASK.COLUMN_NAME_DATE + " BETWEEN ? AND ?",
                new String[]{startDate, endDate},
                null,
                null,
                Contract.TABLE_TASK.COLUMN_NAME_DATE);
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
                taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + projectAlias + "." + Contract.TABLE_PROJECT._ID +
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
        int subjectId = findData(db, Contract.TABLE_SUBJECT.TABLE_NAME, Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        if (subjectId < 0) {
            subjectId = addSubject(db, subject);
        }
        //check if project not exist then insert
        int projectId = findData(db, Contract.TABLE_PROJECT.TABLE_NAME, Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        if (projectId < 0) {
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
        String[] columns = {"_id", dbField};
        String selection = dbField + " =?";
        String[] selectionArgs = {fieldValue};
        String limit = "1";
        int id = -1;

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null, limit);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));
        if (cursor.moveToFirst()) {
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
        int subjectId = findData(db, Contract.TABLE_SUBJECT.TABLE_NAME, Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        if (subjectId < 0) {
            subjectId = addSubject(db, subject);
        }
        //check if project not exist then insert
        int projectId = findData(db, Contract.TABLE_PROJECT.TABLE_NAME, Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        if (projectId < 0) {
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

    public static void dummyTask(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase dbAdd = dbHelper.getWritableDatabase();

        dbAdd.delete(Contract.TABLE_TASK.TABLE_NAME, null, null);
        dbAdd.delete(Contract.TABLE_SUBJECT.TABLE_NAME, null, null);
        dbAdd.delete(Contract.TABLE_PROJECT.TABLE_NAME, null, null);

        DatabaseUtils.addTask(dbAdd, "07/01/2017", "math hw", "school",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/09/2017", "get money from aim for project", "android project",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "07/16/2017", "walk for 30 min", "diet",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "reading", "school",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/23/2017", "walk for 30 min", "diet",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "eat a salad", "diet",
                12, 00, "AM", 1, 00, "AM",
                ((1) * 60) + (00 - 00));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "eat a salad", "diet",
                8, 30, "AM", 9, 00, "AM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "get money from aim for project", "android project",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "Hello", "meeting",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "bye", "meeting",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "commit graph layout", "school",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "cooking snack", "diet",
                11, 31, "PM", 11, 59, "PM",
                ((11 - 11) * 60) + (59 - 31));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "english essay", "school",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "math hw", "school",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "added add task button", "android project",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "finish graph layout", "school",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "finish updating task", "school",
                12, 30, "AM", 3, 30, "AM",
                ((3) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "finish Month button", "android project",
                12, 00, "AM", 1, 30, "AM",
                ((1) * 60) + (30 - 00));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "finish Month button layout", "android project",
                1, 30, "AM", 2, 30, "AM",
                ((2 - 1) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "finish Week button", "android project",
                2, 30, "AM", 4, 30, "AM",
                ((4 - 2) * 60) + (30 - 30));

        dbHelper.close();
        dbAdd.close();
    }
}
