package com.pllug.course.ivankiv.passmanager.ui.main.addpassword;

import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.ui.main.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class AddPasswordFragment extends Fragment implements AddPasswordContract.View{
    private View root;
    private AddPasswordPresenter presenter;
    private SharedPreferences sPref;
    private String nameF = "";
    private long id;

    //View
    private Toolbar toolbar;
    private EditText title, username, password, reenterPassword, email,
            website, notes;
    private ImageButton generateBtn;
    private Spinner category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_password, container, false);

        initView();
        initCategorySpinner();
        initPresenter();
        initToolbar();
        initListener();
        initEditTextWatcher();
        initSpinnerCategoryListener();
        initSharedPreferences();
        setPasswordField();
        return root;
    }

    //InitializationView
    private void initView() {
        toolbar = root.findViewById(R.id.add_password_toolbar);
        //edit text
        title = root.findViewById(R.id.add_password_title);
        username = root.findViewById(R.id.add_password_username);
        password = root.findViewById(R.id.add_password_password);
        reenterPassword = root.findViewById(R.id.add_password_reenter_password);
        email = root.findViewById(R.id.add_password_email);
        website = root.findViewById(R.id.add_password_website);
        notes = root.findViewById(R.id.add_password_notes);
        //image button
        generateBtn = root.findViewById(R.id.add_password_generate_btn);
        //spinner
        category = root.findViewById(R.id.add_password_category);
    }

    //Initialization category spinner
    private void initCategorySpinner() {
        String categoryArr[] = new String[]{"personal", "social", "finance", "work", "other"};

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, categoryArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(adapter);
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new AddPasswordPresenter(this, getActivity());
    }

    //Initialization toolbar
    private void initToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Додати логін");
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

    //Initialization listener
    private void initListener() {
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToSp();
                ((MainActivity)getActivity()).showGeneratePasswordFragment();
            }
        });
    }

    //Initialization onItemSelectedListener for category spinner
    private void initSpinnerCategoryListener() {
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setCategory(category.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                presenter.setCategory("personal");
            }
        });
    }

    //Method which get password from GeneratePasswordFragment
    //and set it to password field
    private void setPasswordField() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.getString("nameF") != null && bundle.getString("nameF").equals("generatePassword")) {
                loadDataFromSP();

                if (bundle.getString("password") != null && !bundle.getString("password").isEmpty()) {
                    password.setText(bundle.getString("password"));
                    reenterPassword.setText(bundle.getString("password"));
                    presenter.loadAccount(id);
                }
            } else if (bundle.getString("nameF") != null && bundle.getString("nameF").equals("DetailPassword")) {
                nameF = bundle.getString("nameF");
                id = bundle.getLong("id");
                presenter.loadAccount(id);
                getDataFromPresenter();
            }
        }
    }

    //Initialization text watcher in edit text
    private void initEditTextWatcher() {
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        reenterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setReenterPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        website.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setWebsite(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setNote(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //Initialization shared preferences
    private void initSharedPreferences() {
        sPref = ((MainActivity)getActivity()).getPreferences(MODE_PRIVATE);
    }

    //Method which load data from shared preference
    private void loadDataFromSP() {
        if (sPref.getString("title","") != null || !sPref.getString("title","").isEmpty()) {
            title.setText(sPref.getString("title",""));
        }

        if (sPref.getString("username","") != null || !sPref.getString("username","").isEmpty()) {
            username.setText(sPref.getString("username",""));
        }

        if (sPref.getString("email","") != null || !sPref.getString("email","").isEmpty()) {
            email.setText(sPref.getString("email",""));
        }

        if (sPref.getString("website","") != null || !sPref.getString("website","").isEmpty()) {
            website.setText(sPref.getString("website",""));
        }

        if (sPref.getString("notes","") != null || !sPref.getString("notes","").isEmpty()) {
            notes.setText(sPref.getString("notes",""));
        }

        if (sPref.getString("category","") != null || !sPref.getString("category","").isEmpty()) {
            category.setSelection(presenter.getCategoryId(sPref.getString("category","")));
        }

        id = sPref.getLong("id", 0);
    }

    //Method which load data from presenter
    private void getDataFromPresenter() {
        if (presenter.getCategory() != null) {
            category.setSelection(presenter.getCategoryId(presenter.getCategory()));
        }

        if (presenter.getTitle() != null) {
            title.setText(presenter.getTitle());
        }

        if (presenter.getUsername() != null) {
            username.setText(presenter.getUsername());
        }

        if (presenter.getPassword() != null) {
            password.setText(presenter.getPassword());
        }

        if (presenter.getEmail() != null) {
            email.setText(presenter.getEmail());
        }

        if (presenter.getWebsite() != null) {
            website.setText(presenter.getWebsite());
        }

        if (presenter.getNote() != null) {
            notes.setText(presenter.getNote());
        }
    }

    //Method, which save entered data
    //to shared preferences
    public void saveDataToSp() {
        SharedPreferences.Editor edit = sPref.edit();
        edit.putString("title", title.getText().toString());
        edit.putString("username", username.getText().toString());
        edit.putString("email", email.getText().toString());
        edit.putString("website", website.getText().toString());
        edit.putString("notes", notes.getText().toString());
        edit.putString("category", category.getSelectedItem().toString());
        edit.putLong("id", id);
        edit.apply();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sign_up_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_up_toolbar_send) {

            if (presenter.getAccount() != null) {
                if (presenter.updateAccount()) {
                    ((MainActivity) getActivity()).showDetailPasswordFragment(id);
                    Toast.makeText(getActivity(), "Дані оновлено", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (presenter.insertDataInDB()) {
                    ((MainActivity) getActivity()).showPasswordListFragment();
                    Toast.makeText(getActivity(), "Дані вставлено в бд", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return true;
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
