package cm.com.newdon.adapters;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.internal.Utility;
import com.squareup.picasso.Picasso;

import cm.com.newdon.R;
import cm.com.newdon.classes.Notification;
import cm.com.newdon.classes.Post;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DateHandler;
import cm.com.newdon.common.OnPostSelectedListener;
import cm.com.newdon.common.PostQuery;
import cm.com.newdon.common.Utils;
import cm.com.newdon.fragments.HomeFragment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notification listview
 */
public class NotificationsAdapter extends BaseAdapter {

    private Context context;
    private boolean isActivities;
    public static boolean UNFOLLOW = true;

    //  we use mCallBack to say BottomBarActivity which fragment to commit
    OnPostSelectedListener mCallBack;

    public void setIsActivities(boolean isActivities) {
        this.isActivities = isActivities;
    }

    public NotificationsAdapter(Context context, OnPostSelectedListener mCallBack) {
        this.context = context;
        this.mCallBack = mCallBack;
    }

    @Override
    public int getCount() {
        int size = CommonData.getInstance().getNotifications().size();
        return size;
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
            layout = (RelativeLayout) convertView;
        } else {
            layout = (RelativeLayout) View.inflate(context, R.layout.notification_item_for_lv, null);
        }

        final Notification notification = CommonData.getInstance().getNotifications().get(position);
        final User user = notification.getUser();

        CircleImageView ivUser = (CircleImageView) layout.findViewById(R.id.ivUser);
        if (user.getPictureUrl() != null && !user.getPictureUrl().equals("")) {
            Picasso.with(context).load(notification.getUser().getPictureUrl()).into(ivUser);
        }
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonData.getInstance().setSelectedUser(user);
                mCallBack.onUserSelected(user.getId());
            }
        });

        TextView tvUserName = (TextView) layout.findViewById(R.id.tvUserName);
        tvUserName.setText(user.getRealName());

        final CircleImageView ivNotification = (CircleImageView) layout.findViewById(R.id.imNotification);

        //handle contentType
        Post contentPost;
        String otherUserName = "USER";
        switch (notification.getContentType()) {
            case POST:
                contentPost = notification.getContentPost();
                if (contentPost.getImageUrl() != null
                        && !contentPost.getImageUrl().equals("")) {
                    Picasso.with(context).load(contentPost.getImageUrl()).into(ivNotification);
                }

                ivNotification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //clear the ArrayList and put only one post there
                        CommonData.getInstance().getUserPosts().clear();
                        CommonData.getInstance().getUserPosts().add(notification.getContentPost());
                        mCallBack.onPostSelected();
                    }
                });

                otherUserName = contentPost.getUser().getRealName();
                break;
            case USER:
                if (isActivities) {
                    final User contentUser = notification.getContentUser();
                    otherUserName = contentUser.getRealName();
                    if (contentUser.getPictureUrl() != null
                            && !contentUser.getPictureUrl().equals("")) {
                        Picasso.with(context).load(contentUser.getPictureUrl()).into(ivNotification);
                    }
                    ivNotification.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CommonData.getInstance().setSelectedUser(contentUser);
                            mCallBack.onUserSelected(contentUser.getId());
                        }
                    });
                } else {
                    ivNotification.setImageResource(user.isFollowed() ? R.drawable.approve : R.drawable.add_user_btn);
                    ivNotification.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (user.isFollowed()) {
                                Utils.followUser(user.getId(), context, UNFOLLOW);
                                ivNotification.setImageResource(R.drawable.add_user_btn);
                                user.setIsFollowed(true);
                            } else {
                                Utils.followUser(user.getId(), context);
                                ivNotification.setImageResource(R.drawable.approve);
                                user.setIsFollowed(true);
                            }
                        }
                    });
                }
                break;
        }

        //change text of action
        TextView tvAction = (TextView) layout.findViewById(R.id.tvAction);
        String notificationText = "";
        String whom = (isActivities ? otherUserName + "'s" : "your");
        String who = (isActivities ? otherUserName : "you");
        switch (notification.getType()) {
            case DONATE:
                notificationText = "donated through " + whom + " post";
                break;
            case SHARE:
                notificationText = "shared " + whom + " post";
                break;
            case FOLLOW:
                notificationText = "started following " + who;
                break;
            case UNFOLLOW:
                notificationText = "unfollowed " + who;
                break;
            case LIKE:
                notificationText = "liked " + whom + " post";
                break;
            case COMMENT:
                notificationText = "commented on " + whom + " post";
                break;
        }
        tvAction.setText(notificationText);

        TextView tvActionTime = (TextView) layout.findViewById(R.id.tvActionTime);
        tvActionTime.setText(DateHandler.howLongAgoWasDate(notification.getCreatedAt()));

        return layout;
    }
}
