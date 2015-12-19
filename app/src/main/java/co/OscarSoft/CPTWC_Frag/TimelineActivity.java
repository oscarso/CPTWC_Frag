package co.OscarSoft.CPTWC_Frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import co.OscarSoft.CPTWC_Frag.models.Tweet;
import co.OscarSoft.CPTWC_Frag.models.User;

public class TimelineActivity extends AppCompatActivity {

    private static final int ACTIVITY_REQ_CODE = 1234;
    private TwitterClient client;
    private ArrayList<Tweet> arrListTweet;
    private TweetsArrayAdapter arrAdapterTweet;
    private ListView lvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        arrListTweet = new ArrayList<>();
        arrAdapterTweet = new TweetsArrayAdapter(this, arrListTweet);
        lvTweets.setAdapter(arrAdapterTweet);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        // handle click here
        //Log.d("DEBUG", "onComposeAction");
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i, ACTIVITY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                User user;
                if (User.getUser() != null) {
                    user = User.getUser();//use cache copy
                } else {
                    user = new User();//set dummy
                    user.setUid(0);
                    user.setName("no_name_yet");
                    user.setProfileImageUrl("no_profile_yet");
                    user.setScreenName("no_screenname_yet");
                }
                Tweet newTweet = new Tweet();
                newTweet.setUid(user.getUid());
                newTweet.setUser(user);
                newTweet.setCreatedAt(null);
                newTweet.setRts(null);
                newTweet.setBody(data.getStringExtra(ComposeActivity.L_NEW_TWEET));
                postNewTweet(newTweet);
            } else {
                String doNothing = "";
            }
        }
    }

    private void postNewTweet(final Tweet newTweet) {
        //Log.d("DEBUG", "newTweet: " + newTweet.toString());
        client.postStatus(
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //super.onSuccess(statusCode, headers, response);
                        Log.d("INFO", "postNewTweet: " + response.toString());
                        arrAdapterTweet.add(newTweet);
                        arrAdapterTweet.setNotifyOnChange(true);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        //super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("ERROR", "postNewTweet Fail 1");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        //super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("ERROR", "postNewTweet Fail 2");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        //super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("ERROR", "postNewTweet Fail 3");
                    }
                },
                newTweet
        );
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //super.onSuccess(statusCode, headers, response);
                //Log.d("INFO", response.toString());
                ArrayList<Tweet> arrTweet = new ArrayList<Tweet>();
                arrAdapterTweet.addAll(Tweet.fromJSONArray(response));
                //Log.d("DEBUG", arrAdapterTweet.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("ERROR", "populateTimeline Fail 1");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR", "populateTimeline Fail 2");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR", "populateTimeline Fail 3");
            }
        });
    }
}
