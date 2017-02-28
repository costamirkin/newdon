package cm.com.newdon.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cm.com.newdon.R;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notificaion listview
 */
public class FollowAdapter extends BaseAdapter {

    private Context context;
    private String  type;

    public FollowAdapter(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {

        return CommonData.getInstance().getFollowUsers().size();
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

        final User user = CommonData.getInstance().getFollowUsers().get(position);

        CircleImageView ivUser         = (CircleImageView) layout.findViewById(R.id.ivUser);
        Log.e("sss", user.getPictureUrl());
        if (!user.getPictureUrl().equals("")) {
            Picasso.with(context).load(user.getPictureUrl()).into(ivUser);
        }
        TextView        tvUserMail     = (TextView) layout.findViewById(R.id.tvUserMail);
        tvUserMail.setText(user.getEmail());
        TextView        tvFollowers    = (TextView) layout.findViewById(R.id.tvFollowers);
        tvFollowers.setText("" + user.getFollowersCount());
        final ImageView       ivNotification = (ImageView) layout.findViewById(R.id.imFollow);
        if (user.isFollowed()) {
            ivNotification.setImageResource(R.drawable.following_btn);
        }
        else {
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
