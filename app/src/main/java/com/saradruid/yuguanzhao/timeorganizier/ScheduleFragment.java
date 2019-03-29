package com.saradruid.yuguanzhao.timeorganizier;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


public class ScheduleFragment extends Fragment implements View.OnClickListener {

    private EditText editTime;
    private EditText editDate;
    private EditText editTitle;
    private Button btTime;
    private Button btDate;
    private Button btOk;
    private Calculator calculator = new Calculator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.schedule_fragment, container, false);

        editTime = rootView.findViewById(R.id.editTime);
        editDate = rootView.findViewById(R.id.editDate);
        editTitle = rootView.findViewById(R.id.editTitle);

        btTime = rootView.findViewById(R.id.buttonTime);
        btTime.setOnClickListener(this);
        btDate = rootView.findViewById(R.id.buttonDate);
        btDate.setOnClickListener(this);
        btOk = rootView.findViewById(R.id.buttonOk);
        btOk.setOnClickListener(this);

        setCurrentDate();
        setCurrentTime();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonTime:
                openTimeSelector();
                break;
            case R.id.buttonDate:
                openDateSelector();
                break;
            case R.id.buttonOk:
                setListItem();
                break;
            default:
                break;
        }
    }

    private void openTimeSelector() {
        Fragment fragment = new TimePicker();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, fragment, "timePicker")
                .commit();
    }

    private void openDateSelector() {
        Fragment fragment = new DatePicker();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, fragment, "datePicker")
                .commit();
    }

    @Override
    public void onResume() {
        Log.e("resumed", "onResume of schedule fragment");
        Fragment timePicker = getFragmentManager().findFragmentByTag("timePicker");
        Fragment datePicker = getFragmentManager().findFragmentByTag("datePicker");
        if(timePicker != null) {
            getFragmentManager().beginTransaction().remove(timePicker).commit();
        }
        if(datePicker != null) {
            getFragmentManager().beginTransaction().remove(datePicker).commit();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("paused", "OnPause of schedule fragment");
        Fragment timePicker = getFragmentManager().findFragmentByTag("timePicker");
        Fragment datePicker = getFragmentManager().findFragmentByTag("datePicker");
        if(timePicker != null) {
            getFragmentManager().beginTransaction().remove(timePicker).commit();
        }
        if(datePicker != null) {
            getFragmentManager().beginTransaction().remove(datePicker).commit();
        }
        super.onPause();
    }

    public void upDateTime(Time time) {
        editTime.setText(time.toString());
    }

    public void upDateDate(Date date) {
        Locale current = getResources().getConfiguration().locale;
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, current);
        String d = df.format(date);
        editDate.setText(d);
    }

    public void setCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        upDateDate(currentDate);
    }

    public void setCurrentTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        Time currentTime = new Time(hour, min);
        upDateTime(currentTime);
    }

    public void setListItem() {
        try {
            String setTime = editTime.getText().toString();
            String setDate = editDate.getText().toString();


            Locale current = getResources().getConfiguration().locale;
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, current);
            Date date = df.parse(setDate);

            Time time =  calculator.parseStringTime(setTime);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(time.getHour()));
            cal.add(Calendar.MINUTE, Integer.parseInt(time.getMinute()));

            Date userSetTime = cal.getTime();


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String dateTimeKey = Long.toString(userSetTime.getTime());


            String item = "";
            if(editTitle.getText() == null) {
                Toast.makeText(getActivity(),R.string.setTitle, Toast.LENGTH_SHORT).show();
            }
            else {
                String title = editTitle.getText().toString();
                item = title + "#" + dateTimeKey;
            }

            prefs.edit().putString(dateTimeKey, item).apply();

            Toast.makeText(getActivity(),R.string.activitySet,
                    Toast.LENGTH_SHORT).show();
        }
        catch(Exception e) {
            Log.e("ScheduleFragment", e.getMessage());
        }
    }

    public void listAllSavedTimes(SharedPreferences prefs) {
        Map<String,?> keys = prefs.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.i("map values",entry.getKey() + ": " +
                    entry.getValue().toString());
        }
    }

}
