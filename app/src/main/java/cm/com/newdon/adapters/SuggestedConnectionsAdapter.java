package cm.com.newdon.adapters;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cm.com.newdon.R;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
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
            ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonData.getInstance().setSelectedUser(user);

                    CommonData.bottomBarActivity.onUserSelected(user.getId());
                }
            });
        }
        TextView        tvUserMail     = (TextView) layout.findViewById(R.id.tvUserMail);
        String realName = user.getRealName();
        tvUserMail.setText(realName);
        TextView  tvAction     = (TextView) layout.findViewById(R.id.tvFollowers);
        String    name = user.getUserName();
        tvAction.setText(name);
        if (searchMode) {
            tvAction.setTextColor(context.getResources().getColor(R.color.grey));
            tvAction.setVisibility(View.VISIBLE);
            SpannableString spannableString1 = new SpannableString(name);
            int indexOf = name.indexOf(searchStr);
            if (indexOf != -1) {
                spannableString1.setSpan(new ForegroundColorSpan(0xff5d9bff), indexOf, indexOf + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                while (indexOf >= 0) {
                    indexOf = name.indexOf(searchStr, indexOf + 1);
                    if (indexOf != -1) {
                        spannableString1.setSpan(new ForegroundColorSpan(0xff5d9bff), indexOf, indexOf + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                tvAction.setText(spannableString1);
            }


            SpannableString spannableString = new SpannableString(realName);
            indexOf = realName.indexOf(searchStr);
            if (indexOf != -1) {
                spannableString.setSpan(new ForegroundColorSpan(0xff5d9bff), indexOf, indexOf + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                while (indexOf >= 0) {
                    indexOf = realName.indexOf(searchStr, indexOf + 1);
                    if (indexOf != -1) {
                        spannableString.setSpan(new ForegroundColorSpan(0xff5d9bff), indexOf, indexOf + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                tvUserMail.setText(spannableString);
            }
        }
        else {
            TextView tvFollowers = (TextView) layout.findViewById(R.id.tvFollowers);
            tvFollowers.setText("" + user.getFollowersCount());
        }
        final ImageView       ivNotification = (ImageView) layout.findViewById(R.id.imFollow);
        if (user.isFollowed()) {
            ivNotification.setImageResource(R.drawable.following_btn);

        }
        else {
            ivNotification.setImageResource(R.drawable.follow_btn);
        }

        ivNotification.setOnClickListener(new FollowListener(ivNotification, user, context));

        return layout;
    }
}
