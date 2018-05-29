package com.pllug.course.ivankiv.passmanager.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.pllug.course.ivankiv.passmanager.data.db.dao.AccountDao;
import com.pllug.course.ivankiv.passmanager.data.db.dao.UserInfoDao;
import com.pllug.course.ivankiv.passmanager.data.model.Account;
import com.pllug.course.ivankiv.passmanager.data.model.UserInfo;

@Database(entities = {UserInfo.class, Account.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserInfoDao userInfoDao();

    public abstract AccountDao accountDao();

}
