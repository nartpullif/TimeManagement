package com.example.android.timemanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.adapters.ProjectAdapter;
import com.example.android.timemanagement.data.DBHelper;
import com.example.android.timemanagement.data.DatabaseUtils;
import com.example.android.timemanagement.utilities.ExcelUtils;


import java.io.File;

public class ProjectsList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>{

    private static final String TAG = "ProjectList Acivity";
    private ProjectAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private Context context;
    private String selectedProjectName;
    private int selectedProjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);

        context = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //mRecyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllProjectWithTaskCount(db);

        mAdapter = new ProjectAdapter( cursor ,  new ProjectAdapter.ItemClickListener(){

            @Override
            public void onItemClick(int pos, int projectId, String projectName) {
                Log.d(TAG, " item click id: " + projectId);

                saveExcelFile(projectId,projectName);

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

    public void saveExcelFile(int projectId,String projectName){

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllTasksOfProject(db,projectId);

        selectedProjectName = projectName;

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1, null, this).forceLoad();

    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {

        return new android.support.v4.content.AsyncTaskLoader<Void>(this) {


            @Override
            public Void loadInBackground() {

                ExcelUtils.saveExcelFileForProjectTasks(cursor , context, "myExcel.xls", selectedProjectName );
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        //Toast.makeText(context,"The project "+String.valueOf(selectedProjectName)+" data exported!", Toast.LENGTH_SHORT).show();
        /*
        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        Uri photoURI = FileProvider.getUriForFile(ProjectsList.this,
                 "com.example.android.timemanagement.provider",
                new File(context.getExternalFilesDir(null),"myExcel.xls"));

        sendIntent.putExtra(Intent.EXTRA_STREAM, photoURI);


        startActivity(sendIntent);
        */
        String filename="myExcel.xls";
        //File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"Android/data/com.example.android.newsapp/files", filename);
        File filelocation = new File(context.getExternalFilesDir(null), filename);
        //context.getExternalFilesDir(null)
        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {""};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, selectedProjectName+" Time Sheet");
        this.startActivity(Intent.createChooser(emailIntent , "Send TimeManagement email..."));

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
