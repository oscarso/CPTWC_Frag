package co.OscarSoft.CPTWC_Frag.frags;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import co.OscarSoft.CPTWC_Frag.TwitterApplication;
import co.OscarSoft.CPTWC_Frag.TwitterClient;
import co.OscarSoft.CPTWC_Frag.models.Tweet;


/**
 * Created by oscarso on 12/19/15.
 */
public class MentionsTimelineFrag extends TweetsListFrag {
    private TwitterClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline() {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
                Log.d("DEBUG", "onSuccess: " + Tweet.fromJSONArray(response).toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR", "onFailure: populateTimeline Fail 2");
            }
        });
    }
}
