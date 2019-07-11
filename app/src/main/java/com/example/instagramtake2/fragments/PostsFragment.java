package com.example.instagramtake2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.instagramtake2.LoginActivity;
import com.example.instagramtake2.R;
import com.example.instagramtake2.TimelineAdapter;
import com.example.instagramtake2.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    TimelineAdapter timelineAdapter;
    private ParseUser currentUser;
    private SwipeRefreshLayout swipeContainer;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    boolean mFirstLoad = true;
    private ImageButton logoutBtn;

    // the onCreateView method is called when Fragment should create its View object hierarchy
    // either dynamically on via XML layout inflation

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutBtn = view.findViewById(R.id.logoutBtn);
        currentUser = ParseUser.getCurrentUser();
        // find the RecyclerView
        rvPosts = (RecyclerView) view.findViewById(R.id.rvItems);
        // init the arraylist  (data source)
        posts = new ArrayList<>();
        // construct the adapter from this datasource
        timelineAdapter = new TimelineAdapter(posts);
        // RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        // set the adapter
        rvPosts.setAdapter(timelineAdapter);
        Log.d("Timeline", "adapter set successfully");
        loadPosts();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    ParseUser.logOut();
                    currentUser = ParseUser.getCurrentUser(); // this will now be null
                    final Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    final Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    // Query messages from Parse so we can load them into the chat adapter
    protected void loadPosts() {
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

    public void fetchTimelineAsync(int page) {
        timelineAdapter.clear();
        loadPosts();
        swipeContainer.setRefreshing(false);
    }
}