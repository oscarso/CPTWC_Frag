package co.OscarSoft.CPTWC_Frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import co.OscarSoft.CPTWC_Frag.frags.HomeTimelineFrag;
import co.OscarSoft.CPTWC_Frag.frags.MentionsTimelineFrag;
import co.OscarSoft.CPTWC_Frag.frags.TweetsListFrag;
import co.OscarSoft.CPTWC_Frag.models.Tweet;
import co.OscarSoft.CPTWC_Frag.models.User;


public class TimelineActivity extends AppCompatActivity {

    private static final int ACTIVITY_REQ_CODE = 1234;
    //private TweetsListFrag fragTweetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == -1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem mi) {
        return;
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
        /*
        client.postStatus(
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //super.onSuccess(statusCode, headers, response);
                        Log.d("INFO", "postNewTweet: " + response.toString());
                        //arrAdapterTweet.add(newTweet);
                        //arrAdapterTweet.setNotifyOnChange(true);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        //super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("ERROR", "postNewTweet Fail 2");
                    }
                },
                newTweet
        );*/
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFrag();
            } else if (position == 1) {
                return new MentionsTimelineFrag();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
