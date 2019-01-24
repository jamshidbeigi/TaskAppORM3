package com.example.mohamadreza.taskapp.Dao;

import android.app.Application;

import com.example.mohamadreza.taskapp.models.DaoMaster;
import com.example.mohamadreza.taskapp.models.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private static App mApp;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this,"task-app-orm.db");
        Database db = myDevOpenHelper.getWritableDb();
         mDaoSession = new DaoMaster(db).newSession();

         mApp=this;
    }

    public static App getApp(){
        return mApp;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
