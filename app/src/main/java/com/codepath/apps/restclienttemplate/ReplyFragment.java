package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;
// ...

public class ReplyFragment extends DialogFragment {

    private EditText mEditText;
    public static final String TAG = "ReplyFragment";
    public static final int MAX_TWEET_LENGHT = 140;
    Button btnReply;
    TextView close2;
    TwitterClient client;
    Context context;
    TextInputLayout compter2;
    TextView Nom2;
    TextView userName2;
    ImageView imagefr2;


    public ReplyFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ReplyFragment newInstance(String title) {
        ReplyFragment frag = new ReplyFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reply, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        Tweet tweet = Parcels.unwrap(bundle.getParcelable("tweets"));
        User utilisateur = Parcels.unwrap(bundle.getParcelable("profile"));


        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etComposeFrag2);
        Nom2 = view.findViewById(R.id.Nom2);
        userName2 = view.findViewById(R.id.userName2);
        btnReply = view.findViewById(R.id.btnReply);
        close2 = view.findViewById(R.id.close2);

        Nom2.setText(utilisateur.getName());
        userName2.setText(utilisateur.getScreenName());


        // Fetch arguments from bundle and set title

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
//        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        getDialog().getWindow().setLayout(700,900);

        mEditText.setText(tweet.user.getScreenName()+"  ");


        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = mEditText.getText().toString();
                if(tweetContent.isEmpty()){
                    Toast.makeText(context, "Sorry, your tweet cannot be Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length()> MAX_TWEET_LENGHT){
                    Toast.makeText(context, "Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"on Succes to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG,"publish tweet says:"+tweet.body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onFailure to publish tweet",throwable);
                    }
                });
            }
        });


    }

}



