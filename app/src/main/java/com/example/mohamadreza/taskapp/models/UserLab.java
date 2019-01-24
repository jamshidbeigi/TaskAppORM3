package com.example.mohamadreza.taskapp.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.mohamadreza.taskapp.ActivitySignUp;
import com.example.mohamadreza.taskapp.Dao.App;

import org.greenrobot.greendao.query.Query;
import java.util.List;

public class UserLab {

    private static UserLab instance;
    private UserDao mUserDao;

    private UserLab() {
        DaoSession mDaoSession = (App.getApp()).getDaoSession();
        mUserDao = mDaoSession.getUserDao();
    }

    public static UserLab getInstance() {
        if (instance == null)
            instance = new UserLab();

        return instance;
    }

    public Long checkLogin(String username, String password) {

        Query<User> query = mUserDao.queryBuilder()
                .where(
                        UserDao.Properties.MUserName.eq(username),
                        UserDao.Properties.MPassword.eq(password))
                .build();
        List<User> isFind = query.list();

        if (!isFind.isEmpty()) {
            User user = isFind.get(0);

            return user.getId();
        } else
            return null;
    }

    public void addUser(Context context,User user) {

        List<User> users = mUserDao.queryBuilder()
                .where(UserDao.Properties.MUserName.eq(user.getMUserName()))
                .list();

        if(users.size() > 0){
            Toast.makeText(context,"user is repeatty !",Toast.LENGTH_SHORT).show();
        }
        else {
            mUserDao.insert(user);
        }
    }
    public void deleteUser(Long userId){

        List<User> users = mUserDao.queryBuilder()
                .where(UserDao.Properties.Id.eq(userId))
                .list();

        User user=users.get(0);
        mUserDao.delete(user);
    }

//    public void curentUser(User user){
//
//        mUserDao.insert(user);
//
//    }
}
