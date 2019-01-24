package com.example.mohamadreza.taskapp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity
public class Task {


    @Id(autoincrement = true)
    private Long id;
    private String mTitle;
    private String mDescription;
    private Date mDate=new Date();
    private boolean mDone;
    private Long mUserId;
    @ToOne(joinProperty = "mUserId")
    private User mUser;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;
    @Generated(hash = 1377221062)
    private transient Long mUser__resolvedKey;
    @Generated(hash = 982296862)
    public Task(Long id, String mTitle, String mDescription, Date mDate, boolean mDone,
            Long mUserId) {
        this.id = id;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mDone = mDone;
        this.mUserId = mUserId;
    }
    @Generated(hash = 733837707)
    public Task() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getMTitle() {
        return this.mTitle;
    }
    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public Long getMUserId() {
        return this.mUserId;
    }
    public void setMUserId(Long mUserId) {
        this.mUserId = mUserId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 747440188)
    public User getMUser() {
        Long __key = this.mUserId;
        if (mUser__resolvedKey == null || !mUser__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User mUserNew = targetDao.load(__key);
            synchronized (this) {
                mUser = mUserNew;
                mUser__resolvedKey = __key;
            }
        }
        return mUser;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1811139563)
    public void setMUser(User mUser) {
        synchronized (this) {
            this.mUser = mUser;
            mUserId = mUser == null ? null : mUser.getId();
            mUser__resolvedKey = mUserId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    public String getMDescription() {
        return this.mDescription;
    }
    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public Date getMDate() {
        return this.mDate;
    }
    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }
    public boolean getMDone() {
        return this.mDone;
    }
    public void setMDone(boolean mDone) {
        this.mDone = mDone;
    }
    public String getPhotoName() {
        return "IMG_" + id + ".jpg";
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }
}