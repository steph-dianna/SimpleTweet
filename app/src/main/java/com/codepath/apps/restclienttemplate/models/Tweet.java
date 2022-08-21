package com.codepath.apps.restclienttemplate.models;

import android.widget.TextView;

import com.codepath.apps.restclienttemplate.Entities;
import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public String body;

    public String createdAt;
    public User user;
    public long id;
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
