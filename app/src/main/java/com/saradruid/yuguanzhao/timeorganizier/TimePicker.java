package com.saradruid.yuguanzhao.timeorganizier;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.util.Calendar;

public class TimePicker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
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
    }
}
