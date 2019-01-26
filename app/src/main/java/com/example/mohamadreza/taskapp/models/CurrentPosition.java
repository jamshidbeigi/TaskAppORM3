package com.example.mohamadreza.taskapp.models;

public class CurrentPosition {
    private static Long mUserId;

    public static Long getUserId() {
        return mUserId;
    }

    public static void setUserId(Long muserId) {
        mUserId = muserId;
    }
}
