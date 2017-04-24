package cm.com.newdon.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cm.com.newdon.R;
import cm.com.newdon.classes.FbUser;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notificaion listview
 */
public class FbConnectionsAdapter extends BaseAdapter {

    private Context context;



    public FbConnectionsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {

        return CommonData.getInstance().fbUsers.size();
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
            layout = (RelativeLayout) View.inflate(context, R.layout.fb_connection_item, null);
        }

        final FbUser user = CommonData.getInstance().fbUsers.get(position);

        CircleImageView ivUser         = (CircleImageView) layout.findViewById(R.id.ivUser);
        Log.e("sss", user.getPictureUrl());
        if (!user.getPictureUrl().equals("")) {
            Picasso.with(context).load(user.getPictureUrl()).into(ivUser);
            ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //CommonData.getInstance().setSelectedUser(user);

                    //CommonData.bottomBarActivity.onUserSelected(user.getId());
                }
            });
        }
        TextView        tvUserMail     = (TextView) layout.findViewById(R.id.tvUserMail);
        String realName = user.getName();
        tvUserMail.setText(realName);



        final Button invite = (Button) layout.findViewById(R.id.invite);


        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return layout;
    }
}
