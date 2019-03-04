package com.saradruid.yuguanzhao.timeorganizier;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.saradruid.yuguanzhao.timeorganzier.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<ListItem> listItems = getListItems();
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);

        ListView lv = rootView.findViewById(R.id.countDownList);
        lv.setAdapter(new CustomAdapter(getActivity(), listItems));

        return rootView;
    }

    private ArrayList<ListItem> getListItems(){

        ArrayList list = new ArrayList<ListItem>();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
       /* cleanSharedPreferences(prefs);*/
        //get all items from sharedPref
        Map<String,?> keys = prefs.getAll();
        //sort items in list
        keys = new TreeMap<>(keys);

        for(Map.Entry<String,?> entry : keys.entrySet()){
            String[]  parts = entry.getValue().toString().split("#");
            String name = parts[0];
            String time = parts[1];
            long l =   Long.parseLong(time);

            DateTime dateTimeLocal = new DateTime( l ).withZone(DateTimeZone.getDefault());

            Log.i("local time is: ", dateTimeLocal.toString());

            ListItem i = new ListItem(name, "b1", dateTimeLocal.toDate());
            list.add(i);
        }
        return list;
    }

    public void cleanSharedPreferences(SharedPreferences prefs) {
        prefs.edit().clear().commit();
    }
}
