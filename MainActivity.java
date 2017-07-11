package com.example.shivani.shivani;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import static com.example.shivani.shivani.DBHelper.DATABASE_NAME;

public class MainActivity extends AppCompatActivity implements UserAdapter.ItemClickListener {

    RecyclerView recyclerView;
    ItemTouchHelper.Callback callback;
    ItemTouchHelper touchHelper;
    FavouriteHelper favHelper;
    DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    /***
     * Function to fetch records
     *
     * Records fetched from database(if exists)
     * otherwise FetchData class called
     */
    public void onFetch(View view) {

        String mydb = DATABASE_NAME;
        File dbFile = MainActivity.this.getDatabasePath(mydb);

        if (dbFile.exists()) {
            DBHelper mDbHelper = new DBHelper(MainActivity.this);
            ArrayList<Users> list = mDbHelper.getRecords();
            createRecyclerView(list);
            Log.d(mydb, "data base exists");

        } else {

            FetchData fetchData = new FetchData(MainActivity.this, recyclerView);
            fetchData.execute();
        }

    }

    /***
     * Function to delete all records
     */
    public void onClear(View view) {

        MainActivity.this.deleteDatabase(DATABASE_NAME);
        MainActivity.this.deleteDatabase("Favourite.db");
        recyclerView.setAdapter(null);
    }

    /***
     * Function to display records in favourites database
     * @param view
     */
    public void onFavourite(View view) {
        startActivity(new Intent(MainActivity.this, FavouriteActivity.class));

        Log.d("MainActivity", "in onFavourite()");
    }

    public void createRecyclerView(ArrayList<Users> list ) {

        //Initialize adapter
        UserAdapter adapter = new UserAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //adapter.setClickListener(this);

        callback = new ItemSwipeHelper(adapter, list);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        adapter.setClickListener(MainActivity.this);
    }


    @Override
    public void onClick(View view, int position, Users users) {

        Log.d("on item click", String.valueOf(position));

        Intent i = new Intent(view.getContext(), Image_Activity.class);
        i.putExtra("url", users.getUrl());
        view.getContext().startActivity(i);
    }


    @Override
    public void deleteItem(Users user) {

        Log.d("deleting", String.valueOf(user.getId()));
        mDbHelper = new DBHelper(MainActivity.this);
        mDbHelper.deleteItem(user);
    }

    @Override
    public void favourite(Users user) {
        favHelper = new FavouriteHelper(MainActivity.this);
        favHelper.addRecord(user);
    }
}

