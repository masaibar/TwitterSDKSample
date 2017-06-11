package com.masaibar.twittersdksample;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new TwitterWrapper(this).init();
    }
}
