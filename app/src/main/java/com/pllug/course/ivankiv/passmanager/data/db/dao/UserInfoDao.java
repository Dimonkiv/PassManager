package com.pllug.course.ivankiv.passmanager.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pllug.course.ivankiv.passmanager.data.model.UserInfo;

import java.util.List;

@Dao
public interface UserInfoDao {
    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getAll();

    @Query("SELECT * FROM UserInfo WHERE id = :id")
    UserInfo getById(long id);

    @Insert
    void insert(UserInfo userInfo);
}
