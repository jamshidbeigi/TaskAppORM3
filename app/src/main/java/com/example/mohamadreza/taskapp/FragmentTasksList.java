package com.example.mohamadreza.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mohamadreza.taskapp.models.CurrentPosition;
import com.example.mohamadreza.taskapp.models.Task;
import com.example.mohamadreza.taskapp.models.TaskLab;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTasksList extends Fragment {

    private static final String ARG_TAB_POSITION = "tab.position";
    private static final String DIALOG_TAG = "DialogDate";
    public static final int DOWN_POSITION = 1;

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private ImageView mImageView;
    private EditText mETSearch;

    public static FragmentTasksList newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_POSITION, position);
        FragmentTasksList fragment = new FragmentTasksList();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTasksList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_show_recycler_view, container, false);
        mImageView = view.findViewById(R.id.imageView_empty);
        FloatingActionButton addButton = view.findViewById(R.id.floating_add_button);
        mRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        mETSearch = view.findViewById(R.id.edit_text_search);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.setMUserId(CurrentPosition.getUserId());

                TaskLab.getInstance().addTask(task);

                FragmentAdd fragmentAdd = FragmentAdd.newInstance(task.getId());
                fragmentAdd.setTargetFragment(FragmentTasksList.this,
                        1);
                if (getFragmentManager() != null) {
                    fragmentAdd.show(getFragmentManager(), DIALOG_TAG);
                }
            }
        });


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {


        mETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Task> tasks = TaskLab.getInstance().searchTaskList(s.toString());
                updateListAndView(tasks);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

            List<Task> tasks = TaskLab.getInstance().getTasks(CurrentPosition.getUserId());
            updateListAndView(tasks);
    }

    private void updateListAndView(List<Task> tasks) {
        int tabPosition = getArguments() != null ? getArguments().getInt(ARG_TAB_POSITION) : 0;
        if (tabPosition == DOWN_POSITION) {
            List<Task> mTasks = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getMDone())
                    mTasks.add(task);
            }
            tasks = mTasks;
        }
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }

        if (tabPosition == 0 && tasks.isEmpty()) {
            mImageView.setVisibility(View.VISIBLE);
        } else
            mImageView.setVisibility(View.GONE);
    }


    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mWordTextView;

        private Task mTask;

         TaskHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.list_item_task_title);
            mDateTextView = itemView.findViewById(R.id.list_item_task_date);
            mWordTextView = itemView.findViewById(R.id.text_view_first_word);
             Button edit = itemView.findViewById(R.id.button_edit);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentDescription fragmentDescription = FragmentDescription.newInstance(mTask.getId());
                    if (getFragmentManager() != null) {
                        fragmentDescription.show(getFragmentManager(), DIALOG_TAG);
                    }
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentAdd fragmentAdd = FragmentAdd.newInstance(mTask.getId());
                    fragmentAdd.setTargetFragment(FragmentTasksList.this,
                            1);
                    if (getFragmentManager() != null) {
                        fragmentAdd.show(getFragmentManager(), DIALOG_TAG);
                    }
                }
            });
        }

        public void bind(Task task) {
            mTask = task;
            mTitleTextView.setText(task.getTitle());
            mDateTextView.setText(task.getMDate().toString());
            if (task.getTitle() != null) {
                char s = task.getTitle().charAt(0);
                mWordTextView.setText(Character.toString(s));
            }
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_task, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);

        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
        Long guestId = (long) -1;
        if (!CurrentPosition.getUserId().equals(guestId)) {
            menu.findItem(R.id.login_menu).setVisible(false);
//            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.delete_task_menu:
                    TaskLab.getInstance().deleteAll(CurrentPosition.getUserId());
                updateUI();
                return true;

            case R.id.login_menu:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                Intent intent = ActivityLogin.newIntent(getActivity());
                startActivity(intent);


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


