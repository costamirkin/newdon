package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        RelativeLayout layout;
        if (convertView != null) {
            layout = (RelativeLayout)convertView;
        }
        else {
            layout = (RelativeLayout) View.inflate(context, R.layout.invite_contact_item, null);
        }

        final PhoneContact contact = contacts.get(position);

        CircleImageView ivUser         = (CircleImageView) layout.findViewById(R.id.ivUser);

        TextView        tvUserName    = (TextView) layout.findViewById(R.id.tvUserName);
        tvUserName.setText(contact.getName());
        TextView        tvUserMail     = (TextView) layout.findViewById(R.id.tvUserMail);
        tvUserMail.setText(contact.getEmail());
        ImageView       ivEmail = (ImageView) layout.findViewById(R.id.imEmail);
        ivEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contact.getEmail().equals("")) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", contact.getEmail(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New don");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
                else {
                    Utils.showAlertDialog("No email for the contact", context);
                }
            }
        });
        ImageView       imSms = (ImageView) layout.findViewById(R.id.imSms);
        imSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contact.getPhone().equals("")) {
                    Uri uri = Uri.parse("smsto:" +contact.getPhone());
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("New don", "The SMS text");
                    context.startActivity(it);
                }
                else {
                    Utils.showAlertDialog("No phone for the contact", context);

                }
            }
        });

        return layout;
    }
}
