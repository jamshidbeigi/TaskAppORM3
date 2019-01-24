package com.example.mohamadreza.taskapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.mohamadreza.taskapp.models.User;
import com.example.mohamadreza.taskapp.models.UserLab;

public class ActivitySignUp extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private Button mSignUp;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ActivitySignUp.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        mUserName = findViewById(R.id.edit_text_username);
        mPassword = findViewById(R.id.edit_text_password);
        mSignUp = findViewById(R.id.button_sign_up);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setMUserName(mUserName.getText().toString());
                user.setMPassword(mPassword.getText().toString());
                UserLab.getInstance().addUser(ActivitySignUp.this,user);
                onBackPressed();
            }
        });
    }
}