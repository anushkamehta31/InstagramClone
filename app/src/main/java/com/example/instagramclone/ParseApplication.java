package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("0ncjgia42N8wJiTSbd1nJx9oTS5xSIXv11monEdA")
                .clientKey("M8R2OJnGrYHP3sM0t6XwX6sax6pmSYJRnnNhsIfK")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
