package com.pllug.course.ivankiv.passmanager.ui.authorization.sigin;

interface SignInContract  {

    interface View {

        void showToast(String str);
    }

    interface Presenter {

        void setPassword(String password);

        boolean checkPassword();
    }
}
