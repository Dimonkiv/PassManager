package com.pllug.course.ivankiv.passmanager.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.main.addpassword.AddPasswordFragment;
import com.pllug.course.ivankiv.passmanager.ui.main.addpassword.generatepassword.GeneratePasswordFragment;
import com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.PasswordListFragment;
import com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.detailpassword.DetailPasswordFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPasswordListFragment();
    }

    //Method, which show PasswordListFragment
    public void showPasswordListFragment() {
        replaceFragment(new PasswordListFragment());
    }

    //Method, which show AddPasswordFragment
    public void showAddPasswordFragment() {
        replaceFragment(new AddPasswordFragment());
    }

    //Method, which show AddPasswordFragment
    public void showAddPasswordFragment(String password, String nameF) {
        AddPasswordFragment fragment = new AddPasswordFragment();
        Bundle args = new Bundle();
        args.putString("password", password);
        args.putString("nameF", nameF);
        fragment.setArguments(args);
        replaceFragment(fragment);
    }

    //Method, which show AddPasswordFragment
    public void showAddPasswordFragment(long id, String nameF) {
        AddPasswordFragment fragment = new AddPasswordFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putString("nameF", nameF);
        fragment.setArguments(args);
        replaceFragment(fragment);
    }

    //Method, which show DetailPasswordFragment
    public void showDetailPasswordFragment(long id) {
        DetailPasswordFragment fragment = new DetailPasswordFragment();

        Bundle args = new Bundle();
        args.putLong("id", id);
        fragment.setArguments(args);

        replaceFragment(fragment);
    }

    //Method, which show GeneratePasswordFragment
    public void showGeneratePasswordFragment() {
        replaceFragment(new GeneratePasswordFragment());
    }

    //Метод для заміни фрагментів у frame layout
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
