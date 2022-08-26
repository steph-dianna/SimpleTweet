package com.codepath.apps.restclienttemplate.models;

import android.widget.TextView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.Entities;
import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))

public class Tweet {

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;


    @PrimaryKey
    @ColumnInfo
    public long id;

    @ColumnInfo
    public Long userId;

    @Ignore
    public static User user;

    @Ignore
    public Entities entities;

    public Tweet(){}

    public String getBody() {
        return body;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public static String getFormattedTimestamp(String createdAt){
        return TimeFormatter.getTimeDifference(createdAt);
    }



    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {

        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");

        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.userId = user.id;
        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));

        return tweet;
    }
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException{
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0;i < jsonArray.length();i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public static String getTimeStamp(String createdAt) {
        return TimeFormatter.getTimeStamp(createdAt);
    }
}
