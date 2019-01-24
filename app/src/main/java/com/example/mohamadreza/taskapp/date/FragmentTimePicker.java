package com.example.mohamadreza.taskapp.date;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.mohamadreza.taskapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTimePicker extends DialogFragment {

    private static final String ARG_DATE = "date";
    public static final String EXTRA_TIME = "com.example.mohamadreza.taskapp.time";

    private TimePicker mTimePicker;
    private Date mDate;

    public static FragmentTimePicker newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        FragmentTimePicker fragment = new FragmentTimePicker();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTimePicker() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = (Date) getArguments().getSerializable(ARG_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final int Hour=calendar.get(Calendar.HOUR);
        final int Minut=calendar.get(Calendar.MINUTE);

        mTimePicker=view.findViewById(R.id.dialog_time_date_picker);

//        mTimePicker.setCurrentHour(Hour);
//        mTimePicker.setCurrentMinute(Minut);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getHour();
                        int minut = mTimePicker.getMinute();

                        Date date = new GregorianCalendar(year, month, day, hour, minut).getTime();
                        sendResult(date);
                    }
                })
                .create();
    }

    private void sendResult(Date date) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().
                onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

        getTargetFragment().onResume();
    }

}
