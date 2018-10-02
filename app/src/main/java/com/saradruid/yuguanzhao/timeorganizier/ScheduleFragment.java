package com.saradruid.yuguanzhao.timeorganizier;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.saradruid.yuguanzhao.timeorganzier.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleFragment extends Fragment implements View.OnClickListener {

    private FragmentCallback mCallback;
    private EditText editTime;
    private EditText editDate;
    private Button btTime;
    private Button btDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.schedule_fragment, container, false);

        editTime = rootView.findViewById(R.id.editTime);
        editDate = rootView.findViewById(R.id.editDate);

        btTime = rootView.findViewById(R.id.buttonTime);
        btTime.setOnClickListener(this);
        btDate = rootView.findViewById(R.id.buttonDate);
        btDate.setOnClickListener(this);

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
        editTime.setText(time.getHour() + ":" + time.getMinute());
    }

    public void upDateDate(Date date) {
        Locale current = getResources().getConfiguration().locale;
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, current);
        String d = df.format(date);
        editDate.setText(d);
    }

    public void setCallback(FragmentCallback callback) {
        mCallback = callback;
    }
}
