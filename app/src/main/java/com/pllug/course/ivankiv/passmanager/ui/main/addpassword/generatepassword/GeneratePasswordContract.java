package com.pllug.course.ivankiv.passmanager.ui.main.addpassword.generatepassword;

interface GeneratePasswordContract {

    interface View {

        void setPasswordEditText(String password);
    }

    interface Presenter {
        void setLoverLetters(boolean x);

        void setUpperLetters(boolean x);

        void setFigures(boolean x);

        void setSpecialSymbols(boolean x);

        void setLength(int length);

        void startGeneratePassword();
    }
}
