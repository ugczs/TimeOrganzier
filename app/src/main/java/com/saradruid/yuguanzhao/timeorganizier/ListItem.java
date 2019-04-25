package com.saradruid.yuguanzhao.timeorganizier;

import org.joda.time.DateTime;

import java.util.Date;

public class ListItem {
    private String description;
    private Date date;
    private DateTime dateTime;
    private int id;

    public ListItem(int id, String description, Date date) {
        super();
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public ListItem(int id, String description, DateTime dateTime) {
        super();
        this.id = id;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }


    public Date getDate() {
        return date;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
