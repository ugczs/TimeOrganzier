package com.saradruid.yuguanzhao.timeorganizier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.saradruid.yuguanzhao.timeorganzier.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter<ListItem> {

    public CustomAdapter(@NonNull Context context, ArrayList<ListItem> resource) {
        super(context, R.layout.eachlistitem, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.eachlistitem, parent, false);

        ListItem singleListItem = getItem(position);

        TextView description = customView.findViewById(R.id.l_description);
        TextView time = customView.findViewById(R.id.l_time);
        TextView date = customView.findViewById(R.id.l_date);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date listDate = singleListItem.getDate();
        String reportDate = df.format(listDate);


        description.setText(singleListItem.getDescription());
        time.setText(singleListItem.getTime());
        date.setText(reportDate);

        return customView;
    }
}
