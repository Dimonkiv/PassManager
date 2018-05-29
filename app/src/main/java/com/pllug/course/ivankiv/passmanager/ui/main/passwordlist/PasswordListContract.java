package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist;

import com.pllug.course.ivankiv.passmanager.data.model.Account;

import java.util.List;

interface PasswordListContract {

    interface View {

        void showDetailContactFragment(long id);
    }

    interface Presenter {

        int getImageCategoryId(String category);

        void updateFavorite(Account account, boolean favorite);

        List<Account> getAccounts();

        List<Account> getAccountsByCategory(String category);

        List<Account> getFavorites();

        void deleteAccount(Account account);

        void sendAccountIdToFragment(long id);
    }
}
