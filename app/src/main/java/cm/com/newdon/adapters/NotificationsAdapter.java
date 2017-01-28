package cm.com.newdon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cm.com.newdon.R;
import cm.com.newdon.classes.Notification;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DateHandler;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notification listview
 */
public class NotificationsAdapter extends BaseAdapter {

    Context context;

    public NotificationsAdapter(Context context) {
        this.context = context;
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

        Notification notification = CommonData.getInstance().getNotifications().get(position);

        CircleImageView ivUser = (CircleImageView) layout.findViewById(R.id.ivUser);
        if (notification.getUser().getPictureUrl() != null && !notification.getUser().getPictureUrl().equals("")) {
            Picasso.with(context).load(notification.getUser().getPictureUrl()).into(ivUser);
        }

        TextView tvUserName = (TextView) layout.findViewById(R.id.tvUserName);
        tvUserName.setText(notification.getUser().getRealName());

        //TODO
        //change text of action
        TextView tvAction = (TextView) layout.findViewById(R.id.tvAction);
        tvAction.setText(notification.getType());

        TextView tvActionTime = (TextView) layout.findViewById(R.id.tvActionTime);
        tvActionTime.setText(DateHandler.howLongAgoWasDate(notification.getCreatedAt()));

        //TODO
        ImageView ivNotification = (ImageView) layout.findViewById(R.id.imNotification);

        return layout;
    }
}
