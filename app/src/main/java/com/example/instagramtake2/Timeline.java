package com.example.instagramtake2;

//
//public class Timeline extends AppCompatActivity {
//
//    TimelineAdapter timelineAdapter;
//    private ParseUser currentUser;
//    private SwipeRefreshLayout swipeContainer;
//    ArrayList<Post> posts;
//    RecyclerView rvPosts;
//    boolean mFirstLoad = true;
//    private ImageButton logoutBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.timeline);
//
//        logoutBtn = findViewById(R.id.logoutBtn);
//        currentUser = ParseUser.getCurrentUser();
//        // find the RecyclerView
//        rvPosts = (RecyclerView) findViewById(R.id.rvItems);
//        // init the arraylist  (data source)
//        posts = new ArrayList<>();
//        // construct the adapter from this datasource
//        timelineAdapter = new TimelineAdapter(posts);
//        // RecyclerView setup (layout manager, use adapter)
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rvPosts.setLayoutManager(linearLayoutManager);
//        // Lookup the swipe container view
//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        // Setup refresh listener which triggers new data loading
//
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeContainer.setRefreshing(false);
//                fetchTimelineAsync(0);
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//
//        // set the adapter
//        rvPosts.setAdapter(timelineAdapter);
//        Log.d("Timeline", "adapter set successfully");
//        loadPosts();
//
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentUser != null) {
//                    ParseUser.logOut();
//                    currentUser = ParseUser.getCurrentUser(); // this will now be null
//                    final Intent intent = new Intent(Timeline.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    final Intent intent = new Intent(Timeline.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
//    }
//
//    // Query messages from Parse so we can load them into the chat adapter
//    void loadPosts() {
//        // Construct query to execute
//        Post.Query query = new Post.Query();
//        // Configure limit and sort order
//        query.getTop().withUser();
//
//        // get the latest 50 messages, order will show up newest to oldest of this group
//        query.orderByDescending("createdAt");
//        // Execute query to fetch all messages from Parse asynchronously
//        // This is equivalent to a SELECT query with SQL
//        query.findInBackground(new FindCallback<Post>() {
//            public void done(List<Post> messages, ParseException e) {
//                if (e == null) {
//                    posts.clear();
//                    posts.addAll(messages);
//                    timelineAdapter.notifyDataSetChanged(); // update adapter
//                    // Scroll to the bottom of the list on initial load
//                    if (mFirstLoad) {
//                        rvPosts.scrollToPosition(0);
//                        mFirstLoad = false;
//                    }
//                } else {
//                    Log.e("message", "Error Loading Messages" + e);
//                }
//            }
//        });
//    }
//
//    public void fetchTimelineAsync(int page) {
//        timelineAdapter.clear();
//        loadPosts();
//        swipeContainer.setRefreshing(false);
//    }
//}