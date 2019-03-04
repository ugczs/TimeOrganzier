package com.saradruid.yuguanzhao.timeorganizier;

import android.util.Log;
import android.widget.Toast;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Calculator {

    public Calculator() {

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
}
