package com.masaibar.twittersdksample;

import android.content.Context;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

public class TwitterWrapper {

    private Context mContext;

    public TwitterWrapper(Context context) {
        mContext = context;
    }

    public void init() {
        String consumerKey = mContext.getString(R.string.twitter_consumer_key);
        String consumerSecret = mContext.getString(R.string.twitter_consumer_secret);
        TwitterConfig config = new TwitterConfig.Builder(mContext)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(consumerKey, consumerSecret))
                .debug(true)
                .build();

        Twitter.initialize(config);
    }

    public void tweet(String text, Callback<Tweet> callback) {
        getApiClient().getStatusesService()
                .update(text, null, null, null, null, null, null, null, null)
                .enqueue(callback);
    }

    public TwitterSession getSession() {
        return TwitterCore.getInstance().getSessionManager().getActiveSession();
    }

    public TwitterApiClient getApiClient() {
        return TwitterCore.getInstance().getApiClient(getSession());
    }
}
