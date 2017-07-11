package com.example.shivani.shivani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Image_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_);
        String url = getIntent().getStringExtra("url");
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this)
                .load(url)
                .into(imageView);

    }
}
