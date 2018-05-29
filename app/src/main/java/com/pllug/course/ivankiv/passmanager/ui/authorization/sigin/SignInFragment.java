package com.pllug.course.ivankiv.passmanager.ui.authorization.sigin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.authorization.AuthorizationActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by iw97d on 20.03.2018.
 */

public class SignInFragment extends Fragment implements SignInContract.View {
    private View root;
    private SignInPresenter presenter;

    //View
    private EditText password;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_sign_in, container, false);

        initView();
        initPresenter();
        initListener();
        return root;
    }


    //Initialization View
    private void initView() {
        password = root.findViewById(R.id.sign_in_password);
        btn = root.findViewById(R.id.sign_in_btn);
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new SignInPresenter(this, getActivity());
    }

    //Initialization listener
    private void initListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setPassword(password.getText().toString());

                if (presenter.checkPassword()) {
                    ((AuthorizationActivity)getActivity()).goToMainActivity();
                }
            }
        });
    }


    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
