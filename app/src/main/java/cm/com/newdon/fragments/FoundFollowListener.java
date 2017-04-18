package cm.com.newdon.fragments;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import cm.com.newdon.R;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.Utils;

/**
 * Created by costa on 17/04/17.
 */
class FoundFollowListener implements View.OnClickListener {
    private ImageView ivNotification;
    private Foundation foundation;
    private Context context;

    public FoundFollowListener(ImageView ivNotification, Foundation foundation, Context context) {
        this.ivNotification = ivNotification;
        this.foundation = foundation;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (foundation.isSubscribed()) {
            Utils.unfollowFoundation(foundation.getId(), context);
            ivNotification.setImageResource(R.drawable.follow_found);
            foundation.setIsSubscribed(false);
        } else {
            ivNotification.setImageResource(R.drawable.follow_btn_brown);
            Utils.followFoundation(foundation.getId(), context);
            foundation.setIsSubscribed(true);

        }


    }
}
