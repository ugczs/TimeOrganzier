package com.saradruid.yuguanzhao.timeorganizier;

import org.joda.time.DateTime;

import java.util.Date;

public class ListItem {
    private String description;
    private String time;
    private Date date;
    private DateTime dateTime;

    public ListItem(String description, String time, Date date) {
        super();
        this.description = description;
        this.time = time;
        this.date = date;
    }

    public ListItem(String description, String time, DateTime dateTime) {
        super();
        this.description = description;
        this.time = time;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
