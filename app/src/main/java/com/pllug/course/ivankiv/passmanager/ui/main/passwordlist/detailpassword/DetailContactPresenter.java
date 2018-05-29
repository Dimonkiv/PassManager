package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.detailpassword;

import android.content.Context;
import android.content.SharedPreferences;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.data.db.AppDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.InitDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.dao.AccountDao;
import com.pllug.course.ivankiv.passmanager.data.model.Account;
import com.pllug.course.ivankiv.passmanager.utils.Crypto;

public class DetailContactPresenter implements DetailPasswordContract.Presenter {
    private DetailPasswordContract.View view;
    private AppDatabase db;
    private AccountDao accountDao;
    private Account account;
    private SharedPreferences sPref;
    private Crypto crypto;
    private String key;

    public DetailContactPresenter(DetailPasswordContract.View view, Context mContext) {
        this.view = view;
        initDatabase();
        crypto = new Crypto();
        sPref = mContext.getSharedPreferences("keyStorage", Context.MODE_PRIVATE);
        key = sPref.getString("key", "");
    }

    private void initDatabase() {
        db = InitDatabase.getInstance().getDatabese();
        accountDao = db.accountDao();
    }

    private String decryptString(String encryptedString) {
        return crypto.decryptString(key, encryptedString);
    }




    @Override
    public void loadAccount(long id) {
        account = accountDao.getById(id);
    }

    @Override
    public String getTitle() {
        return decryptString(account.getTitle());
    }

    @Override
    public String getUsername() {
        return decryptString(account.getUsername());
    }

    @Override
    public String getPassword() {
        return decryptString(account.getPassword());
    }

    @Override
    public String getEmail() {
        return decryptString(account.getEmail());
    }

    @Override
    public String getWebsite() {
        return decryptString(account.getWebsite());
    }

    @Override
    public String getNote() {
        return decryptString(account.getNote());
    }

    @Override
    public boolean getFavorite() {
        return account.isFavorite();
    }

    @Override
    public void updateAccount(boolean favorite) {
        account.setFavorite(favorite);
        accountDao.update(account);
    }

    @Override
    public int getImageCategoryId(String category) {

        int imageId = 0;
        switch (category) {
            case "personal":
                imageId = R.drawable.image_personal;
                break;
            case "social":
                imageId = R.drawable.image_social;
                break;
            case "finance":
                imageId = R.drawable.image_finance;
                break;
            case "work":
                imageId = R.drawable.image_work;
                break;
            case "other":
                imageId = R.drawable.image_others;
                break;
            default:
                imageId = R.drawable.image_personal;
                break;
        }
        return imageId;
    }

    @Override
    public String getCategory() {
        return decryptString(account.getCategory());
    }
}
