package com.example.simpletodotest;

import android.widget.CalendarView;

public class Items {
    private boolean selected;
    private String name;
    private String date;
    private String priority;


    public Items(Boolean selected, String name, String date, String priority) {
        this.selected = selected;
        this.name = name;
        this.date = date;
        this.priority = priority;

    }




    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }



    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }





    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
