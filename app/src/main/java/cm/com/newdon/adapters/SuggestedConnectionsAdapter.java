package cm.com.newdon.adapters;

import android.content.Context;
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

import cm.com.newdon.R;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notificaion listview
 */
public class SuggestedConnectionsAdapter extends BaseAdapter {

    Context context;

    public SuggestedConnectionsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {

        return CommonData.getInstance().getSuggestedUsers().size();
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

        final User user = CommonData.getInstance().getSuggestedUsers().get(position);

        CircleImageView ivUser         = (CircleImageView) layout.findViewById(R.id.ivUser);
        Log.e("sss", user.getPictureUrl());
        if (!user.getPictureUrl().equals("")) {
            Picasso.with(context).load(user.getPictureUrl()).into(ivUser);
        }
        TextView        tvUserMail     = (TextView) layout.findViewById(R.id.tvUserMail);
        tvUserMail.setText(user.getEmail());
        TextView        tvFollowers    = (TextView) layout.findViewById(R.id.tvFollowers);
        tvFollowers.setText("" + user.getFollowersCount());
        ImageView       ivNotification = (ImageView) layout.findViewById(R.id.imFollow);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.followUser(user.getId(), context);
                Toast.makeText(context, "CLICK", Toast.LENGTH_LONG).show();
            }
        });

        return layout;
    }
}
