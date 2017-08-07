package com.example.android.timemanagement.models;

/**
 * Created by Vahedi on 7/19/17.
 */

public class Subject {
    private String title;
    private int numberOfTasks;

    public Subject(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }
}
