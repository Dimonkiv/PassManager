package com.pllug.course.ivankiv.passmanager.ui.main.addpassword.generatepassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.main.MainActivity;

public class GeneratePasswordFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        CompoundButton.OnCheckedChangeListener, GeneratePasswordContract.View{
    private View root;
    private GeneratePasswordPresenter presenter;

    //View
    private Toolbar toolbar;
    private CheckBox lowerChB, upperChB, figuresChB, specialChB;
    private EditText password;
    private TextView passwordLengthTxt;
    private SeekBar seekBar;
    private Button generateBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_generate_password, container, false);

        initView();
        initToolbar();
        initPresenter();
        initListener();
        initSeekBarChangeListener();
        initCheckedChangeListener();
        return root;
    }

    //Initialization view
    private void initView() {
        toolbar = root.findViewById(R.id.generate_password_toolbar);
        //checkbox
        lowerChB = root.findViewById(R.id.generate_password_lower_ch_b);
        upperChB = root.findViewById(R.id.generate_password_upper_ch_b);
        figuresChB = root.findViewById(R.id.generate_password_figures_ch_b);
        specialChB = root.findViewById(R.id.generate_password_special_ch_b);
        //edit text
        password = root.findViewById(R.id.generate_password_password);
        //text view
        passwordLengthTxt = root.findViewById(R.id.generate_password_length_txt);
        //seek bar
        seekBar = root.findViewById(R.id.generate_password_seek_bar);
        generateBtn = root.findViewById(R.id.generate_password_generate_btn);
    }

    //Initialization toolbar
    private void initToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Генератор паролів");
        setHasOptionsMenu(true);

        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showAddPasswordFragment(password.getText().toString(), "generatePassword");
            }
        });
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new GeneratePasswordPresenter(this);
    }

    //Initialization listener
    private void initListener() {
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startGeneratePassword();
            }
        });
    }

    //Initialization Seek Bar Change Listener
    private void initSeekBarChangeListener() {
        seekBar.setOnSeekBarChangeListener(this);
    }

    //Initialization checked change listener
    private void initCheckedChangeListener() {
        lowerChB.setOnCheckedChangeListener(this);
        upperChB.setOnCheckedChangeListener(this);
        figuresChB.setOnCheckedChangeListener(this);
        specialChB.setOnCheckedChangeListener(this);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        passwordLengthTxt.setText(String.valueOf(seekBar.getProgress()));
        presenter.setLength(seekBar.getProgress());

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.generate_password_lower_ch_b:
                    presenter.setLoverLetters(true);
                    break;
                case R.id.generate_password_upper_ch_b:
                    presenter.setUpperLetters(true);
                    break;
                case R.id.generate_password_figures_ch_b:
                    presenter.setFigures(true);
                    break;
                case R.id.generate_password_special_ch_b:
                    presenter.setSpecialSymbols(true);
                    break;
            }
        } else {
            switch (buttonView.getId()) {
                case R.id.generate_password_lower_ch_b:
                    presenter.setLoverLetters(false);
                    break;
                case R.id.generate_password_upper_ch_b:
                    presenter.setUpperLetters(false);
                    break;
                case R.id.generate_password_figures_ch_b:
                    presenter.setFigures(false);
                    break;
                case R.id.generate_password_special_ch_b:
                    presenter.setSpecialSymbols(false);
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_up_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_toolbar_send) {
            ((MainActivity)getActivity()).showAddPasswordFragment(password.getText().toString(), "generatePassword");
        }
        return true;
    }

    @Override
    public void setPasswordEditText(String password) {
        this.password.setText(password);
    }
}
