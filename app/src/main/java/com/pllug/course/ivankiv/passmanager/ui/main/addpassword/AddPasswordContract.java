package com.pllug.course.ivankiv.passmanager.ui.main.addpassword;

import com.pllug.course.ivankiv.passmanager.data.model.Account;

interface AddPasswordContract {

    interface View {
        void showToast(String str);
    }

    interface Presenter {

        void setTitle(String title);

        void setUsername(String username);

        void setPassword(String password);

        void setReenterPassword(String reenterPassword);

        void setEmail(String email);

        void setWebsite(String website);

        void setNote(String note);

        String getCategory();

        String getTitle();

        String getUsername();

        String getPassword();

        String getEmail();

        String getWebsite();

        String getNote();

        void setCategory(String category);

        void loadAccount(long id);

        boolean updateAccount();

        boolean insertDataInDB();

        int getCategoryId(String category);

        Account getAccount();
    }
}
