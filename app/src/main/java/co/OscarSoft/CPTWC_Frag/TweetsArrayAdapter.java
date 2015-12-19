package co.OscarSoft.CPTWC_Frag;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.OscarSoft.CPTWC_Frag.models.Tweet;

/**
 * Created by oscarso on 12/13/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private static class ViewHolderUser {
        ImageView profileImage;
        TextView userName;
    }

    // View lookup cache
    private static class ViewHolder {
        ViewHolderUser vhUser;
        TextView rts; //relative timestamp
        TextView body;
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            if (viewHolder.vhUser == null) {
                viewHolder.vhUser = new ViewHolderUser();
                viewHolder.vhUser.profileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
                viewHolder.vhUser.profileImage.setImageResource(android.R.color.transparent);
                viewHolder.vhUser.userName = (TextView) convertView.findViewById(R.id.tvUserName);
            } else {
                Log.d("DEBUG", "viewHolder.vhUser != null");
            }
            viewHolder.body = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.rts = (TextView) convertView.findViewById(R.id.tvRTS);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.vhUser.profileImage);
        viewHolder.vhUser.userName.setText(tweet.getUser().getScreenName());
        viewHolder.body.setText(tweet.getBody());
        viewHolder.rts.setText(tweet.getRts());

        return convertView;
    }
}
