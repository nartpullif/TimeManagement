package com.example.android.timemanagement.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.android.timemanagement.R;

public class GraphActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        toolbar = (Toolbar) findViewById(R.id.graph_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void onButtonToday(View view)
    {
        Toast.makeText(this, "Clicked on Today Button", Toast.LENGTH_LONG).show();
    }

    public void onButtonWeek(View view)
    {
        Toast.makeText(this, "Clicked on Week Button", Toast.LENGTH_LONG).show();
    }

    public void onButtonMonth(View view)
    {
        Toast.makeText(this, "Clicked on Month Button", Toast.LENGTH_LONG).show();
    }
}
