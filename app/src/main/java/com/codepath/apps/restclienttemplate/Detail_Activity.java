package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class Detail_Activity extends AppCompatActivity {
    public TextView Nom;
    public TextView Description;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Nom = findViewById(R.id.Nom);
        Description = findViewById(R.id.Description);
        imageView = findViewById(R.id.imageView);
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));

        Description.setText(tweet.getBody());
        Nom.setText(tweet.getUser().getName());

        Glide.with(this).load(tweet.user.profileImageUrl).into(imageView);
    }

}