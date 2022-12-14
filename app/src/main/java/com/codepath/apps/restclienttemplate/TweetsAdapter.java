package com.codepath.apps.restclienttemplate;

import static com.codepath.apps.restclienttemplate.TimelineActivity.utilisateur;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {


    Context context;
    List<Tweet> tweets;

    //Pass in the context and list of tweets
    public TweetsAdapter(Context context,  List<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;

    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }



    //For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent,false);
        TweetsAdapter.ViewHolder viewHolder = new TweetsAdapter.ViewHolder(view,listener);
        return  viewHolder;
    }


    //Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);

        //Bind the tweet with view holder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {

        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    //Add a list of items
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }




    //Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        ImageView imVideo;
        TextView tvBody;
        TextView Nom;
        TextView tvScreenName;
        TextView heure;
        TextView textView2;
//
        public ViewHolder(@NonNull View itemView, final OnItemClickListener clickListener){
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfile);
            tvBody = itemView.findViewById(R.id.tvBody);
            Nom = itemView.findViewById(R.id.Nom);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            imVideo = itemView.findViewById(R.id.imVideo);
            heure = itemView.findViewById(R.id.heure);
            textView2 = itemView.findViewById(R.id.textView2);
//
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(itemView,getAdapterPosition());
                }
            });



        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            Nom.setText(tweet.user.getName());
            tvScreenName.setText(tweet.user.getScreenName());
            heure.setText(tweet.getFormattedTimestamp(tweet.createdAt));

            Glide.with(context).load(tweet.user.profileImageUrl)
                    .transform(new RoundedCorners(100)).into(ivProfileImage);

//            if(!tweet.entities.media_url.isEmpty()){
//                imVideo.setVisibility(View.VISIBLE);
//                Glide.with(context).load(tweet.entities.media_url)
//                        .transform(new RoundedCorners(40)).into(imVideo);
//            }

            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showEditDialog(Parcels.wrap(tweet));
                }
            });

        }
        private void showEditDialog(Parcelable tweet) {
            FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
            ReplyFragment editNameDialogFragment = ReplyFragment.newInstance("Some Title");

            Bundle bundle = new Bundle();
            bundle.putParcelable("tweets",tweet);
            bundle.putParcelable("profile",Parcels.wrap(utilisateur));

            editNameDialogFragment.setArguments(bundle);
            editNameDialogFragment.show(fm, "fragment_reply");
        }


    }
}
