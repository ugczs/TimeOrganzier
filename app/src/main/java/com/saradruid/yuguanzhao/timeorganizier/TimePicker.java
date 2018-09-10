package com.saradruid.yuguanzhao.timeorganizier;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.util.Calendar;

public class TimePicker extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time_picker, container, false);
        Log.i("TimePicker ", "is created!");

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog d = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                String time = "You picked the following time: "+hourOfDay+"h"+minute+"m";
                Log.i("time: ", time + "!");
            }
        }, hour, minute, true);
        d.setTitle("Select Time");
        d.setCancelable(false);
        d.show();
        return rootView;
    }

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_time_picker);
        Log.i("TimePicker ", "is created!");

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog d = new TimePickerDialog(TimePicker.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                String time = "You picked the following time: "+hourOfDay+"h"+minute+"m";
                Log.i("time: ", time + "!");
            }
        }, hour, minute, true);
        d.setTitle("Select Time");
        d.setCancelable(false);
        d.show();
    }*/
}