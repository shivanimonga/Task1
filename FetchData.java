package com.example.shivani.shivani;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

/**
 * Created by shivani on 6/7/17.
 */

class FetchData extends AsyncTask<String, String, ArrayList<Users>> implements UserAdapter.ItemClickListener {
    private Context mContext;
    private ProgressDialog dialog;
    RecyclerView rvusers;
    Gson gson;
    GsonBuilder gsonBuilder;
    FavouriteHelper favHelper;
    DBHelper mDbHelper;
    //SQLiteDatabase db = mDbHelper.getWritableDatabase();
    ItemTouchHelper.Callback callback;

    FetchData(Context context, RecyclerView recyclerView) {
        mContext = context;
        rvusers = recyclerView;
        dialog = new ProgressDialog(mContext);
    }

    protected void onPreExecute() {
        super.onPreExecute();
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        dialog.setCancelable(false);
        dialog.setMessage("Fetching data,please wait");
        dialog.show();
        //Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        Log.d("in", "onPreExecute: ");
    }

    @Override
    protected ArrayList<Users> doInBackground(String... strings) {

        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://jsonplaceholder.typicode.com/photos";
        String jsonStr = sh.makeServiceCall(url);
        Log.d("in", "doInBackground: ");

        ArrayList<Users> userList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonStr.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            Users t = null;
            try {
                t = gson.fromJson(jsonArray.getJSONObject(i).toString(), Users.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            userList.add(t);
        }

        mDbHelper = new DBHelper(mContext);
        mDbHelper.addRecords(userList);
        return userList;
    }

    @Override
    public void onPostExecute(ArrayList<Users> userses) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        ItemTouchHelper touchHelper;

        UserAdapter adapter = new UserAdapter(mContext, userses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvusers.setLayoutManager(layoutManager);
        rvusers.setAdapter(adapter);
        callback = new ItemSwipeHelper(adapter, userses);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvusers);
        adapter.setClickListener(this);

    }


    @Override
    public void onClick(View view, int position, Users user) {
        Log.d("on item click", String.valueOf(position));

        Intent i = new Intent(view.getContext(), Image_Activity.class);
        i.putExtra("url", user.getUrl());
        view.getContext().startActivity(i);
    }

    @Override
    public void deleteItem(Users user) {
        Log.d("delete", String.valueOf(user.getId()));

        mDbHelper = new DBHelper(mContext);
        mDbHelper.deleteItem(user);
    }

    @Override
    public void favourite(Users user) {
        favHelper=new FavouriteHelper(mContext);
        favHelper.addRecord(user);
    }


}



