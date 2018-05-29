package com.pllug.course.ivankiv.passmanager.ui.authorization.signup;

import android.content.Context;
import android.content.SharedPreferences;

import com.pllug.course.ivankiv.passmanager.data.db.AppDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.InitDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.dao.UserInfoDao;
import com.pllug.course.ivankiv.passmanager.data.model.UserInfo;
import com.pllug.course.ivankiv.passmanager.utils.Crypto;

import java.security.NoSuchAlgorithmException;

import static android.content.Context.MODE_PRIVATE;

public class SignUpPresenter implements SignUpContract.Presenter {
    private SignUpContract.View view;
    private AppDatabase db;
    private UserInfoDao userInfoDao;
    private Crypto crypto;
    private Context mContext;
    private SharedPreferences sPref;

    private String password, reenterPassword, tip, keyS;

    public SignUpPresenter(SignUpContract.View view, Context mContext) {
        this.view = view;
        this.mContext = mContext;
        crypto = new Crypto();
        sPref = mContext.getSharedPreferences("keyStorage", MODE_PRIVATE);
        keyS = "qwerty";
        initDatabase();
    }

    private void initDatabase() {
        db = InitDatabase.getInstance().getDatabese();
        userInfoDao = db.userInfoDao();
    }

    //Method, which check fields on emptied
    private boolean fieldsIsEmpty() {
        if (password == null || password.isEmpty()) {
            return true;
        }

        if (reenterPassword == null || reenterPassword.isEmpty()) {
            return true;
        }

        if (tip == null || tip.isEmpty()) {
            return true;
        }

        return false;
    }

    //Method which check equlity entered passwords
    private boolean passwordIsEquals() {
        if (password.equals(reenterPassword)) {
            return true;
        }

        return false;
    }

    //Method which get key
    private String getKey() {
        String key = crypto.keyGenerator();

        return keyS;
    }

    //Method, which check entered data
    private boolean checkEnteredData() {

        if (fieldsIsEmpty()) {
            view.showToast("Заповніть всі поля!");
            return false;
        }

        if (!passwordIsEquals()) {
            view.showToast("Введені паролі не співпадають!");
            return false;
        }


        return true;
    }

    //Method which send string key to shared preference
    private void sendToSharedPreference(String key) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("key", key);
        editor.commit();
    }

    @Override
    public void getPassword(String password) {
        this.password = password;
    }

    @Override
    public void getReenterPassword(String reenterPassword) {
        this.reenterPassword = reenterPassword;
    }

    @Override
    public void getTip(String tip) {
        this.tip = tip;
    }

    @Override
    public boolean setDataIntoDB() {

        if (checkEnteredData()) {
            UserInfo userInfo = new UserInfo();
            String key = getKey();
            userInfo.setPassword(crypto.encryptedString(key,password));
            userInfo.setTip(crypto.encryptedString(key,tip));
            userInfoDao.insert(userInfo);

            sendToSharedPreference(key);
        } else {
            return false;
        }

        return true;
    }




}
