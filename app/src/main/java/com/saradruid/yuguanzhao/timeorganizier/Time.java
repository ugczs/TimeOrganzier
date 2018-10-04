package com.saradruid.yuguanzhao.timeorganizier;

import android.util.Log;

public class Time {
    private String hour;
    private String minute;

    public Time(int hour, int minute) {
        this.hour = Integer.toString(hour);
        this.minute = Integer.toString(minute);
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String toString() {
        String time = null;
        if(this.hour != null && this.minute != null) {
            hour = String.format("%02d", Integer.parseInt(this.hour));
            minute = String.format("%02d", Integer.parseInt(this.minute));
            time = hour + ":" + minute;
            return time;
        }
        else {
            Log.i("Time", "Time is not set!");
            return time;
        }
    }


}
