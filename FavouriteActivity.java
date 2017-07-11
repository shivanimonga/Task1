package com.example.shivani.shivani;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shivani on 11/7/17.
 */

public class FavouriteActivity extends AppCompatActivity implements UserAdapter.ItemClickListener {
    RecyclerView recyclerView;
    UserAdapter recyclerViewAdapter;
    ArrayList<Users> myUserArrayList;
    FavouriteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
       recyclerView = (RecyclerView) findViewById(R.id.favouriteRecycler);
        helper = new FavouriteHelper(this);

        myUserArrayList = helper.getRecords();
        makeRecyclerView();


    }

    public void makeRecyclerView() {
        if (myUserArrayList.size() == 0) {
            Toast.makeText(this, "No Favourite User Present", Toast.LENGTH_SHORT).show();
        } else {
            recyclerViewAdapter = new UserAdapter(this, myUserArrayList);
            recyclerViewAdapter.setClickListener(this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }

    }


    @Override
    public void onClick(View view, int position, Users users) {

    }

    @Override
    public void deleteItem(Users user) {

    }

    @Override
    public void favourite(Users user) {

    }
}
