package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pllug.course.ivankiv.passmanager.R;
import com.pllug.course.ivankiv.passmanager.data.model.Account;
import com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.PasswordListPresenter;


import java.util.List;

public class PasswordListAdapter extends RecyclerView.Adapter<PasswordListAdapter.ViewHolder> {

    private Context mContext;
    private List<Account> accounts;
    private PasswordListPresenter presenter;

    public PasswordListAdapter(Context mContext, List<Account> accounts, PasswordListPresenter presenter) {
        this.mContext = mContext;
        this.accounts = accounts;
        this.presenter = presenter;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Account account = accounts.get(position);

        if (holder.categoryImage != null) {
            Glide.with(mContext)
                    .load(presenter.getImageCategoryId(account.getCategory()))
                    .into(holder.categoryImage);
        }

        if (holder.title != null) {
            holder.title.setText(account.getTitle());
        }

        if (holder.username != null) {
            holder.username.setText(account.getUsername());
        }


        if (holder.favoriteBtn != null) {
            if (account.isFavorite()) {
                holder.favoriteBtn.setImageResource(R.drawable.icon_star_selected);
            } else {
                holder.favoriteBtn.setImageResource(R.drawable.icon_star);
            }

           holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (account.isFavorite()) {
                       holder.favoriteBtn.setImageResource(R.drawable.icon_star);
                       presenter.updateFavorite(account, false);
                   } else {
                       holder.favoriteBtn.setImageResource(R.drawable.icon_star_selected);
                       presenter.updateFavorite(account, true);
                   }
               }
           });
        }

        if (holder.container != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.sendAccountIdToFragment(account.getId());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void filterList(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        accounts.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView title, username;
        private ImageButton favoriteBtn;
        private FrameLayout container;
        public RelativeLayout foregroundView, backgroundView;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.item_password_list_category_image);
            title = itemView.findViewById(R.id.item_password_list_title);
            username = itemView.findViewById(R.id.item_password_list_username);
            favoriteBtn = itemView.findViewById(R.id.item_password_list_favorite_btn);
            backgroundView = itemView.findViewById(R.id.item_password_list_background_view);
            foregroundView =  itemView.findViewById(R.id.item_password_list_view_foreground);
            container =  itemView.findViewById(R.id.item_password_list_container);
        }
    }
}
