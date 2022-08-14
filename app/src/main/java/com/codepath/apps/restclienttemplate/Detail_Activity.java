package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Parcel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter_1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setTitle(" ");

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Nom = findViewById(R.id.Nom);
        userName = findViewById(R.id.userName);
        Description = findViewById(R.id.Description);

        imageView = findViewById(R.id.imageView);
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));

        Description.setText(tweet.getBody());
        userName.setText(tweet.getUser().getScreenName());
        Nom.setText(tweet.getUser().getName());


        Glide.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(100)).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image,menu);


        return true;

    }

}