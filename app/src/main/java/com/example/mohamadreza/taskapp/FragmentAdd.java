package com.example.mohamadreza.taskapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mohamadreza.taskapp.date.FragmentDatePicker;
import com.example.mohamadreza.taskapp.date.FragmentTimePicker;
import com.example.mohamadreza.taskapp.models.Task;
import com.example.mohamadreza.taskapp.models.TaskLab;
import com.example.mohamadreza.taskapp.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdd extends DialogFragment {

    private static final String ARG_TASK_ID = "taskId";
    private static final String DIALOG_TAG = "DialogDate";

    private static final int REQ_DATE_PICKER = 0;
    private static final int REQ_TIME_PICKER = 1;
    private static final int REQ_PHOTOS = 2;
    private static final int PICK_IMAGE_REQUEST = 3;


    private Task mTask;

    private EditText mTitleField;
    private EditText mDescription;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mShare;
    private Button mPhotoButton;
    private File mPhotoFile;
    private ImageView mPhotoView;
    private Button mDone;
    private Button mGalleryButton;

    public FragmentAdd() {
        // Required empty public constructor
    }

    public static FragmentAdd newInstance(Long taskId) {
        Bundle args = new Bundle();
        args.putLong(ARG_TASK_ID, taskId);
        FragmentAdd fragment = new FragmentAdd();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        Long taskId = getArguments().getLong(ARG_TASK_ID);
        mTask = TaskLab.getInstance().getTask(taskId);
        mPhotoFile = TaskLab.getInstance().getPhotoFile(getActivity(), mTask);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_task, container, false);

        mTitleField = view.findViewById(R.id.title_edit_text);
        mDescription = view.findViewById(R.id.description_text_view);
        mDateButton = view.findViewById(R.id.button_date);
        mTimeButton = view.findViewById(R.id.button_time);
        CheckBox solvedCheckBox = view.findViewById(R.id.check_box_is_done);
        mDone = view.findViewById(R.id.button_done_add);
        mShare = view.findViewById(R.id.button_share);
        mPhotoButton = view.findViewById(R.id.btn_camera);
        mPhotoView = view.findViewById(R.id.task_photo);
        mGalleryButton = view.findViewById(R.id.btn_gallery);

        mTitleField.setText(mTask.getTitle());
        mDescription.setText(mTask.getMDescription());

        String simpleDate = getDateString();
        mDateButton.setText(simpleDate);
        String date = getTimeString();
        mTimeButton.setText(date);
        solvedCheckBox.setChecked(mTask.getMDone());

        titleETHandler();
        descriptionETHandler();
        doneBtnHandle();
        checkBoxHandle(solvedCheckBox);
        dateBtnHandle();
        timeBtnHandle();
        handleShareButton();
        handlePhotoButton();
        updatePhotoView();
        handleGalleryButton();

        return view;
    }

    private void doneBtnHandle() {
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTask.getTitle() != null) {
                    getDialog().onBackPressed();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("you dont create a Task!");
                    builder.setMessage("Write a Title");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private void titleETHandler() {
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void descriptionETHandler() {
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setMDescription(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkBoxHandle(CheckBox solvedCheckBox) {
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTask.setMDone(isChecked);
            }
        });
    }

    private void timeBtnHandle() {
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTimePicker timePickerFragment = FragmentTimePicker.newInstance(mTask.getMDate());
                timePickerFragment.setTargetFragment(FragmentAdd.this,
                        REQ_TIME_PICKER);
                if (getFragmentManager() != null) {
                    timePickerFragment.show(getFragmentManager(), DIALOG_TAG);
                }
            }
        });
    }

    private void dateBtnHandle() {
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDatePicker datePickerFragment = FragmentDatePicker.newInstance(mTask.getMDate());
                datePickerFragment.setTargetFragment(FragmentAdd.this,
                        REQ_DATE_PICKER);
                if (getFragmentManager() != null) {
                    datePickerFragment.show(getFragmentManager(), DIALOG_TAG);
                }
            }
        });
    }

    private void handleShareButton() {
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(Intent.ACTION_SEND);
                reportIntent.setType("text/plain");
                reportIntent.putExtra(Intent.EXTRA_TEXT, getTaskText());
                reportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.task_subject));

                startActivity(Intent.createChooser(reportIntent, getString(R.string.send_task)));
            }
        });
    }

    private String getTaskText() {
        @SuppressLint
                ("SimpleDateFormat") String dateString = new SimpleDateFormat("yyyy/MM/dd").format(mTask.getMDate());

        String solvedString = mTask.getMDone() ?
                getString(R.string.task_done) :
                getString(R.string.task_undone);

        return getString(R.string.task_text, mTask.getTitle(), mTask.getMDescription(), dateString, solvedString);
    }


    private void handlePhotoButton() {
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Uri uri = getPhotoFileUri();
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                PackageManager packageManager = getActivity().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(
                        captureIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : activities) {
                    getActivity().grantUriPermission(
                            activity.activityInfo.packageName,
                            uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureIntent, REQ_PHOTOS);
            }
        });
    }


    private void handleGalleryButton() {
        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getImageIntent.setType("image/*");
                Uri uri = getPhotoFileUri();
                getImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                PackageManager packageManager = getActivity().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(
                        getImageIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : activities) {
                    getActivity().grantUriPermission(
                            activity.activityInfo.packageName,
                            uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(getImageIntent, PICK_IMAGE_REQUEST);
            }
        });

    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(),
                    getActivity());

            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private Uri getPhotoFileUri() {
        return FileProvider.getUriForFile(getActivity(),
                "com.example.mohamadreza.taskapp.fileprovider",
                mPhotoFile);
    }


    public String getDateString() {
        String pattern = "E,yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "US"));
        return simpleDateFormat.format(mTask.getMDate());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTask.getTitle() == null || mTask.getTitle().equals("")) {
            TaskLab.getInstance().delete(mTask);
        }
        TaskLab.getInstance().update(mTask);
        if (getTargetFragment() != null) {
            getTargetFragment().onResume();
        }


    }

    @Override
    public void onPause() {
        super.onPause();

        TaskLab.getInstance().update(mTask);
        if (getTargetFragment() != null) {
            getTargetFragment().onResume();
        }
    }

    public String getTimeString() {
        String pattern = "HH:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "US"));
        return simpleDateFormat.format(mTask.getMDate());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQ_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(FragmentDatePicker.EXTRA_DATE);

            mTask.setMDate(date);
            String simpleDate = getDateString();
            mDateButton.setText(simpleDate);

        } else if (requestCode == REQ_TIME_PICKER) {
            Date date = (Date) data.getSerializableExtra(FragmentTimePicker.EXTRA_TIME);
            mTask.setMDate(date);

            String simpleDate = getDateString();
            mDateButton.setText(simpleDate);

            String time = getTimeString();

            mTimeButton.setText(time);
        } else if (requestCode == REQ_PHOTOS) {
            Uri uri = getPhotoFileUri();
            if (getActivity() != null) {
                getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                updatePhotoView();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {

            Uri selectedImageUri = data.getData();
            mPhotoView.setImageURI(selectedImageUri);

        }
    }

}