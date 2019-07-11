package com.example.instagramtake2.fragments;

import android.util.Log;

import com.example.instagramtake2.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {
    @Override
    protected void loadPosts() {
        Post.Query query = new Post.Query();
        // Configure limit and sort order
        query.getTop().withUser();

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> messages, ParseException e) {
                if (e == null) {
                    posts.clear();
                    posts.addAll(messages);
                    timelineAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvPosts.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
    }
}
