package com.example.mohamadreza.taskapp.models;

import android.content.Context;

import com.example.mohamadreza.taskapp.Dao.App;
import org.greenrobot.greendao.query.DeleteQuery;

import java.io.File;
import java.util.List;

public class TaskLab {

    private static TaskLab instance;
    private TaskDao mTaskDao;

    private TaskLab() {
        DaoSession mDaoSession = (App.getApp()).getDaoSession();
        mTaskDao = mDaoSession.getTaskDao();
    }

    public static TaskLab getInstance() {
        if (instance == null)
            instance = new TaskLab();

        return instance;
    }

    public List<Task> getTasks(Long userId) {

        List<Task> tasks = mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MUserId.eq(userId)).list();
        return tasks;
    }

    public Task getTask(Long taskId) {
        List<Task> tasks = mTaskDao.queryBuilder()
                .where(TaskDao.Properties.Id.eq(taskId)).list();

        Task task=tasks.get(0);
        return task;
    }


    public void addTask(Task task) {
        mTaskDao.insert(task);
    }

    public void update(Task task) {
        mTaskDao.update(task);
    }

    public void deleteAll(Long userId) {
        final DeleteQuery<Task> taskDeleteQuery = mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MUserId.eq(userId))
                .buildDelete();
        taskDeleteQuery.executeDeleteWithoutDetachingEntities();
    }

    public List<Task> searchTaskList(String search){
        List<Task> findTasks = mTaskDao.queryBuilder()
                .whereOr(TaskDao.Properties.MTitle.like("%"+ search + "%"),
                        TaskDao.Properties.MDescription.like("%"+ search + "%"))
                .list();
        return findTasks;
    }

    public void delete(Task task) {
        mTaskDao.delete(task);
    }

    public File getPhotoFile(Context context,Task task) {
        File filesDir = context.getFilesDir();
        File photoFile = new File(filesDir, task.getPhotoName());

        return photoFile;
    }

}

