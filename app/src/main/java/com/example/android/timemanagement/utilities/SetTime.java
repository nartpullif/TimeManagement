package com.example.android.timemanagement.utilities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Siriporn on 7/24/2017.
 * creditted to https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
 */

public class SetTime implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context context;

    public SetTime(EditText editText, Context context){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.context = context;
        this.myCalendar = Calendar.getInstance();

    }

    @Override
    public void onClick(View v) {
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        new TimePickerDialog(context, this, hour, minute, false).show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        Format formatter = new SimpleDateFormat("h:mm a");
        editText.setText(formatter.format(myCalendar.getTime()));
    }

    public int getHourOfDay(){
        return myCalendar.get(Calendar.HOUR_OF_DAY)%12;
    }

    public int getMinute(){
        return myCalendar.get(Calendar.MINUTE);
    }

    public String getMidDay(){
        return (myCalendar.get(Calendar.HOUR_OF_DAY) < 12) ? "AM" : "PM";
    }

    public java.util.Date getTime() {return myCalendar.getTime();}

    public void setTime(String textTime) {
        DateFormat sdf = new SimpleDateFormat("h:m a");
        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myCalendar.set(Calendar.HOUR_OF_DAY, date.getHours());
        myCalendar.set(Calendar.MINUTE, date.getMinutes());
        editText.setText(textTime);
    }

    public Editable getText(){
        return editText.getText();
    }

    public void clear(){
        editText.getText().clear();
        this.myCalendar = Calendar.getInstance();
    }

}
