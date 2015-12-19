package co.OscarSoft.CPTWC_Frag.frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.OscarSoft.CPTWC_Frag.R;
import co.OscarSoft.CPTWC_Frag.TweetsArrayAdapter;
import co.OscarSoft.CPTWC_Frag.models.Tweet;


/**
 * Created by oscarso on 12/18/15.
 */
public class TweetsListFrag extends Fragment {

    private ArrayList<Tweet> arrListTweet;
    private TweetsArrayAdapter arrAdapterTweet;
    private ListView lvTweets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_tweets_list, parent, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(arrAdapterTweet);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrListTweet = new ArrayList<>();
        arrAdapterTweet = new TweetsArrayAdapter(getActivity(), arrListTweet);
    }

    public void addAll(List<Tweet> tweets) {
        arrAdapterTweet.addAll(tweets);
    }
}
