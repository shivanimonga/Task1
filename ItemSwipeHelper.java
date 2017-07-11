package com.example.shivani.shivani;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

/**
 * Created by shivani on 10/7/17.
 */

public class ItemSwipeHelper extends ItemTouchHelper.Callback {

    private int direction;
    UserAdapter recyclerViewAdapter;
    ArrayList<Users> user;

    public ItemSwipeHelper(UserAdapter recyclerViewAdapter, ArrayList<Users> user) {
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.user = user;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        int position = viewHolder.getLayoutPosition();
        if (direction == ItemTouchHelper.RIGHT)
            recyclerViewAdapter.removeItem(position, user);
        else if(direction==ItemTouchHelper.LEFT)
            recyclerViewAdapter.addToFavourite(position, user);
    }
}
