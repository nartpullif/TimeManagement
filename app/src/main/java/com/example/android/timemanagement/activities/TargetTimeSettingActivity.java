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

public class TargetTimeSettingActivity extends AppCompatActivity implements SetHourFragment.OnSetTargetTimeCloseListerner{
    private ActivitySwitcherToolbar activitySwitcherToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_time_setting);

        // bottom of the layout
        activitySwitcherToolbar = new ActivitySwitcherToolbar(this.getClass(), this);
    }

    @Override
    public void closeSetDialog(int rid, String time) {
        TextView targetTime = (TextView) findViewById(rid);
        targetTime.setText(time);
    }

    public void onFragmentPopUp(int rid)
    {
        TextView TargetTime = (TextView) findViewById(rid);
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

