package com.example.android.timemanagement.utilities;


import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.example.android.timemanagement.data.Contract;
import com.example.android.timemanagement.data.DBHelper;
import com.example.android.timemanagement.data.DatabaseUtils;

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

/**
 * Created by Vahedi on 8/6/17.
 */

public class ExcelUtils {

    public static final String TAG = "ExcelUtils";

    public static boolean saveExcelFile(Cursor cursor, Context context, String fileName, String projectName) {


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


        String completeTime="";
        int durationHours = 0;
        int durationMinutes = 0;
        int totalTime = 0;
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            row = sheet1.createRow(i+1);

            c = row.createCell(0);
            c.setCellValue(String.valueOf(i+1));

            c = row.createCell(1);
            c.setCellValue(cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE)));

            c = row.createCell(2);
            completeTime = "";
            completeTime += cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR)) + " : ";
            completeTime += cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE)) + "  ";
            completeTime += cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));
            c.setCellValue(completeTime);

            c = row.createCell(3);
            completeTime = "";
            completeTime += cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR)) + " : ";
            completeTime += cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE)) + "  ";
            completeTime += cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));
            c.setCellValue(completeTime);

            c = row.createCell(4);
            durationHours = 0;
            durationMinutes = 0;
            totalTime += cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));
            durationHours = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES))/60;
            durationMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES))%60;
            completeTime = "";
            completeTime += String.valueOf(durationHours) + " : " + String.valueOf(durationMinutes);
            c.setCellValue(completeTime);

        }

        row = sheet1.createRow(cursor.getCount()+1);

        c = row.createCell(0);
        c.setCellValue("");

        c = row.createCell(1);
        c.setCellValue("");

        c = row.createCell(2);
        c.setCellValue("");

        c = row.createCell(3);
        c.setCellValue("Total Duration");
        c.setCellStyle(cs);

        c = row.createCell(4);
        durationHours = 0;
        durationMinutes = 0;
        durationHours = totalTime/60;
        durationMinutes = totalTime%60;
        completeTime = "";
        completeTime += String.valueOf(durationHours) + " : " + String.valueOf(durationMinutes);
        c.setCellValue(completeTime);
        c.setCellStyle(cs);



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

}
