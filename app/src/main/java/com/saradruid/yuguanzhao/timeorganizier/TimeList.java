package com.saradruid.yuguanzhao.timeorganizier;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TimeList {
    private ArrayList<ListItem> listItems;

    public TimeList() {
        this.listItems = new ArrayList<ListItem>();
    }

    public void add(ListItem item) {
        listItems.add(item);
    }

    public void remove(int position) {
        listItems.remove(position);
    }

  /*  public void sort() {
        Collections.sort(listItems, new Comparator<ListItem>() {
            @Override
            public int compare(ListItem o1, ListItem o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
    }*/

    public ArrayList<ListItem> getListItems() {
        return listItems;
    }

    public int size() {
        return listItems.size();
    }

    public ListItem get(int i) {
        return listItems.get(i);
    }


    public ListItem findItemById(int id) {
        for(ListItem li: listItems) {
            if(li.getId().equals(id)) {
                return li;
            }
            else {
                Log.i("findItemById",  String.valueOf(id) + " " + "can not be found!");
            }
        }
        Log.i("findItemById",   "null will be given back");
        return null;
    }

    public ListItem findItemByPosition(int position) {
        if(listItems.isEmpty() || position >= listItems.size()) {
            Log.i("findItemByPosition",   String.valueOf(position) + " " + "can not be found!");
        }
        else {
            return listItems.get(position);
        }
        Log.i("findItemByPosition",   "null will be given back");
        return null;
    }
}
