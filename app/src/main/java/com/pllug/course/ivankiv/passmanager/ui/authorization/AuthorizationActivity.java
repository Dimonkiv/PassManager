package com.pllug.course.ivankiv.passmanager.ui.authorization;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.authorization.sigin.SignInFragment;
import com.pllug.course.ivankiv.passmanager.ui.authorization.signup.SignUpFragment;
import com.pllug.course.ivankiv.passmanager.ui.authorization.welcome.WelcomeFragment;
import com.pllug.course.ivankiv.passmanager.ui.main.MainActivity;

public class AuthorizationActivity extends AppCompatActivity {
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        initSharedPreferences();
        showFragment();


    }


    public void showFragment() {
        if (loadFirstLaunch()) {
            showSignIn();
        } else {
            showWelcomeFragment();
        }
    }
    //Initialization shared preferences
    private void initSharedPreferences() {
        sPref = getPreferences(MODE_PRIVATE);
    }

    //Метод для додавання фрагментів у frame layout
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.sign_in_frame_container, fragment)
                .commit();
    }

    //Метод для заміни фрагментів у frame layout
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.sign_in_frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    //Method, which open to MainActivity
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    //Method, which load data about first launch
    //from shared preferences
    public void saveFirstLaunch() {
        SharedPreferences.Editor edit = sPref.edit();
        edit.putBoolean("firstLaunch", true);
        edit.apply();
    }

    //Method whic load data from shared preference
    private boolean loadFirstLaunch() {
        return sPref.getBoolean("firstLaunch", false);
    }

    //Метод для відкривання welcome fragment
    private void showWelcomeFragment() {
        addFragment(new WelcomeFragment());
    }

    //Метод для відображення sign up fragment
    public void showSignUp() {
        replaceFragment(new SignUpFragment());
    }

    //Метод для відображення фрагменту входу
    public void showSignIn() {
        replaceFragment(new SignInFragment());
    }
}
