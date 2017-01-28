package cm.com.newdon.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cm.com.newdon.R;
import cm.com.newdon.classes.PhoneContact;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for notificaion listview
 */
public class ContactsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PhoneContact> contacts;

    public ContactsAdapter(Context context, ArrayList<PhoneContact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {


        return contacts.size();
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

        final PhoneContact contact = contacts.get(position);

        CircleImageView ivUser         = (CircleImageView) layout.findViewById(R.id.ivUser);

        TextView        tvUserMail     = (TextView) layout.findViewById(R.id.tvUserMail);
        tvUserMail.setText(contact.getEmail());
        TextView        tvFollowers    = (TextView) layout.findViewById(R.id.tvFollowers);
        tvFollowers.setText("" + contact.getName());
        ImageView       ivNotification = (ImageView) layout.findViewById(R.id.imFollow);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "CLICK", Toast.LENGTH_LONG).show();
            }
        });

        return layout;
    }
}
