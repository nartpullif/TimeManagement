package com.example.android.timemanagement.data;

/**
 * Created by Administrator on 2017/8/1.
 */
//get all data from row that cursor point
//id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TASK._ID));
//date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE));
//        Log.d(TAG, "date :"+ date);
//        subjectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE));
//        Log.d(TAG, "subject :"+ subjectTitle);
//        projectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE));

//        startTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR));
//        startTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE));
//        startTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));
//        endTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR));
//        endTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE));
//        endTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));
//        durationTimeMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));
//        startTime = String.format("%s:%02d %s", startTimeHour, startTimeMinute, startTimeMidDay); //format is 10:47 AM
//        endTime = String.format("%s:%02d %s", endTimeHour, endTimeMinute, endTimeMidDay);
//        periodTime = String.format("%s - %s", startTime, endTime);// format is 10:47 AM - 11:47 AM
//        durationTime = String.format("%d:%02d", durationTimeMinutes/60, durationTimeMinutes%60);// format is 1:00


public class DataBean {

    public Long id;
    public String date;
    public String subjectTitle;
    public String projectTitle;

    public int startTimeHour;
    public int startTimeMinute;
    public String startTimeMidDay;

    public int endTimeHour;
    public int endTimeMinute;
    public String endTimeMidDay;

    public int durationTimeMinutes;

    public String getEndTimeMidDay() {
        return endTimeMidDay;
    }

    public void setEndTimeMidDay(String endTimeMidDay) {
        this.endTimeMidDay = endTimeMidDay;
    }

    public int getDurationTimeMinutes() {
        return durationTimeMinutes;
    }

    public void setDurationTimeMinutes(int durationTimeMinutes) {
        this.durationTimeMinutes = durationTimeMinutes;
    }

    public int getEndTimeMinute() {
        return endTimeMinute;
    }

    public void setEndTimeMinute(int endTimeMinute) {
        this.endTimeMinute = endTimeMinute;
    }

    public int getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(int endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public String getStartTimeMidDay() {
        return startTimeMidDay;
    }

    public void setStartTimeMidDay(String startTimeMidDay) {
        this.startTimeMidDay = startTimeMidDay;
    }

    public int getStartTimeMinute() {
        return startTimeMinute;
    }

    public void setStartTimeMinute(int startTimeMinute) {
        this.startTimeMinute = startTimeMinute;
    }

    public int getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(int startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
