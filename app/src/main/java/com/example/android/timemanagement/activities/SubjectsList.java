package com.example.android.timemanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.adapters.ProjectAdapter;
import com.example.android.timemanagement.data.DBHelper;
import com.example.android.timemanagement.data.DatabaseUtils;
import com.example.android.timemanagement.utilities.ExcelUtils;

import java.io.File;

public class SubjectsList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>{

    private static final String TAG = "SubjectList Acivity";
    private ProjectAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private Context context;
    private String selectedSubjectName;
    private int selectedSubjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);

        context = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }





    @Override
    protected void onStart() {
        super.onStart();

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllSubjectsWithTaskCount(db);

        mAdapter = new ProjectAdapter( cursor ,  new ProjectAdapter.ItemClickListener(){

            @Override
            public void onItemClick(int pos, int subjectId, String subjectName) {
                Log.d(TAG, " item click id: " + subjectId);

                saveExcelFile(subjectId,subjectName);

            }


        });

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (db != null) db.close();
        if (cursor != null) cursor.close();
    }

    public void saveExcelFile(int subjectId,String subjectName){

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllTasksOfSubject(db,subjectId);

        selectedSubjectName = subjectName;

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1, null, this).forceLoad();

    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {

        return new android.support.v4.content.AsyncTaskLoader<Void>(this) {


            @Override
            public Void loadInBackground() {

                ExcelUtils.saveExcelFileForSubjectTasks(cursor , context, "myExcel.xls", selectedSubjectName);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

        //Toast.makeText(context,"The project "+String.valueOf(selectedProjectName)+" data exported!", Toast.LENGTH_SHORT).show();

        String filename="myExcel.xls";

        File filelocation = new File(context.getExternalFilesDir(null), filename);

        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {""};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);

        emailIntent .putExtra(Intent.EXTRA_STREAM, path);

        emailIntent .putExtra(Intent.EXTRA_SUBJECT, selectedSubjectName+" Time Sheet");
        this.startActivity(Intent.createChooser(emailIntent , "Send TimeManagement email..."));

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}


