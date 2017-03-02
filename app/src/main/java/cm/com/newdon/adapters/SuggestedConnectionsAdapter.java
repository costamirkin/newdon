package cm.com.newdon.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cm.com.newdon.R;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notificaion listview
 */
public class SuggestedConnectionsAdapter extends BaseAdapter {

    private Context context;
    private boolean searchMode = false;
    private ArrayList<User> users;

    private String searchStr = "";

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public SuggestedConnectionsAdapter(Context context, boolean searchMode, ArrayList<User> users) {
        this.context = context;
        this.searchMode = searchMode;
        this.users = users;
    }

    @Override
    public int getCount() {

        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layout;
        if (convertView != null) {
            layout = (RelativeLayout)convertView;
        }
        else {
            layout = (RelativeLayout) View.inflate(context, R.layout.suggested_connection_item, null);
        }

        final User user = users.get(position);

        CircleImageView ivUser         = (CircleImageView) layout.findViewById(R.id.ivUser);
        Log.e("sss", user.getPictureUrl());
        if (!user.getPictureUrl().equals("")) {
            Picasso.with(context).load(user.getPictureUrl()).into(ivUser);
        }
        TextView        tvUserMail     = (TextView) layout.findViewById(R.id.tvUserMail);
        String realName = user.getRealName();
        tvUserMail.setText(realName);
        if (searchMode) {
            SpannableString spannableString = new SpannableString(realName);
            Object blueSpan = new ForegroundColorSpan(Color.BLUE);
            int indexOf = realName.indexOf(searchStr);
            if (indexOf != -1) {
                spannableString.setSpan(blueSpan, indexOf, indexOf + searchStr.length(), 0);
                tvUserMail.setText(spannableString);
            }
        }
        TextView        tvFollowers    = (TextView) layout.findViewById(R.id.tvFollowers);
        tvFollowers.setText("" + user.getFollowersCount());
        final ImageView       ivNotification = (ImageView) layout.findViewById(R.id.imFollow);
        if (user.isFollowed()) {
            ivNotification.setImageResource(R.drawable.following_btn);
            ivNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.unfollowUser(user.getId(), context);
                    ivNotification.setImageResource(R.drawable.follow_btn);
                }
            });

        }
        else {
            ivNotification.setImageResource(R.drawable.follow_btn);
            ivNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.followUser(user.getId(), context);
                    ivNotification.setImageResource(R.drawable.following_btn);
                }
            });

        }

        return layout;
    }
}
