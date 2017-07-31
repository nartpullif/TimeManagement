package com.example.android.timemanagement.Formatting;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by icyfillup on 7/29/2017.
 */

public class TimeAxisFormatter implements IAxisValueFormatter {

    private float reference_timestamp;

    public TimeAxisFormatter(float reference_timestamp)
    {
        this.reference_timestamp = reference_timestamp;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        long timeUTC = Long.valueOf((long) (value * reference_timestamp));
        Date time = new Date(timeUTC);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String formattedDate = sdf.format(time);

        return formattedDate;
    }
}
