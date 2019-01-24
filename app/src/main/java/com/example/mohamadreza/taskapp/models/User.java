package com.example.mohamadreza.taskapp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity()
public class User {

    @Id
    private Long id;
    @Unique
    private String mUserName;
    private String mPassword;


    @Generated(hash = 480302010)
    public User(Long id, String mUserName, String mPassword) {
        this.id = id;
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMUserName() {
        return this.mUserName;
    }

    public void setMUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getMPassword() {
        return this.mPassword;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}