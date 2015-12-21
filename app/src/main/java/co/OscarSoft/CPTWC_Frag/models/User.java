package co.OscarSoft.CPTWC_Frag.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by oscarso on 12/13/15.
 */
public class User {
    private static User dataHolder = null;
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagLine;
    private int followersCount;
    private int followingsCount;


    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public static User fromJSON(JSONObject jsonObj) {
        User user;
        if (getUser() != null) {
            user = dataHolder;//use the cache copy!
        } else {
            user = new User();
            try {
                user.name = jsonObj.getString("name");
                user.uid = jsonObj.getLong("id");
                user.screenName = jsonObj.getString("screen_name");
                user.profileImageUrl = jsonObj.getString("profile_image_url");
                user.tagLine = jsonObj.getString("description");
                user.followersCount = jsonObj.getInt("followers_count");
                user.followingsCount = jsonObj.getInt("friends_count");
                dataHolder = user; //cache it for later use
            } catch (JSONException je) {
                Log.e("ERROR", je.toString());
            }
        }

        return user;
    }

    public static User getUser() {
        return dataHolder;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
