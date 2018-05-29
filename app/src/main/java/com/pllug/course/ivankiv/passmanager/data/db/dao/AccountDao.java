package com.pllug.course.ivankiv.passmanager.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pllug.course.ivankiv.passmanager.data.model.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM Account")
    List<Account> getAll();

    @Query("SELECT * FROM Account WHERE id = :id")
    Account getById(long id);

    @Query("SELECT * FROM Account WHERE favorite = :isFavorite")
    List<Account> getFavorites(boolean isFavorite);

    @Query("SELECT * FROM Account WHERE category = :category")
    List<Account> getAllByCategory(String category);

    @Insert
    void insert(Account account);

    @Delete
    void delete(Account account);

    @Update
    int update(Account account);
}
