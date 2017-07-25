package com.example.android.timemanagement.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.data.Contract;

/**
 * Created by Siriporn on 7/24/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "taskAdapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.adapter_task, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String subject, String project, String startTime, String endTime, String date,  long id);
    }

    public TaskAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView subjectTextView;
        TextView projectTextView;
        TextView periodTimeTextView;
        TextView durationTimeTextView;

        long id;
        String date;
        String subjectTitle, projectTitle;
        int startTimeHour, startTimeMinute, endTimeHour, endTimeMinute;
        String startTimeMidDay, endTimeMidDay;
        String startTime, endTime, periodTime;
        int durationTimeMinutes;
        String durationTime;

        ItemHolder(View view) {
            super(view);
            subjectTextView = (TextView) view.findViewById(R.id.tv_subject);
            projectTextView = (TextView) view.findViewById(R.id.tv_project);
            periodTimeTextView = (TextView) view.findViewById(R.id.tv_periodTime);
            durationTimeTextView = (TextView) view.findViewById(R.id.tv_durationTime);
            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TASK._ID));
            Log.d(TAG, "binding id: " + id);

            //get all data from row that cursor point
            date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE));
            Log.d(TAG, "date :"+ date);
            subjectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE));
            Log.d(TAG, "subject :"+ subjectTitle);
            projectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE));
            startTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR));
            startTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE));
            startTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));
            endTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR));
            endTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE));
            endTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));
            durationTimeMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));
            startTime = String.format("%s:%02d %s", startTimeHour, startTimeMinute, startTimeMidDay); //format is 10:47 AM
            endTime = String.format("%s:%02d %s", endTimeHour, endTimeMinute, endTimeMidDay);
            periodTime = String.format("%s - %s", startTime, endTime);// format is 10:47 AM - 11:47 AM
            durationTime = String.format("%d:%02d", durationTimeMinutes/60, durationTimeMinutes%60);// format is 1:00

            //bind text
            subjectTextView.setText(subjectTitle);
            projectTextView.setText(projectTitle);
            periodTimeTextView.setText(periodTime);
            durationTimeTextView.setText(durationTime);

            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, subjectTitle, projectTitle, startTime, endTime, date, id);
        }
    }
}
