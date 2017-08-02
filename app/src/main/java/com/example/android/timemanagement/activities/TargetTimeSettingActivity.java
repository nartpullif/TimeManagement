package com.example.android.timemanagement.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.fragment.SetHourFragment;
import com.example.android.timemanagement.models.ActivitySwitcherToolbar;
import com.example.android.timemanagement.utilities.PreferenceUtils;

import java.util.HashMap;

public class TargetTimeSettingActivity extends AppCompatActivity implements SetHourFragment.OnSetTargetTimeCloseListerner{

    private HashMap<Integer, TextView> dayTargetTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_time_setting);
        dayTargetTime = new HashMap<>();

        initTextViewTargetTime();
    }
    public void initTextViewTargetTime()
    {
        PreferenceUtils.initSharePreference(this);

        TextView mondayTargetTime = (TextView) findViewById(R.id.date_monday_time);
        mondayTargetTime.setText(PreferenceUtils.getDayTargetTime(this, PreferenceUtils.MONDAY_TARGET_TIME));
        dayTargetTime.put(R.id.date_monday_time, mondayTargetTime);

        TextView tuesdayTargetTime = (TextView) findViewById(R.id.date_tuesday_time);
        tuesdayTargetTime.setText(PreferenceUtils.getDayTargetTime(this, PreferenceUtils.TUESDAY_TARGET_TIME));
        dayTargetTime.put(R.id.date_tuesday_time, tuesdayTargetTime);

        TextView wednesdayTargetTime = (TextView) findViewById(R.id.date_wednesday_time);
        wednesdayTargetTime.setText(PreferenceUtils.getDayTargetTime(this, PreferenceUtils.WEDNESDAY_TARGET_TIME));
        dayTargetTime.put(R.id.date_wednesday_time, wednesdayTargetTime);

        TextView thursdayTargetTime = (TextView) findViewById(R.id.date_thursday_time);
        thursdayTargetTime.setText(PreferenceUtils.getDayTargetTime(this, PreferenceUtils.THURSDAY_TARGET_TIME));
        dayTargetTime.put(R.id.date_thursday_time, thursdayTargetTime);

        TextView fridayTargetTime = (TextView) findViewById(R.id.date_friday_time);
        fridayTargetTime.setText(PreferenceUtils.getDayTargetTime(this, PreferenceUtils.FRIDAY_TARGET_TIME));
        dayTargetTime.put(R.id.date_friday_time, fridayTargetTime);

        TextView saturdayTargetTime = (TextView) findViewById(R.id.date_saturday_time);
        saturdayTargetTime.setText(PreferenceUtils.getDayTargetTime(this, PreferenceUtils.SATURDAY_TARGET_TIME));
        dayTargetTime.put(R.id.date_saturday_time, saturdayTargetTime);

        TextView sundayTargetTime = (TextView) findViewById(R.id.date_sunday_time);
        sundayTargetTime.setText(PreferenceUtils.getDayTargetTime(this, PreferenceUtils.SUNDAY_TARGET_TIME));
        dayTargetTime.put(R.id.date_sunday_time, sundayTargetTime);

    }

    @Override
    public void closeSetDialog(int rid, String time) {
        dayTargetTime.get(rid).setText(time);
        if(rid == R.id.date_monday_time)
        {
            PreferenceUtils.updateDayTargetTime(this, PreferenceUtils.MONDAY_TARGET_TIME, time);
        }
        else if(rid == R.id.date_tuesday_time)
        {
            PreferenceUtils.updateDayTargetTime(this, PreferenceUtils.TUESDAY_TARGET_TIME, time);
        }
        else if(rid == R.id.date_wednesday_time)
        {
            PreferenceUtils.updateDayTargetTime(this, PreferenceUtils.WEDNESDAY_TARGET_TIME, time);
        }
        else if(rid == R.id.date_thursday_time)
        {
            PreferenceUtils.updateDayTargetTime(this, PreferenceUtils.THURSDAY_TARGET_TIME, time);
        }
        else if(rid == R.id.date_friday_time)
        {
            PreferenceUtils.updateDayTargetTime(this, PreferenceUtils.FRIDAY_TARGET_TIME, time);
        }
        else if(rid == R.id.date_saturday_time)
        {
            PreferenceUtils.updateDayTargetTime(this, PreferenceUtils.SATURDAY_TARGET_TIME, time);
        }
        else if(rid == R.id.date_sunday_time)
        {
            PreferenceUtils.updateDayTargetTime(this, PreferenceUtils.SUNDAY_TARGET_TIME, time);
        }
    }

    public void onFragmentPopUp(int rid)
    {
        TextView TargetTime = dayTargetTime.get(rid);
        String[] time = TargetTime.getText().toString().split(":");

        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        FragmentManager fm = getSupportFragmentManager();
        SetHourFragment fragment = SetHourFragment.newInstance(rid, hour, minute);
        fragment.show(fm, "sethourfragment");
    }
    public void onClickMonday(View view)
    {
        int rid = R.id.date_monday_time;
        onFragmentPopUp(rid);
//        Toast.makeText(this, "onClickMonday", Toast.LENGTH_LONG).show();
    }

    public void onClickTuesday(View view)
    {
        int rid = R.id.date_tuesday_time;
        onFragmentPopUp(rid);
//        Toast.makeText(this, "onClickTuesday", Toast.LENGTH_LONG).show();
    }
    public void onClickWednesday(View view)
    {
        int rid = R.id.date_wednesday_time;
        onFragmentPopUp(rid);
//        Toast.makeText(this, "onClickWednesday", Toast.LENGTH_LONG).show();
    }

    public void onClickThursday(View view)
    {
        int rid = R.id.date_thursday_time;
        onFragmentPopUp(rid);
//        Toast.makeText(this, "onClickThursday", Toast.LENGTH_LONG).show();
    }
    public void onClickFriday(View view)
    {
        int rid = R.id.date_friday_time;
        onFragmentPopUp(rid);
//        Toast.makeText(this, "onClickFriday", Toast.LENGTH_LONG).show();
    }

    public void onClickSaturday(View view)
    {
        int rid = R.id.date_saturday_time;
        onFragmentPopUp(rid);
//        Toast.makeText(this, "onClickSaturday", Toast.LENGTH_LONG).show();
    }
    public void onClickSunday(View view)
    {
        int rid = R.id.date_sunday_time;
        onFragmentPopUp(rid);
//        Toast.makeText(this, "onClickSunday", Toast.LENGTH_LONG).show();
    }
}