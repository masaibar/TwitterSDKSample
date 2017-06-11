package com.masaibar.twittersdksample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TwitterLoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }

    private void setupViews() {
        setupLoginButton();
        setupTweetButton();
        setLogoutButton();
        setCheckStatusButton();
    }

    private void setupLoginButton() {
        mLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                long userId = session.getUserId();
                String userName = session.getUserName();

                Log.d("!!!", String.format("userId = %s, userName = %s", userId, userName));
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("!!!", String.format("failure, %s", exception.getMessage()));
            }
        });
    }

    private void setupTweetButton() {
        findViewById(R.id.button_tweet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterWrapper twitterWrapper = new TwitterWrapper(getApplicationContext());

                String text = String.format(
                        "test tweet (%s)",
                        DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString()
                );
                twitterWrapper.tweet(text, new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        Log.d("!!!", "Tweet succeeded");
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.d("!!!", String.format("Tweet failed, %s", exception.getMessage()));
                    }
                });
            }
        });
    }

    private void setLogoutButton() {
        findViewById(R.id.button_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TwitterWrapper(getApplicationContext()).logout();
            }
        });
    }

    private void setCheckStatusButton() {
        final Context context = getApplicationContext();
        findViewById(R.id.button_check_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        context,
                        new TwitterWrapper(context).isLoggedIn() ? "logged in" : "logged out",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
