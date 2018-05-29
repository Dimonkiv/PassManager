package com.pllug.course.ivankiv.passmanager.ui.authorization.signup;

import java.security.NoSuchAlgorithmException;

interface SignUpContract {

    interface View {
        void showToast(String str);
    }

    interface Presenter {

        void getPassword(String password);

        void getReenterPassword(String reenterPassword);

        void getTip(String tip);

        boolean setDataIntoDB() throws NoSuchAlgorithmException;
    }
}
