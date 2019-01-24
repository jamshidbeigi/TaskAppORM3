package com.example.mohamadreza.taskapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mohamadreza.taskapp.models.Task;
import com.example.mohamadreza.taskapp.models.TaskLab;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FragmentDescription extends DialogFragment {


    private static final String ARG_TASK_ID = "taskId";

    private Task mTask;

    public static FragmentDescription newInstance(Long taskId) {
        Bundle args = new Bundle();
        args.putLong(ARG_TASK_ID, taskId);
        FragmentDescription fragment = new FragmentDescription();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentDescription() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Long taskId = null;
        if (getArguments() != null) {
            taskId = getArguments().getLong(ARG_TASK_ID);
        }
        mTask = TaskLab.getInstance().getTask(taskId);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_description, container, false);

        TextView title = view.findViewById(R.id.title_text_view);
        TextView description = view.findViewById(R.id.description_text_view);
        TextView date = view.findViewById(R.id.date_text_view);
        TextView time = view.findViewById(R.id.time_text_view);

        title.setText(mTask.getTitle());
        description.setText(mTask.getMDescription());
        date.setText(mTask.getMDate().toString());

        String pattern = "HH:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("da", "DK"));
        String timeString = simpleDateFormat.format(mTask.getMDate());
        time.setText(timeString);

        return view;
    }

}
