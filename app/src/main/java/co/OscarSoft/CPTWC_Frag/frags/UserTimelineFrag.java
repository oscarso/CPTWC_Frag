package co.OscarSoft.CPTWC_Frag.frags;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import co.OscarSoft.CPTWC_Frag.ProfileActivity;
import co.OscarSoft.CPTWC_Frag.TwitterApplication;
import co.OscarSoft.CPTWC_Frag.TwitterClient;
import co.OscarSoft.CPTWC_Frag.models.Tweet;

/**
 * Created by oscarso on 12/20/15.
 */
public class UserTimelineFrag extends TweetsListFrag {
    private TwitterClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    public static UserTimelineFrag newInstance(final String screenName) {
        UserTimelineFrag userTimelineFrag = new UserTimelineFrag();
        Bundle args = new Bundle();
        Log.d("DEBUG", "UserTimelineFrag: newInstance - screenName=" + screenName);
        args.putString(ProfileActivity.SCREEN_NAME, screenName);
        userTimelineFrag.setArguments(args);
        return userTimelineFrag;
    }

    private void populateTimeline() {
        final String screenName = getArguments().getString(ProfileActivity.SCREEN_NAME);
        Log.d("DEBUG", "UserTimelineFrag: populateTimeline - screenName=" + screenName);
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
                //Log.d("DEBUG", arrAdapterTweet.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR", "UserTimelineFrag: populateTimeline failed");
            }
        });
    }
}
