package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist;

import android.content.Context;
import android.content.SharedPreferences;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.data.db.AppDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.InitDatabase;
import com.pllug.course.ivankiv.passmanager.data.db.dao.AccountDao;
import com.pllug.course.ivankiv.passmanager.data.model.Account;
import com.pllug.course.ivankiv.passmanager.utils.Crypto;

import java.util.ArrayList;
import java.util.List;

public class PasswordListPresenter implements PasswordListContract.Presenter {
    private PasswordListContract.View view;
    private AppDatabase db;
    private AccountDao accountDao;
    private Crypto crypto;
    private SharedPreferences sPref;
    private String key;

    public PasswordListPresenter(PasswordListContract.View view, Context mContext) {
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

    private List<Account> decryptAccounts(List<Account> encryptedAccounts) {
        List<Account> accounts = new ArrayList<>();

        for (Account encryptedAccount : encryptedAccounts) {
            Account account = new Account();
            account.setId(encryptedAccount.getId());
            account.setTitle(decryptString(encryptedAccount.getTitle()));
            account.setUsername(decryptString(encryptedAccount.getUsername()));
            account.setPassword(decryptString(encryptedAccount.getPassword()));
            account.setEmail(decryptString(encryptedAccount.getEmail()));
            account.setWebsite(decryptString(encryptedAccount.getWebsite()));
            account.setNote(decryptString(encryptedAccount.getNote()));
            account.setCategory(decryptString(encryptedAccount.getCategory()));
            account.setFavorite(encryptedAccount.isFavorite());

            accounts.add(account);

        }

        return accounts;
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

    private String decryptString(String encryptedString) {
        return crypto.decryptString(key, encryptedString);
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
    public void updateFavorite(Account account, boolean favorite) {
        account.setFavorite(favorite);
        accountDao.update(encryptAccount(account));
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = accountDao.getAll();
        List<Account> ec = decryptAccounts(accounts);
        return ec;
    }

    @Override
    public List<Account> getAccountsByCategory(String category) {
        List<Account> accounts = accountDao.getAllByCategory(encryptString(category));

        return decryptAccounts(accounts);
    }

    @Override
    public List<Account> getFavorites() {
        List<Account> enccyptedAccounts = accountDao.getFavorites(true);
        return decryptAccounts(enccyptedAccounts);
    }

    @Override
    public void deleteAccount(Account account) {
        accountDao.delete(account);
    }

    @Override
    public void sendAccountIdToFragment(long id) {
        view.showDetailContactFragment(id);
    }
}
