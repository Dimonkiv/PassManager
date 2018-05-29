package com.pllug.course.ivankiv.passmanager.ui.authorization.signup;

import android.app.Presentation;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.authorization.AuthorizationActivity;


/**
 * Created by iw97d on 19.03.2018.
 */

public class SignUpFragment extends Fragment implements SignUpContract.View {
    private View root;
    private Toolbar toolbar;
    private SignUpPresenter presenter;

    //View
    private EditText password, reenterPassword, tip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initView();
        initToolbar();
        initPresenter();
        initEditTextWatcher();

        return root;
    }

    //Initialization View
    private void initView() {
        toolbar = root.findViewById(R.id.sign_up_toolbar);
        password = root.findViewById(R.id.sign_up_password);
        reenterPassword = root.findViewById(R.id.sign_up_reenter_password);
        tip = root.findViewById(R.id.sign_up_tips);
    }

    //Initialization toolbar
    private void initToolbar() {
        ((AuthorizationActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Створити обліковий запис");
        setHasOptionsMenu(true);
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new SignUpPresenter(this, getActivity());
    }

    //Send change data in EditText to presenter
    void initEditTextWatcher() {
        //password
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.getPassword(s.toString());
            }
        });

        //reenterPassword
        reenterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.getReenterPassword(s.toString());
            }
        });

        //tip
        tip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.getTip(s.toString());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_up_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_toolbar_send) {
            if (presenter.setDataIntoDB()) {
                Toast.makeText(getActivity(), "Sended", Toast.LENGTH_SHORT).show();
                ((AuthorizationActivity)getActivity()).saveFirstLaunch();
                ((AuthorizationActivity)getActivity()).showFragment();

            }
        }
        return true;
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
