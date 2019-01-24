package com.example.mohamadreza.taskapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamadreza.taskapp.models.CurrentPosition;
import com.example.mohamadreza.taskapp.models.User;
import com.example.mohamadreza.taskapp.models.UserLab;

public class ActivityLogin extends AppCompatActivity {

    private String username;
    private String password;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ActivityLogin.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Button login = findViewById(R.id.button_login);
        TextView signUp = findViewById(R.id.text_view_sign_up);
        TextView guestMode = findViewById(R.id.text_view_guest_mode);
        EditText userNameEditText = findViewById(R.id.edit_text_user_name_login);
        EditText passwordEditText = findViewById(R.id.edit_text_password_login);

        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        guestMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long guestId = (long) -1;
                CurrentPosition.setUserId(guestId);
                User user = new User();
                user.setId(guestId);
                user.setMUserName("Guest");
                UserLab.getInstance().addUser(ActivityLogin.this,user);
                Intent intent = ActivityMain.newIntent(ActivityLogin.this);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ActivitySignUp.newIntent(ActivityLogin.this);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long userId = UserLab.getInstance().checkLogin(username,password);
                if(userId==null){
                    Toast.makeText(ActivityLogin.this,"USER NOT FOUND",Toast.LENGTH_SHORT).show();}
                else{
                    CurrentPosition.setUserId(userId);
                    Intent intent = ActivityMain.newIntent(ActivityLogin.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
