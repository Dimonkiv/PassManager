package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.data.model.Account;
import com.pllug.course.ivankiv.passmanager.ui.main.MainActivity;
import com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.adapter.PasswordListAdapter;
import com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.helper.RecyclerItemTouchHelper;
import com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.helper.RecyclerItemTouchHelperListener;

import java.util.ArrayList;
import java.util.List;


public class PasswordListFragment extends Fragment implements PasswordListContract.View,
        CompoundButton.OnCheckedChangeListener, RecyclerItemTouchHelperListener {
    private View root;
    private PasswordListPresenter presenter;
    private PasswordListAdapter adapter;
    private List<Account> accounts;


    //View
    private Toolbar toolbar;
    private AppCompatCheckBox personalCheckB, socialCheckB, financeCheckB, workCheckB,
            othersCheckB, favoritesCheckB;
    private RecyclerView recycler;
    private FloatingActionButton fab;
    private LinearLayout categoryAlert, favoriteAlert, noItemAlert;
    private MaterialSearchView searchView;
    private View drawer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_password_list, container, false);

        initView();
        initToolbar();
        initPresenter();
        initAdapter();
        initCheckedChangeListener();
        initListener();
        initSearchVew();
        showAllAccount();

        return root;
    }

    //Initialization View
    private void initView() {
        toolbar = root.findViewById(R.id.password_list_toolbar);
        //AppCompactCheckBox
        personalCheckB = root.findViewById(R.id.password_list_personal_ch_b);
        socialCheckB = root.findViewById(R.id.password_list_social_ch_b);
        financeCheckB = root.findViewById(R.id.password_list_finance_ch_b);
        workCheckB = root.findViewById(R.id.password_list_work_ch_b);
        othersCheckB = root.findViewById(R.id.password_list_other_ch_b);
        favoritesCheckB = root.findViewById(R.id.password_list_favorite_ch_b);
        //recycler view
        recycler =  root.findViewById(R.id.password_list_recycler);
        //floating action button
        fab = root.findViewById(R.id.password_list_fab);
        //linear layout
        categoryAlert = root.findViewById(R.id.password_list_category_alert);
        favoriteAlert = root.findViewById(R.id.password_list_favorite_alert);
        noItemAlert = root.findViewById(R.id.password_list_no_item_alert);
        searchView = root.findViewById(R.id.password_list_search_view);

    }

    //Initialization toolbar
    private void initToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Список аккаунтів");
        setHasOptionsMenu(true);
    }

    //Initialization presenter
    private void initPresenter() {
        presenter = new PasswordListPresenter(this, getActivity());
    }

    //Initialization adapter
    private void initAdapter() {
        accounts = new ArrayList<>();
        adapter = new PasswordListAdapter(getActivity(), accounts, presenter);
        recycler.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler);
    }

    //Initialization listener
    private void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showAddPasswordFragment();
            }
        });
    }

    //Initialization Material Search View
    private void initSearchVew() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                showAllAccount();
            }
        });
    }

    //Initialization change listener listener
    private void initCheckedChangeListener() {
        personalCheckB.setOnCheckedChangeListener(this);
        socialCheckB.setOnCheckedChangeListener(this);
        financeCheckB.setOnCheckedChangeListener(this);
        workCheckB.setOnCheckedChangeListener(this);
        othersCheckB.setOnCheckedChangeListener(this);
        favoritesCheckB.setOnCheckedChangeListener(this);


    }

    //Method, which checking coincidence between entered text and name in contact list
    private void filter(String text) {
        List<Account> filteredAccounts = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredAccounts.add(account);
            }
        }

        adapter.filterList(filteredAccounts);
    }

    //Method which show all account
    private void showAllAccount() {
        accounts.clear();
        accounts.addAll(presenter.getAccounts());
        if (accounts.size() == 0) {
            noItemAlert.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            categoryAlert.setVisibility(View.GONE);
            favoriteAlert.setVisibility(View.GONE);
        } else {
            noItemAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            categoryAlert.setVisibility(View.GONE);
            favoriteAlert.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    //Method which doing, if personal Radio Button
    //was checked
    void onPersonalChecked() {
        socialCheckB.setChecked(false);
        financeCheckB.setChecked(false);
        workCheckB.setChecked(false);
        othersCheckB.setChecked(false);
        favoritesCheckB.setChecked(false);
        accounts.clear();
        accounts.addAll(presenter.getAccountsByCategory("personal"));
        if (accounts.size() == 0) {
            categoryAlert.setVisibility(View.VISIBLE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.GONE);
        } else {
            categoryAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    //Method which doing, if social Radio Button
    //was checked
    void onSocialChecked() {
        personalCheckB.setChecked(false);
        financeCheckB.setChecked(false);
        workCheckB.setChecked(false);
        othersCheckB.setChecked(false);
        favoritesCheckB.setChecked(false);
        accounts.clear();
        accounts.addAll(presenter.getAccountsByCategory("social"));
        if (accounts.size() == 0) {
            categoryAlert.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
        } else {
            categoryAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    //Method which doing, if Finance Radio Button
    //was checked
    void onFinanceChecked() {
        socialCheckB.setChecked(false);
        personalCheckB.setChecked(false);
        workCheckB.setChecked(false);
        othersCheckB.setChecked(false);
        favoritesCheckB.setChecked(false);
        accounts.clear();
        accounts.addAll(presenter.getAccountsByCategory("finance"));
        if (accounts.size() == 0) {
            categoryAlert.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
        } else {
            categoryAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    //Method which doing, if Work Radio Button
    //was checked
    void onWorkChecked() {
        socialCheckB.setChecked(false);
        financeCheckB.setChecked(false);
        personalCheckB.setChecked(false);
        othersCheckB.setChecked(false);
        favoritesCheckB.setChecked(false);
        accounts.clear();
        accounts.addAll(presenter.getAccountsByCategory("work"));
        if (accounts.size() == 0) {
            categoryAlert.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
        } else {
            categoryAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    //Method which doing, if Other Radio Button
    //was checked
    void onOtherChecked() {
        socialCheckB.setChecked(false);
        financeCheckB.setChecked(false);
        workCheckB.setChecked(false);
        personalCheckB.setChecked(false);
        favoritesCheckB.setChecked(false);
        accounts.clear();
        accounts.addAll(presenter.getAccountsByCategory("other"));
        if (accounts.size() == 0) {
            categoryAlert.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
        } else {
            categoryAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            favoriteAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    //Method which doing, if Favorite Radio Button
    //was checked
    void onFavoriteChecked() {
        socialCheckB.setChecked(false);
        financeCheckB.setChecked(false);
        workCheckB.setChecked(false);
        othersCheckB.setChecked(false);
        personalCheckB.setChecked(false);
        accounts.clear();
        accounts.addAll(presenter.getFavorites());
        if (accounts.size() == 0) {
            favoriteAlert.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
        } else {
            favoriteAlert.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            noItemAlert.setVisibility(View.GONE);
            noItemAlert.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.password_list_toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.password_list_toolbar_search);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.isChecked()) {
            switch (compoundButton.getId()) {
                case R.id.password_list_personal_ch_b:
                    onPersonalChecked();
                    break;
                case R.id.password_list_social_ch_b:
                    onSocialChecked();
                    break;
                case R.id.password_list_finance_ch_b:
                    onFinanceChecked();
                    break;
                case R.id.password_list_work_ch_b:
                    onWorkChecked();
                    break;
                case R.id.password_list_other_ch_b:
                    onOtherChecked();
                    break;
                case R.id.password_list_favorite_ch_b:
                    onFavoriteChecked();
                    break;

            }
        } else {
            showAllAccount();
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PasswordListAdapter.ViewHolder) {
            String tittle = accounts.get(position).getTitle();

            final int deletePosition = viewHolder.getAdapterPosition();
            Account account = accounts.get(position);
            presenter.deleteAccount(account);
            adapter.removeItem(deletePosition);
        }
    }

    @Override
    public void showDetailContactFragment(long id) {
        ((MainActivity)getActivity()).showDetailPasswordFragment(id);
    }
}
