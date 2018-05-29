package com.pllug.course.ivankiv.passmanager.ui.authorization.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.authorization.AuthorizationActivity;

/**
 * Created by iw97d on 19.03.2018.
 */

public class WelcomeFragment extends Fragment {
    private View root;
    private Button btn;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_welcome, container, false);

        initView();
        initListener();

        return root;
    }

    private void initView() {
        btn = root.findViewById(R.id.btn_go_to_sign_up);
    }

    private void initListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthorizationActivity)getActivity()).showSignUp();
            }
        });
    }
}
