package com.example.android.timemanagement.models;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.activities.GraphActivity;
import com.example.android.timemanagement.activities.MainActivity;

/**
 * Created by icyfillup on 7/27/2017.
 */

public class ActivitySwitcherToolbar
{
    private ImageView listActivityImageView;
    private ImageView reportActivityImageView;
    private ImageView graphActivityImageView;

    public ActivitySwitcherToolbar(final Class activityClass, final AppCompatActivity activity)
    {
        listActivityImageView = (ImageView) activity.findViewById(R.id.list_item);
        reportActivityImageView = (ImageView) activity.findViewById(R.id.total_report);
        graphActivityImageView = (ImageView) activity.findViewById(R.id.task_graph);

        listActivityImageView.setImageResource(R.drawable.ic_list_black);
        reportActivityImageView.setImageResource(R.drawable.ic_report_black);
        graphActivityImageView.setImageResource(R.drawable.ic_graph_black);

        if(activityClass.equals(MainActivity.class))
        {
            graphActivityImageView.setBackgroundResource(R.color.colorPrimary);
        }
        else if(activityClass.equals(GraphActivity.class))
        {
            graphActivityImageView.setBackgroundResource(R.color.colorPrimary);
        }
//        else if(activityClass.equals(ReportActivity.class))
//        {
//
//        }

        listActivityImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(!activityClass.equals(MainActivity.class))
                {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }
                else
                {
                    Toast.makeText(activity, "On current activity", Toast.LENGTH_LONG).show();
                }
            }
        });
        reportActivityImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                /* //the report class has not been made a this point (7/27)
                if(!activityClass.equals(ReportActivity.class))
                {
                    Intent intent = new Intent(activity, ReportActivity.class);
                    activity.startActivity(intent);
                }
                else
                {
                    Toast.makeText(activity, "On current activity", Toast.LENGTH_LONG).show();
                }
                */
            }
        });
        graphActivityImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(!activityClass.equals(GraphActivity.class))
                {
                    Intent intent = new Intent(activity, GraphActivity.class);
                    activity.startActivity(intent);
                }
                else
                {
                    Toast.makeText(activity, "On current activity", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
