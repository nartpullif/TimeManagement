package com.example.android.timemanagement.data;

/**
 * Created by Vahedi on 7/19/17.
 */

public class Task {
    private String date;
    private Subject subject;
    private Project project;
    private int startHour;
    private int startMinute;
    private MidDay startMidDay;
    private int endHour;
    private int endMinute;
    private MidDay endMidDay;
    private int taskTotalMinutes;

    public Task(String date,Subject subject,Project project, int startHour,int startMinute,MidDay startMidDay,
                int endHour ,int endMinute,MidDay endMidDay ){

        this.date = date;
        this.subject = subject;
        this.project = project;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.startMidDay = startMidDay;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.endMidDay = endMidDay;

    }


    public int getTaskTotalMinutes() { return taskTotalMinutes; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public MidDay getStartMidDay() {
        return startMidDay;
    }

    public void setStartMidDay(MidDay startMidDay) {
        this.startMidDay = startMidDay;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public MidDay getEndMidDay() {
        return endMidDay;
    }

    public void setEndMidDay(MidDay endMidDay) {
        this.endMidDay = endMidDay;
    }
}
