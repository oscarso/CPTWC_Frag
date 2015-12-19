package co.OscarSoft.CPTWC_Frag.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by oscarso on 12/13/15.
 */
public class Tweet {
    private String body;
    private long uid;
    private User user;
    private String createdAt;
    private String rts;

    public static Tweet fromJSON(JSONObject jsonObj) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObj.getString("text");
            tweet.uid = jsonObj.getLong("id");
            tweet.createdAt = jsonObj.getString("created_at");
            tweet.user = User.fromJSON(jsonObj.getJSONObject("user"));
            tweet.rts = getRelativeTimeAgo(tweet.createdAt);
        } catch (JSONException je) {
            Log.e("ERROR", je.toString());
        }
        return tweet;
    }

    public String getJsonDateNow() {
            return (new Date()).toString();
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        Log.d("DEBUG", "rawJsonDate: " + rawJsonDate);
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> arrTweet = new ArrayList<Tweet>();
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(jsonObj);
                if (tweet != null) {
                    arrTweet.add(tweet);
                }
            } catch (JSONException je) {
                Log.e("ERROR", je.toString());
                continue;
            }
        }
        return arrTweet;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getRts() {
        return rts;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        if (createdAt != null) {
            this.createdAt = createdAt;
        } else {
            this.createdAt = getJsonDateNow();
        }
    }

    public void setRts(String rts) {
        if (rts != null) {
            this.rts = rts;
        } else {
            this.rts = Tweet.getRelativeTimeAgo(this.createdAt);
        }
    }
}
