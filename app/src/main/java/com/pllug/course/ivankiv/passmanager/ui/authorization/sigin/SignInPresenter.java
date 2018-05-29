package com.pllug.course.ivankiv.passmanager.ui.authorization.sigin;

import android.content.Context;
import android.content.SharedPreferences;

import com.pllug.course.ivankiv.passmanager.data.db.AppDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.InitDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.dao.UserInfoDao;
import com.pllug.course.ivankiv.passmanager.utils.Crypto;

public class SignInPresenter implements SignInContract.Presenter {
    private SignInContract.View view;
    private AppDatabase db;
    private UserInfoDao userInfoDao;
    private String password;
    private Crypto crypto;
    private SharedPreferences sPref;

    public SignInPresenter(SignInContract.View view, Context mContext) {
        this.view = view;
        initDatabase();
        crypto = new Crypto();
        sPref = mContext.getSharedPreferences("keyStorage", Context.MODE_PRIVATE);
    }

    //Initialization database
    private void initDatabase() {
        db = InitDatabase.getInstance().getDatabese();
        userInfoDao = db.userInfoDao();
    }

    //Method, which check equality entered password and password from db
    private boolean isPasswordsEqual() {
        String encryptedString = userInfoDao.getById(1).getPassword();
        String key = sPref.getString("key", "");
        String normPass = crypto.decryptString(key, encryptedString);
        if (normPass.equals(password)) {
            return true;
        }


        return false;
    }



    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkPassword() {

        if (isPasswordsEqual()) {
            return true;
        } else {
            view.showToast("Неправильний пароль!");
        }

        return false;
    }


}
