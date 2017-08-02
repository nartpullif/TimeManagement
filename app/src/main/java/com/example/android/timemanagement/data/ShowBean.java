package com.example.android.timemanagement.data;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ShowBean {

    private String title;
    private List<Item> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }
}
