package com.codepath.apps.restclienttemplate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.autofill.SaveInfo;
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
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.prefs.Preferences;

import okhttp3.Headers;
// ...

public class EditNameDialogFragment extends DialogFragment {

    private EditText mEditText;
    public static final String TAG = "EditNameDialogFragment";
    public static final int MAX_TWEET_LENGHT = 140;
    public static final String Key = "BROUILLONS";
    Button btnTweet;
    TextView close;
    TwitterClient client;
    Context context;
    TextView Nom;
    TextView userName;
    ImageView imagefr;


    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_name, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = view.findViewById(R.id.etComposeFrag);
        Nom = view.findViewById(R.id.Nom);
        userName = view.findViewById(R.id.userName);
        close = view.findViewById(R.id.close);
        btnTweet = view.findViewById(R.id.btnTweet);
        imagefr = view.findViewById(R.id.imagefr);
        client = TwitterApp.getRestClient(getContext());



        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        getDialog().getWindow().setLayout(700,900);


        Bundle bundle = getArguments();
        User utilisateur = Parcels.unwrap(bundle.getParcelable("profile"));
        Nom.setText(utilisateur.getName());
        userName.setText(utilisateur.getScreenName());
        Glide.with(getContext()).load(utilisateur.getProfileImageUrl()).into(imagefr);


        //draft
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String draft = preferences.getString(Key,"");

        if (draft.isEmpty()){
            mEditText.setText(draft);
        }


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = mEditText.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(context, "Sorry, your tweet cannot be Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGHT) {
                    Toast.makeText(context, "Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }

                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"onSuccess to publish tweet");
                        try {
                            Tweet tweet=Tweet.fromJson(json.jsonObject);
                            Log.i(TAG," published tweet says : " + tweet);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet", throwable);
                    }
                });
                dismiss();

            }
        });
    }
    public interface EditListTweets{
        void onFinishEditDialog(Tweet tweet);
    }
    public void open(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Save Draft");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Save();
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();

            }
        });
       AlertDialog dialog = builder.create();
       dialog.show();
    }
    public void Save(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username",mEditText.getText().toString());
        editor.commit();
        dismiss();
    }
}