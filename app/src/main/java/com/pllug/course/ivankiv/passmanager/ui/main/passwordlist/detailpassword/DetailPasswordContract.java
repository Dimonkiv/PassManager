package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.detailpassword;

public interface DetailPasswordContract {

    interface View {

    }

    interface Presenter {

        void loadAccount(long id);

        String getTitle();

        String getUsername();

        String getPassword();

        String getEmail();

        String getWebsite();

        String getNote();

        boolean getFavorite();

        void updateAccount(boolean favorite);

        int getImageCategoryId(String category);

        String getCategory();
    }
}
