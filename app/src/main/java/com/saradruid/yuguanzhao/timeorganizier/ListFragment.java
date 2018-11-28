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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
        /*Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1991);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        Date date1 = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 1992);
        cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal2.set(Calendar.DAY_OF_MONTH, 16);
        Date date2 = cal2.getTime();*/

        ArrayList list = new ArrayList<ListItem>();
        /*ListItem i1 = new ListItem("a1", "b1", date1);
        ListItem i2 = new ListItem("a2", "b2", date2);
        list.add(i1);
        list.add(i2);*/

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        /*cleanSharedPreferences(prefs);*/
        //get all items from sharedPref
        Map<String,?> keys = prefs.getAll();
        //sort items in list
        keys = new TreeMap<>(keys);

        for(Map.Entry<String,?> entry : keys.entrySet()){
            long l =   Long.parseLong(entry.getValue().toString());

            DateTime dateTimeLocal = new DateTime( l ).withZone(DateTimeZone.getDefault());

            Log.i("testo", dateTimeLocal.toString());

            ListItem i = new ListItem("a1", "b1", dateTimeLocal.toDate());
            list.add(i);
        }
        return list;
    }

    public void cleanSharedPreferences(SharedPreferences prefs) {
        prefs.edit().clear().commit();
    }
}
