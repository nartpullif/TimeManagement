package com.example.android.timemanagement.models;

/**
 * Created by icyfillup on 7/29/2017.
 */

public class StartEndTime
{
    private long startingTime;
    private long endingTime;
    private int totalMinutes;

    public StartEndTime(long startingTime, long endingTime, int totalMinutes)
    {
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.totalMinutes = totalMinutes;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public long getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }
}
