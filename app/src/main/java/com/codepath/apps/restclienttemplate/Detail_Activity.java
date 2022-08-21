package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class Detail_Activity extends AppCompatActivity {
    public TextView Nom;
    public TextView Description;
    public TextView userName;
    public ImageView imageView;
    public TextView heureDetail;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Detail_Activity.this,TimelineActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter_1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Tweet ");

        Nom = findViewById(R.id.Nom);
        userName = findViewById(R.id.userName);
        Description = findViewById(R.id.Description);
        heureDetail = findViewById(R.id.heureDetail);

        imageView = findViewById(R.id.imageView);
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));

        Description.setText(tweet.getBody());
        userName.setText(tweet.getUser().getScreenName());
        Nom.setText(tweet.getUser().getName());
        heureDetail.setText(tweet.getTimeStamp(tweet.createdAt));


        Glide.with(this).load(tweet.user.profileImageUrl)
                .transform(new RoundedCorners(100)).into(imageView);
    }



}