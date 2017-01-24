package cm.com.newdon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cm.com.newdon.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notificaion listview
 */
public class NotificationsAdapter extends BaseAdapter {

    Context context;

    public NotificationsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
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
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout layout = (LinearLayout) View.inflate(context, R.layout.notification_item_for_lv, null);

        CircleImageView ivUser = (CircleImageView) layout.findViewById(R.id.ivUser);
        TextView tvUserName = (TextView) layout.findViewById(R.id.tvUserName);
        TextView tvAction = (TextView) layout.findViewById(R.id.tvAction);
        TextView tvActionTime = (TextView) layout.findViewById(R.id.tvActionTime);
        ImageView ivNotification = (ImageView) layout.findViewById(R.id.imNotification);

        return layout;
    }
}
