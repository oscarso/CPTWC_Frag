package co.OscarSoft.CPTWC_Frag;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends AppCompatActivity {

    public static final String L_NEW_TWEET = "NEW_TWEET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    public void onComposeTweet(View v) {
        EditText etNewTweet = (EditText) findViewById(R.id.edtCompose);
        String newTweet = etNewTweet.getText().toString();
        Intent retIntent = new Intent();
        retIntent.putExtra(L_NEW_TWEET, newTweet);
        setResult(Activity.RESULT_OK, retIntent);
        finish();
    }

    public void onCancel(View v) {
        Intent retIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, retIntent);
        finish();
    }
}
