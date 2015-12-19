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
