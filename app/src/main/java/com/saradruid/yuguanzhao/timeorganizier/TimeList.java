package com.saradruid.yuguanzhao.timeorganizier;

import java.util.ArrayList;

public class TimeList {
    private ArrayList<ListItem> listItems;

    public TimeList() {
        this.listItems = new ArrayList<ListItem>();
    }

    public void add(ListItem item) {
        listItems.add(item);
    }

    public void remove(ListItem item) {
        listItems.remove(item);
    }

    public void sort() {
    }

    public ArrayList<ListItem> getListItems() {
        return listItems;
    }
}
