package com.pllug.course.ivankiv.passmanager.ui.main.passwordlist.helper;

import android.support.v7.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener {

    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
