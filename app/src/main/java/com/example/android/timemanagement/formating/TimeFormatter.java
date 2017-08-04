package com.example.android.timemanagement.formating;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;

/**
 * Created by icyfillup on 8/4/2017.
 */

public class TimeFormatter implements IValueFormatter, IAxisValueFormatter {

    private static final int MIN_TO_HR = 60;
    public TimeFormatter() {}

    @Override
    public String getFormattedValue(float min, AxisBase axis) {
        return makeTimeFormat(min);
    }

    @Override
    public String getFormattedValue(float min, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return makeTimeFormat(min);
    }

    public String makeTimeFormat(float min)
    {
        int minute = (int)min;
        int hour = 0;

        while(minute >= MIN_TO_HR)
        {
            minute -= MIN_TO_HR;
            hour++;
        }

        String formattedMinute = String.valueOf(minute);
        if(formattedMinute.length() == 1)
        {
            formattedMinute = "0" + formattedMinute;
        }
        return hour + ":" + formattedMinute;
    }
}
