package com.example.shivani.shivani;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shivani on 8/7/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    interface ItemClickListener {

        void onClick(View view, int position, Users users);

        void deleteItem(Users user);

        void favourite(Users user);
    }

    private ItemClickListener clickListener;

    public void removeItem(int position, ArrayList<Users> userData) {
        Log.d("RecyclerViewAdapter", "in removeItem");
        if (clickListener != null && userData != null && userData.size() > 0) {
            Users user = userData.get(position);
            clickListener.deleteItem(user);
            userData.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    public void addToFavourite(int position, ArrayList<Users> userData) {
        Log.d("RecyclerViewAdapter", "in addToFavourite");
        if (clickListener != null && userData != null && userData.size() > 0) {
            Users user = userData.get(position);
            clickListener.favourite(user);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageView;
        public TextView textView;
        public ImageView thumbimage;
        Users user;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbimage);
            textView = itemView.findViewById(R.id.title);
            thumbimage = itemView.findViewById(R.id.image);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition(), user);
        }

        public void setuser(Users user) {
            this.user = user;
        }
    }


    private ArrayList<Users> mUsers;
    private Context mContext;

    public UserAdapter(Context context, ArrayList<Users> Users) {
        mUsers = Users;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }


    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        viewHolder.setIsRecyclable(false);
        Users users = mUsers.get(position);
        // Set item views based on your views and data model
        viewHolder.setuser(users);
        viewHolder.textView.setText(users.getTitle());
        Picasso.with(mContext)
                .load(users.getthumburl())
                .into(viewHolder.imageView);


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
