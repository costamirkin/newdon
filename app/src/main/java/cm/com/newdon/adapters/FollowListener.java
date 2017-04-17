package cm.com.newdon.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import cm.com.newdon.R;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.Utils;

/**
 * Created by costa on 17/04/17.
 */
class FollowListener implements View.OnClickListener {
    private ImageView ivNotification;
    private User user;
    private Context context;

    public FollowListener(ImageView ivNotification, User user, Context context) {
        this.ivNotification = ivNotification;
        this.user = user;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (user.isFollowed()) {
            Utils.unfollowUser(user.getId(), context);
            ivNotification.setImageResource(R.drawable.follow_btn);
            user.setIsFollowed(false);
        } else {
            Utils.followUser(user.getId(), context);
            ivNotification.setImageResource(R.drawable.following_btn);
            user.setIsFollowed(true);

        }


    }
}
