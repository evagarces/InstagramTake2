package com.example.instagramtake2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.instagramtake2.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Timeline extends AppCompatActivity {

    TimelineAdapter timelineAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    boolean mFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        // find the RecyclerView
        rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        // init the arraylist  (data source)
        posts = new ArrayList<>();
        // construct the adapter from this datasource
        timelineAdapter = new TimelineAdapter(posts);
        // RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(linearLayoutManager);

        // set the adapter
        rvPosts.setAdapter(timelineAdapter);
        Log.d("Timeline", "adapter set successfully");
        loadPosts();
    }

    // Query messages from Parse so we can load them into the chat adapter
    void loadPosts() {
        // Construct query to execute
        Post.Query query = new Post.Query();
        // Configure limit and sort order
        query.getTop().withUser();

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
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

