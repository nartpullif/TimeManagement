package com.example.android.timemanagement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.timemanagement.R;

/**
 * Created by icyfillup on 7/31/2017.
 */

public class SetHourFragment extends DialogFragment
{
    private static final String TAG = "SetHourFragment";
    private static final String R_ID = "rid";
    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";

    private NumberPicker hourNp;
    private NumberPicker minNp;

    private int rid;
    private int hour;
    private int minute;

    public SetHourFragment(){}

    public static SetHourFragment newInstance(int rid, int hour, int minute)
    {
        SetHourFragment fragment = new SetHourFragment();

        Bundle args = new Bundle();
        args.putInt(R_ID, rid);
        args.putInt(HOUR, hour);
        args.putInt(MINUTE, minute);

        fragment.setArguments(args);

        return fragment;
    }

    public interface OnSetTargetTimeCloseListerner
    {
        void closeSetDialog(int rid, String time);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        rid = getArguments().getInt(R_ID);
        hour = getArguments().getInt(HOUR);
        minute = getArguments().getInt(MINUTE);

        View view = inflater.inflate(R.layout.fragment_target_time, container, false);
        hourNp = (NumberPicker) view.findViewById(R.id.hour_np);
        hourNp.setMinValue(0);
        hourNp.setMaxValue(23);
        hourNp.setValue(hour);
        hourNp.setWrapSelectorWheel(false);

        minNp = (NumberPicker) view.findViewById(R.id.min_np);
        minNp.setMinValue(0);
        minNp.setMaxValue(59);
        minNp.setValue(minute);
        minNp.setWrapSelectorWheel(false);

        Button setButton = (Button) view.findViewById(R.id.set_button);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");

                String min = String.valueOf(minNp.getValue());
                if(min.length() == 1)
                {
                    min = "0" + min;
                }
                String time = String.valueOf(hourNp.getValue() + ":" + min);

                OnSetTargetTimeCloseListerner activity = (OnSetTargetTimeCloseListerner) getActivity();
                activity.closeSetDialog(rid, time);

                SetHourFragment.this.dismiss();
            }
        });




        return view;
    }
}
