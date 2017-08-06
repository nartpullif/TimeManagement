package com.example.android.timemanagement.activities;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.adapters.ProjectAdapter;
import com.example.android.timemanagement.data.DBHelper;
import com.example.android.timemanagement.data.DatabaseUtils;
import com.example.android.timemanagement.utilities.ExcelUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProjectsList extends AppCompatActivity {

    private int number_of_items = 100;
    private ProjectAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final String TAG = "ProjectList Acivity";
    private Context context;

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
                Toast.makeText(context,"Project no."+String.valueOf(projectId), Toast.LENGTH_SHORT).show();
                saveExcelFile(projectId,projectName);

            }


        });

        mRecyclerView.setAdapter(mAdapter);

    }

    public void saveExcelFile(int projectId,String projectName){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllTasksOfProject(db,projectId);
        ExcelUtils.saveExcelFile(cursor , context, "myExcel.xls", projectName );
    }



    /*
    private boolean saveExcelFile(Context context, String fileName, String projectName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("projectName");

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("Row");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("Date");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("Start Time");
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("End Time");
        c.setCellStyle(cs);

        c = row.createCell(4);
        c.setCellValue("Duration");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, (15 * 100));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));
        sheet1.setColumnWidth(3, (15 * 500));
        sheet1.setColumnWidth(4, (15 * 500));

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllProjectWithTaskCount(db);

        for(int i=0;i<cursor.getCount();i++){
            row = sheet1.createRow(0);

        }


        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }


        return success;
    }


    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }
    */


}
