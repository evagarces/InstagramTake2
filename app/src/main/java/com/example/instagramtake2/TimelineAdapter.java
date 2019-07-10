package com.example.instagramtake2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagramtake2.model.Post;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    // pass in the Tweets array in the constructor
    public TimelineAdapter(List<Post> posts) {
        mPosts = posts;
    }

    // clean all elements of the recycler
    public void clear() { //$$
        mPosts.clear();
        notifyDataSetChanged();
    }

    // for each row, inflate the layout and cache references into ViewHolder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.single_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data according to position
        Post post = mPosts.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getDescription());
        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivProfileImage);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvCaption;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewbyId lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
        }
    }
}

