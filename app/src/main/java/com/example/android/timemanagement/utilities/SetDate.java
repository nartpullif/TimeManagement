package com.example.android.timemanagement.utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Siriporn on 7/24/2017.
 */

public class SetDate implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private EditText editText;
    private Calendar myCalendar;
    private Context context;

    public SetDate(EditText editText, Context context){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.context = context;
        this.myCalendar = Calendar.getInstance();

    }

    @Override
    public void onClick(View v) {
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(context, this, year, month, dayOfMonth).show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    public int getYear(){
        return myCalendar.get(Calendar.YEAR);
    }

    public int getMonth(){
        return myCalendar.get(Calendar.MONTH);
    }

    public int getDayOfMonth(){
        return myCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getDate(){
        return editText.getText().toString();
    }

    public void setDate(String textDate){
        String[] texts = textDate.split("/");
        myCalendar.set(Calendar.MONTH, Integer.parseInt(texts[0]));
        myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(texts[1]));
        myCalendar.set(Calendar.YEAR, Integer.parseInt(texts[2]));
        editText.setText(textDate);
    }

    public Editable getText(){
        return editText.getText();
    }

    public void clear(){
        editText.getText().clear();
        myCalendar = Calendar.getInstance();
    }
}
