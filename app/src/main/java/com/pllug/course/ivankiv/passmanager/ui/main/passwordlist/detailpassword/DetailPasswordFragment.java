package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.detailpassword;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.main.MainActivity;

public class DetailPasswordFragment extends Fragment implements DetailPasswordContract.View,
        View.OnClickListener{
    private View root;
    private long id;
    private DetailContactPresenter presenter;
    private boolean x = false;

    //View
    private Toolbar toolbar;
    private ImageView categoryImage, editImage, favoriteImage;
    private ImageButton showPasswordBtn;
    private TextView username, title, password;
    private Button copyBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_password, container, false);

        initView();
        initToolbar();
        initPresenter();
        initListener();
        getDataFromPasswordListFragment();
        setDataInFields();

        return root;
    }

    //Initialization view
    private void initView() {
        //image view
        categoryImage = root.findViewById(R.id.detail_password_category_image);
        editImage = root.findViewById(R.id.detail_password_edit_btn);
        favoriteImage = root.findViewById(R.id.detail_password_favorite_image);
        //image button
        showPasswordBtn = root.findViewById(R.id.detail_password_show_password_btn);
        //text view
        username = root.findViewById(R.id.detail_password_username);
        title = root.findViewById(R.id.detail_password_title);
        password = root.findViewById(R.id.detail_password_password);
        //toolbar
        toolbar = root.findViewById(R.id.detail_password_toolbar);
        //button
        copyBtn = root.findViewById(R.id.detail_password_copy_btn);
    }

    //Initialization toolbar
    private void initToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");
        setHasOptionsMenu(true);

        //Set back button
        toolbar.setNavigationIcon(R.drawable.icon_back);
        //Listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showPasswordListFragment();
            }
        });
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new DetailContactPresenter(this, getActivity());
    }

    //Initialization listener
    private void initListener() {
        editImage.setOnClickListener(this);
        favoriteImage.setOnClickListener(this);
        showPasswordBtn.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
    }

    //Method which get id from PasswordListFragment
    private void getDataFromPasswordListFragment() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            id = bundle.getLong("id", 0);
            loadAccount(id);
        }
    }

    //Method which load account in presenter
    private void loadAccount(long id) {
        if (id != 0) {
            presenter.loadAccount(id);
        }
    }

    //Method which set data into fields from presenter
    private void setDataInFields() {
        title.setText(presenter.getTitle());
        username.setText(presenter.getUsername());
        password.setText(presenter.getPassword());

        if (presenter.getFavorite()) {
            favoriteImage.setImageResource(R.drawable.icon_star_selected);
        } else {
            favoriteImage.setImageResource(R.drawable.icon_star);
        }

        Glide.with(getActivity())
                .load(presenter.getImageCategoryId(presenter.getCategory()))
                .into(categoryImage);
    }

    //Method which processing click on favoriteBtn
    private void onFavoriteButtonClick() {
        if (presenter.getFavorite()) {
            favoriteImage.setImageResource(R.drawable.icon_star);
            presenter.updateAccount(false);
        } else {
            favoriteImage.setImageResource(R.drawable.icon_star_selected);
            presenter.updateAccount(true);
        }
    }

    //Method which processing click on show password btn
    private void onShowPasswordButtonClick() {
        if (!x) {
            showPasswordBtn.setImageResource(R.drawable.icon_eye);
            password.setTransformationMethod(null);
            x = true;
        } else {
            showPasswordBtn.setImageResource(R.drawable.icon_hide);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            x = false;
        }
    }

    //Method which processing click on copy btn
    private void onCopyButtonClick() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", password.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Скопійовано в буфер обміну", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_password_edit_btn:
                ((MainActivity)getActivity()).showAddPasswordFragment(id, "DetailPassword");
                break;
            case R.id.detail_password_favorite_image:
                onFavoriteButtonClick();
                break;
            case R.id.detail_password_show_password_btn:
                onShowPasswordButtonClick();
                break;
            case R.id.detail_password_copy_btn:
                onCopyButtonClick();
                break;
        }
    }




}
