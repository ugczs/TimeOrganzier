package com.saradruid.yuguanzhao.timeorganizier;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    /*private CountDownTimer cTimer;
    private Calculator calculator = new Calculator();*/
    private Context context;
    private List<ListItem> list;

    public CustomAdapter(@NonNull Context context, ArrayList<ListItem> list) {
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

    @NonNull
   /* @Override
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
    }*/

    @Override
    public int getItemCount() {
        return this.list.size();
    }



    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        ListItem list = this.list.get(position);

        holder.description.setText(list.getDescription());
        holder.time.setText(list.getTime());
        holder.date.setText(list.getDate().toString());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView time;
        private TextView date;

        public ViewHolder(@NonNull View view) {
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
