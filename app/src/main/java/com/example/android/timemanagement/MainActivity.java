package com.example.android.timemanagement;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView mainDate;
    private Button previousDate;
    private Button nextDate;
    private SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainDate = (TextView) findViewById(R.id.mainDate);
        previousDate = (Button) findViewById(R.id.previousDate);
        nextDate = (Button) findViewById(R.id.nextDate);

        final Date currentDate = new Date();
        dateFormat = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        mainDate.setText(dateFormat.format(currentDate));

        previousDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DATE,-1);

                refreshViewForNewDate(c.getTime());
            }
        });

    }

    private void refreshViewForNewDate(Date date) {
        mainDate.setText(dateFormat.format(date));
    }

}
