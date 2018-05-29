package com.pllug.course.ivankiv.passmanager.ui.main.addpassword;

import android.content.Context;
import android.content.SharedPreferences;

import com.pllug.course.ivankiv.passmanager.data.db.AppDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.InitDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.dao.AccountDao;
import com.pllug.course.ivankiv.passmanager.data.model.Account;
import com.pllug.course.ivankiv.passmanager.utils.Crypto;

import java.util.ArrayList;
import java.util.List;

public class AddPasswordPresenter implements AddPasswordContract.Presenter {
    private AddPasswordContract.View view;
    private AppDatabase db;
    private AccountDao accountDao;
    private String title, username, password, reenterPassword, email,
            website, notes, category;
    private Account account;
    private SharedPreferences sPref;
    private Crypto crypto;
    private String key;

    public AddPasswordPresenter(AddPasswordContract.View view, Context mContext) {
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

    //Method which check equlity entered passwords
    private boolean passwordIsEquals() {
        if (password.equals(reenterPassword)) {
            return true;
        }

        return false;
    }

    //Method, which check fields on emptied
    private boolean fieldsIsEmpty() {
        if (title == null || title.isEmpty()) {
            return true;
        }

        if (username == null || username.isEmpty()) {
            return true;
        }

        if (password == null || password.isEmpty()) {
            return true;
        }

        if (reenterPassword == null || reenterPassword.isEmpty()) {
            return true;
        }

        if (email == null || email.isEmpty()) {
            return true;
        }

        if (website == null || website.isEmpty()) {
            return true;
        }

        if (notes == null || notes.isEmpty()) {
            return true;
        }

        return false;
    }

    private String decryptString(String encryptedString) {
        return crypto.decryptString(key, encryptedString);
    }

    private Account decryptAccount(Account encryptedAccount) {
        Account account = new Account();
        if (encryptedAccount.getId() != 0) {
            account.setId(encryptedAccount.getId());
        }
        account.setTitle(decryptString(encryptedAccount.getTitle()));
        account.setUsername(decryptString(encryptedAccount.getUsername()));
        account.setPassword(decryptString(encryptedAccount.getPassword()));
        account.setEmail(decryptString(encryptedAccount.getEmail()));
        account.setWebsite(decryptString(encryptedAccount.getWebsite()));
        account.setNote(decryptString(encryptedAccount.getNote()));
        account.setFavorite(encryptedAccount.isFavorite());


        return account;
    }

    private Account encryptAccount(Account account) {
        Account encryptedAccount = new Account();

        if (account.getId() != 0) {
            encryptedAccount.setId(account.getId());
        }
        encryptedAccount.setTitle(encryptString(account.getTitle()));
        encryptedAccount.setUsername(encryptString(account.getUsername()));
        encryptedAccount.setPassword(encryptString(account.getPassword()));
        encryptedAccount.setEmail(encryptString(account.getEmail()));
        encryptedAccount.setWebsite(encryptString(account.getWebsite()));
        encryptedAccount.setNote(encryptString(account.getNote()));
        encryptedAccount.setCategory(encryptString(account.getCategory()));
        encryptedAccount.setFavorite(account.isFavorite());


        return encryptedAccount;
    }

    private String encryptString(String string) {
        return crypto.encryptedString(key, string);
    }



    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setReenterPassword(String reenterPassword) {
        this.reenterPassword = reenterPassword;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public void setNote(String note) {
        this.notes = note;
    }

    @Override
    public String getCategory() {
        return account.getCategory();
    }

    @Override
    public String getTitle() {
        return account.getTitle();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getEmail() {
        return account.getEmail();
    }

    @Override
    public String getWebsite() {
        return account.getWebsite();
    }

    @Override
    public String getNote() {
        return account.getNote();
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void loadAccount(long id) {
        if (id != 0) {
            account = decryptAccount(accountDao.getById(id));
        }
    }

    @Override
    public boolean updateAccount() {
        if (checkEnteredData()) {
            account.setTitle(title);
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setWebsite(website);
            account.setNote(notes);
            account.setCategory(category);

            accountDao.update(encryptAccount(account));
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean insertDataInDB() {
        if (checkEnteredData()) {
            Account account = new Account();

            account.setTitle(title);
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setWebsite(website);
            account.setNote(notes);
            account.setCategory(category);

            Account encryptedAc = encryptAccount(account);
            accountDao.insert(encryptedAc);
        } else {
            return false;
        }

        return true;
    }

    @Override
    public int getCategoryId(String category) {
        int id = 0;

        switch (category) {
            case "personal":
                id = 0;
                break;
            case "social":
                id = 1;
                break;
            case "finance":
                id = 2;
                break;
            case "work":
                id = 3;
                break;
            case "other":
                id = 4;
                break;
            default:
                id = 0;
                break;
        }

        return id;
    }

    @Override
    public Account getAccount() {
        return account;
    }
}
