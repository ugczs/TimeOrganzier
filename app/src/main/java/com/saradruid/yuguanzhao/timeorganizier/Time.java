package com.saradruid.yuguanzhao.timeorganizier;

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
}
