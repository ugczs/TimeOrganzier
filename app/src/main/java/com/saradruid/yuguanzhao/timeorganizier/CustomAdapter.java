package com.saradruid.yuguanzhao.timeorganizier;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.saradruid.yuguanzhao.timeorganzier.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CustomAdapter extends ArrayAdapter<ListItem> {
    private CountDownTimer cTimer;
    private TextView description;
    private TextView time;
    private TextView date;
    private Calculator calculator = new Calculator();

    public CustomAdapter(@NonNull Context context, ArrayList<ListItem> resource) {
        super(context, R.layout.eachlistitem, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.eachlistitem, parent, false);

        ListItem singleListItem = getItem(position);

        description = customView.findViewById(R.id.l_description);
        time = customView.findViewById(R.id.l_time);
        date = customView.findViewById(R.id.l_date);

        Locale current = Locale.getDefault();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, current);
        Date listDate = singleListItem.getDate();
        String reportDate = df.format(listDate);


        description.setText(singleListItem.getDescription());
        date.setText(reportDate);


        Date currentTime = Calendar.getInstance().getTime();
        Date userSetTimeInGMT = calculator.dateToGMT(listDate);

        long l = calculator.calculateDateDiff(calculator.dateToGMT(currentTime), userSetTimeInGMT);

        //countdown timer
        cTimer = new CountDownTimer(l, 1000) {

            public void onTick(long millisUntilFinished) {
                String timeLeft = calculator.calcLeftTime(millisUntilFinished);
                time.setText(timeLeft);
            }
            public void onFinish() {
                time.setText(R.string.time_up);
            }
        };
        cTimer.start();
        return customView;
    }
}
