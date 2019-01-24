package com.example.mohamadreza.taskapp.models;

public class CurrentPosition {
     private static Long mUserId;
     private static boolean mIsLogedIn;

    public static boolean isLogedIn() {
        return mIsLogedIn;
    }

    public static void setLogedIn(boolean logedIn) {
        mIsLogedIn = logedIn;
    }

    public static Long getUserId() {
        return mUserId;
    }

    public static void setUserId(Long muserId) {
        mUserId = muserId;
    }
}
