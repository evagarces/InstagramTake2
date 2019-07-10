package com.example.instagramtake2;

import android.app.Application;

import com.example.instagramtake2.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("egg")
                .clientKey("sassquatch")
                .server("http://eva-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

    }
}
