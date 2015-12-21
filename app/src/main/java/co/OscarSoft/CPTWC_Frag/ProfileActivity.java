package co.OscarSoft.CPTWC_Frag;

//import android.app.FragmentTransaction;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import co.OscarSoft.CPTWC_Frag.frags.UserTimelineFrag;
import co.OscarSoft.CPTWC_Frag.models.User;


public class ProfileActivity extends AppCompatActivity {
    public final static String SCREEN_NAME = "screen_name";
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(myToolbar);

        client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                getSupportActionBar().setTitle(user.getScreenName());
            }
        });

        final String screenName = getIntent().getStringExtra(SCREEN_NAME);
        Log.d("DEBUG", "ProfileActivity: screenName=" + screenName);
        if (savedInstanceState == null) {
            UserTimelineFrag userTimelineFrag = UserTimelineFrag.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFrag);
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
}
