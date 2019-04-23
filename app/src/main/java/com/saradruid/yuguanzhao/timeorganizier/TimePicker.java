package com.saradruid.yuguanzhao.timeorganizier;


import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.util.Calendar;

public class TimePicker extends Fragment {

    private int pickedHour;
    private int pickedMinute;
    private OnDataPass dataPasser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time_picker, container, false);
        Log.i("TimePicker ", "is created!");

        onAttach(getActivity());

        Calendar currentCalendar = Calendar.getInstance();
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        TimePickerDialog d = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                String time = "You picked the following time: " + hourOfDay+":" + minute;
                Log.i("time", time + "!");
                pickedHour = hourOfDay;
                pickedMinute = minute;

                Time t = new Time(hourOfDay, minute);
                passData(t);
            }
        }, currentHour, currentMinute, true);

        d.setTitle(R.string.set_time);
        d.setCancelable(true);
        d.show();
        return rootView;
    }

    public int getHour() {
        return pickedHour;
    }

    public int getMinute() {
        return pickedMinute;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    public void passData(Time data) {
        try{
            dataPasser.onDataPass(data);
        }
        catch (Exception e) {
            Log.e("passData:",e.getMessage());
        }
    }

    @Override
    public void onResume() {
        Log.e("resumed", "onResume of TimePicker");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("paused", "OnPause of TimePicker");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e("destroy", "onDestroy of timePicker");
        Fragment timePicker = getFragmentManager().findFragmentByTag("timePicker");
        if(timePicker != null) {
            getFragmentManager().beginTransaction().remove(timePicker).commit();
        }
        super.onDestroy();
    }

    public void callParentMethod(int keyCode, KeyEvent event){
        getActivity().onKeyDown(keyCode, event);
    }
}