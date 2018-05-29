package com.pllug.course.ivankiv.passmanager.ui.main.addpassword.generatepassword;

import java.util.Random;

public class GeneratePasswordPresenter implements GeneratePasswordContract.Presenter {
    private GeneratePasswordContract.View view;
    private boolean isLover = false, isUpper = false,
            isFigures = false, isSpecial = false;
    private int length = 8;

    //alphabet for generation
    private String abc[] = new String[]{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s",
            "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
    private String ABC[] = new String[]{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S",
            "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
    private String special[] = new String[]{"#", "$", "^", "<", ">", "@"};

    public GeneratePasswordPresenter(GeneratePasswordContract.View view) {
        this.view = view;
    }

    private String generatePassword() {
        final Random random = new Random();
        int pLength = 0;
        String password = "";

        //if user set type of password
        if (isFigures || isLover || isUpper || isSpecial) {
            boolean isOne = false;
            int type = 0;

            //if user chose only figures
            if (isFigures && !isLover && !isUpper && !isSpecial) {
                type = 1;
                isOne = true;
            }

            //if user chose only abc
            if (!isFigures && isLover && !isUpper && !isSpecial) {
                type = 2;
                isOne = true;
            }

            //if user chose only ABC
            if (!isFigures && !isLover && isUpper && !isSpecial) {
                type = 3;
                isOne = true;
            }

            //if user chose only special symbols
            if (!isFigures && !isLover && !isUpper && isSpecial) {
                type = 4;
                isOne = true;
            }

            do {

                //if user chose more than one type of symbols
                if (!isOne) {
                    type = random.nextInt(4);
                }

                //If random chose figures and user chose figures
                if (type == 1 && isFigures) {

                    int num = random.nextInt(9);
                    password += Integer.toString(num);
                    pLength += 1;
                }

                //If random chose lover abc and user chose lover abc
                if (type == 2 && isLover) {

                    int numb = random.nextInt(abc.length);

                    if (numb != 0) {
                        password += abc[numb - 1];
                        pLength += 1;
                    }
                }

                //If random chose ABC and user chose lover ABC
                if (type == 3 && isUpper) {

                    int numb = random.nextInt(ABC.length);

                    if (numb != 0) {
                        password += ABC[numb - 1];
                        pLength += 1;
                    }
                }

                //If random chose special symbols and user chose special symbols
                if (type == 4 && isSpecial) {

                    int numb = random.nextInt(special.length);
                    if (numb != 0) {
                        password += special[numb - 1];
                        pLength += 1;
                    }
                }

            } while (pLength < length);
        }

        return password;
    }

    @Override
    public void setLoverLetters(boolean x) {
        isLover = x;
    }

    @Override
    public void setUpperLetters(boolean x) {
        isUpper = x;
    }

    @Override
    public void setFigures(boolean x) {
        isFigures = x;
    }

    @Override
    public void setSpecialSymbols(boolean x) {
        isSpecial = x;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void startGeneratePassword() {
        view.setPasswordEditText(generatePassword());
    }
}
