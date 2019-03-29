package com.saradruid.yuguanzhao.timeorganizier;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DatePicker extends Fragment implements DatePickerDialog.OnDateSetListener {

    private int pickedDay;
    private int pickedMonth;
    private int pickedYear;
    private Date pickedDate;
    private OnDataPass dataPasser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_date_picker, container, false);
        Log.i("TimePicker ", "is created!");

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.setCancelable(true);
        dialog.show();

        return rootView;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.pickedYear = year;
        this.pickedMonth = monthOfYear;
        this.pickedDay = dayOfMonth;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.pickedDate = cal.getTime();

        String date = year+"."+monthOfYear+"." + dayOfMonth;
        Log.i("date: ", "You picked the following Date: " + date + "!");
        passData(pickedDate);
    }

    public int getDay() {
        return pickedDay;
    }

    public int getMonth() {
        return pickedMonth;
    }

    public int getYear() {
        return pickedYear;
    }

    public Date getDate() {
        return pickedDate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    public void passData(Object data) {
        try{
            dataPasser.onDataPass(data);
        }
        catch (Exception e)
        {
            Log.e("passData:",e.getMessage());
        }
    }

    @Override
    public void onResume() {
        Log.e("resumed", "onResume of DatePicker");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("paused", "OnPause of DatePicker");
        super.onPause();
    }
}