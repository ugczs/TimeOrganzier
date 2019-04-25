package com.saradruid.yuguanzhao.timeorganizier;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.saradruid.yuguanzhao.timeorganzier.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Map;
import java.util.TreeMap;

public class ListFragment extends Fragment {
    private TimeList listItems;
    private CustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listItems = getListItems();
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);

        RecyclerView rv = rootView.findViewById(R.id.countDownList);
        adapter = new CustomAdapter(getActivity(), listItems);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));




        return rootView;
    }

    private TimeList getListItems(){

        TimeList list = new TimeList();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //get all items from sharedPref
        Map<String,?> keys = prefs.getAll();
        //sort items in list
        keys = new TreeMap<>(keys);

        for(Map.Entry<String,?> entry : keys.entrySet()){
            String[]  parts = entry.getValue().toString().split("#");
            String[] id = entry.getKey().split("#");
            String itemId = id[1];
            Integer itemNr = Integer.parseInt(itemId);

            String description = parts[0];
            String time = parts[1];
            Log.i("item: ", itemId + ": " + description );
            long l =   Long.parseLong(time);

            DateTime setTime = new DateTime( l ).withZone(DateTimeZone.getDefault());

            Log.i("set time is: ", setTime.toString());

            ListItem i = new ListItem(itemNr, description, setTime.toDate());
            list.add(i);
        }
        return list;
    }

    public void cleanSharedPreferences(SharedPreferences prefs) {
        prefs.edit().clear().commit();
    }

}
