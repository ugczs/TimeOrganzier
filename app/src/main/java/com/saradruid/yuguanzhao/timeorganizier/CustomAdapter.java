package com.saradruid.yuguanzhao.timeorganizier;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Calculator calculator = new Calculator();
    private Context context;
    private TimeList list;

    public CustomAdapter(@NonNull Context context, TimeList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.eachlistitem, parent, false)
        );
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }



    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.ViewHolder holder, int position) {
        ListItem item = this.list.get(position);

        if (holder.timer != null) {
            holder.timer.cancel();
        }

        Date userSetTime = item.getDate();
        Date currentTime = Calendar.getInstance().getTime();

        Date a = calculator.dateToGMT(currentTime);
        Log.i("cuTime", "current time: " + currentTime.toString() + " user set time: " + userSetTime.toString());

        long l = calculator.calculateDateDiff(currentTime, userSetTime);

        holder.timer = new CountDownTimer(l, 1000) {

            public void onTick(long millisUntilFinished) {
                String timeLeft = calculator.calcLeftTime(millisUntilFinished);
                holder.time.setText(timeLeft);
            }
            public void onFinish() {
                holder.time.setText(R.string.time_up);
            }
        }.start();

        holder.description.setText(item.getDescription());


        Resources res = holder.itemView.getContext().getResources();
        Locale current = res.getConfiguration().locale;
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, current);
        String date = df.format(item.getDate());
        holder.date.setText(date);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView time;
        private TextView date;
        private CountDownTimer timer;

        private ViewHolder(@NonNull View view) {
            super(view);
            this.description = view
                    .findViewById(R.id.l_description);
            this.time = view
                    .findViewById(R.id.l_time);
            this.date = view
                    .findViewById(R.id.l_date);
        }
    }
}
