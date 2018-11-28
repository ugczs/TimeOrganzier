package com.saradruid.yuguanzhao.timeorganizier;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.saradruid.yuguanzhao.timeorganzier.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class ScheduleFragment extends Fragment implements View.OnClickListener {

    private EditText editTime;
    private EditText editDate;
    private EditText editTitle;
    private Button btTime;
    private Button btDate;
    private Button btOk;
    private CountDownTimer cTimer;

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
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("paused", "OnPause of schedule fragment");
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
            Date currentTime = Calendar.getInstance().getTime();

            Locale current = getResources().getConfiguration().locale;
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, current);
            Date date = df.parse(setDate);

            Time time =  parseStringTime(setTime);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(time.getHour()));
            cal.add(Calendar.MINUTE, Integer.parseInt(time.getMinute()));

            Date userSetTime = cal.getTime();

            Date userSetTimeInGMT = dateToGMT(userSetTime);



            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String dateTimeKey = Long.toString(userSetTimeInGMT.getTime());
            prefs.edit().putLong(dateTimeKey, userSetTimeInGMT.getTime()).apply();



            long l = calculateDateDiff(dateToGMT(currentTime), userSetTimeInGMT);

            //countdown timer
            cTimer = new CountDownTimer(l, 1000) {

                public void onTick(long millisUntilFinished) {
                    String timeLeft = calcLeftTime(millisUntilFinished);
                    editTitle.setText(timeLeft);
                }
                public void onFinish() {
                    editTitle.setText(R.string.time_up);
                }
            };

            cTimer.start();

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

    public  Date localToGMT(){
        Date currentDate = Calendar.getInstance().getTime();
        TimeZone tz = TimeZone.getDefault();
        Date ret = new Date(currentDate.getTime() - tz.getRawOffset() );

        // if we are now in DST, back off by the delta.  Note that we are checking the GMT date, this is the KEY.
        if ( tz.inDaylightTime( ret )){
            Date dstDate = new Date( ret.getTime() - tz.getDSTSavings() );

            // check to make sure we have not crossed back into standard time
            // this happens when we are on the cusp of DST (7pm the day before the change for PDT)
            if ( tz.inDaylightTime( dstDate )){
                ret = dstDate;
            }
        }
        Log.i("localToGMT", ret.toString());
        return ret;
    }

    public Date dateToGMT(Date date) {
        TimeZone tz = TimeZone.getDefault();
        Date ret = new Date(date.getTime() - tz.getRawOffset() );

        // if we are now in DST, back off by the delta.  Note that we are checking the GMT date, this is the KEY.
        if ( tz.inDaylightTime( ret )){
            Date dstDate = new Date( ret.getTime() - tz.getDSTSavings() );

            // check to make sure we have not crossed back into standard time
            // this happens when we are on the cusp of DST (7pm the day before the change for PDT)
            if ( tz.inDaylightTime( dstDate )){
                ret = dstDate;
            }
        }
        Log.i("dateToGMT", ret.toString());
        return ret;
    }

    public Date gmtToLocalDate(Date date) {
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        Date local = new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
        Log.i("LocalDate is", local.toString());
        return local;
    }

    public Time parseStringTime(String time) {
        int setHour;
        int setMinute;
        setHour = Integer.parseInt(time.substring(0, 2));
        setMinute = Integer.parseInt(time.substring(3));
        Time t = new Time(setHour, setMinute);
        return t;
    }

    public long calculateDateDiff(Date begin, Date end) {
        long diff = 0;
        if(end.after(begin)) {
            long diffInMillies = Math.abs(end.getTime() - begin.getTime());
            diff = TimeUnit.MILLISECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return diff;
        }
        else {
            Toast.makeText(getActivity(), getResources().getString(R.string.set_time_err),Toast.LENGTH_LONG).show();
            return diff;
        }
    }

    public String calcLeftTime(long timeInMilliSeconds) {
        long seconds = timeInMilliSeconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String time = days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
        return time;
    }

    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelTimer();
        Log.d("Destroy", "FragmentA.onDestroyView() has been called.");
    }

    private void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }*/
}
