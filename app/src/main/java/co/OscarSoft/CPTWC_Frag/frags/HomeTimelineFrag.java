package co.OscarSoft.CPTWC_Frag.frags;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import co.OscarSoft.CPTWC_Frag.TwitterApplication;
import co.OscarSoft.CPTWC_Frag.TwitterClient;
import co.OscarSoft.CPTWC_Frag.models.Tweet;

/**
 * Created by oscarso on 12/18/15.
 */
public class HomeTimelineFrag extends TweetsListFrag {
    private TwitterClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
                //Log.d("DEBUG", arrAdapterTweet.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR", "populateTimeline Fail 2");
            }
        });
    }
}
