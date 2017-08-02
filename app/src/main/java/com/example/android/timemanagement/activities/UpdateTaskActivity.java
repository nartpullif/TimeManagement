package com.example.android.timemanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.data.Contract;
import com.example.android.timemanagement.data.DBHelper;
import com.example.android.timemanagement.data.DatabaseUtils;
import com.example.android.timemanagement.utilities.SetDate;
import com.example.android.timemanagement.utilities.SetTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mark on 7/4/17.
 */

public class UpdateTaskActivity extends AppCompatActivity {

    private SetDate dateSetDate;
    private AutoCompleteTextView subjectAutoText;
    private AutoCompleteTextView projectAutoText;
    private SetTime startSetTime;
    private SetTime endSetTime;
    private Button updateTaskButton;

    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private Context context;

    private final String TAG = "updateTaskActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        context = this;
        dateSetDate = new SetDate((EditText) findViewById(R.id.et_date), this);
        subjectAutoText = (AutoCompleteTextView) findViewById(R.id.at_subject);
        projectAutoText = (AutoCompleteTextView) findViewById(R.id.at_project);
        startSetTime = new SetTime((EditText) findViewById(R.id.et_start), this);
        endSetTime = new SetTime((EditText) findViewById(R.id.et_end), this);

        helper = new DBHelper(context);
        db = helper.getReadableDatabase();

        //load subject title from db to autoText
        cursor = DatabaseUtils.getAllSubject(db);
        List<String> subjectList = new ArrayList<String>();
        if(cursor != null){
            int titleCol = cursor.getColumnIndex(Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE);
            while(cursor.moveToNext()){
                subjectList.add(cursor.getString(titleCol));
            }
            cursor.close();
        }
        ArrayAdapter subjectAdapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, subjectList);
        subjectAutoText.setAdapter(subjectAdapter);

        //load project title from db to autoText
        cursor = DatabaseUtils.getAllProject(db);
        List<String> projectList = new ArrayList<String>();
        if(cursor != null){
            int titleCol = cursor.getColumnIndex(Contract.TABLE_PROJECT.COLUMN_NAME_TITLE);
            while(cursor.moveToNext()){
                projectList.add(cursor.getString(titleCol));
            }
            cursor.close();
        }
        ArrayAdapter projectAdapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, projectList);
        projectAutoText.setAdapter(projectAdapter);

//        extras.putString("EXTRA_SUBJECT", subject);
//        extras.putString("EXTRA_PROJECT", project);
//        extras.putString("EXTRA_START_TIME", startTime);
//        extras.putString("EXTRA_END_TIME", endTime);
//        extras.putString("EXTRA_DATE", date);
//        extras.putLong("EXTRA_ID", id);

        //set all fields
        Bundle extras = getIntent().getExtras();
        dateSetDate.setDate(extras.getString("EXTRA_DATE"));
        subjectAutoText.setText(extras.getString("EXTRA_SUBJECT"));
        projectAutoText.setText(extras.getString("EXTRA_PROJECT"));
        startSetTime.setTime(extras.getString("EXTRA_START_TIME"));
        endSetTime.setTime(extras.getString("EXTRA_END_TIME"));
        final long id = extras.getLong("EXTRA_ID");

        updateTaskButton = (Button) findViewById(R.id.btn_actionAddTask);
        updateTaskButton.setText("UPDATE");
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper = new DBHelper(context);
                db = helper.getWritableDatabase();
                if(DatabaseUtils.updateTask(db, dateSetDate.getDate()
                        , subjectAutoText.getText().toString(), projectAutoText.getText().toString()
                        , startSetTime.getHourOfDay(), startSetTime.getMinute(), startSetTime.getMidDay()
                        , endSetTime.getHourOfDay(), endSetTime.getMinute(), endSetTime.getMidDay()
                        , getTimeDiff(startSetTime.getTime(), endSetTime.getTime()), id) > 0){


                    Toast.makeText(context, "Update task success", Toast.LENGTH_SHORT).show();
                    dateSetDate.clear();
                    subjectAutoText.getText().clear();
                    projectAutoText.getText().clear();
                    startSetTime.clear();
                    endSetTime.clear();

                    //if add task success go to main activity
//                    Intent intent = new Intent(context, MainActivity.class);
//                    startActivity(intent);
                      finish();
                }else{
                    Toast.makeText(context, "Update task failed", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
        db.close();
    }


    private int getTimeDiff(Date start, Date end){
        long diff = end.getTime() - start.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return (int) minutes;
    }


}




