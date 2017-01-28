package cm.com.newdon.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cm.com.newdon.R;
import cm.com.newdon.adapters.ContactsAdapter;
import cm.com.newdon.adapters.NotificationsAdapter;
import cm.com.newdon.adapters.SuggestedConnectionsAdapter;
import cm.com.newdon.classes.PhoneContact;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;

public class ConnectionsFragment extends Fragment implements DataLoadedIf {

    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_connections, container, false);
        listView = (ListView) view.findViewById(R.id.lvConnections);
        DataLoader.getSuggestedUsers();

        listView.setAdapter(new SuggestedConnectionsAdapter(getActivity()));

        final TextView tvSuggested = (TextView) view.findViewById(R.id.tvSuggested);
        final TextView tvFacebook = (TextView) view.findViewById(R.id.tvFacebook);
        final TextView tvContacts = (TextView) view.findViewById(R.id.tvContacts);

        tvSuggested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextViewColors(tvSuggested,tvFacebook, tvContacts);
            }
        });
        tvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextViewColors(tvFacebook, tvSuggested, tvContacts);
                findFacebookConnections();
            }
        });
        tvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextViewColors(tvContacts, tvFacebook,tvSuggested);
                showContacts();
            }
        });

        getNameEmailDetails();
        return view;
    }

    private boolean isFbFirst = true;

    private void findFacebookConnections() {
        if (isFbFirst) {
            isFbFirst = false;
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/taggable_friends",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            System.out.println(response.toString());
                            try {
                                JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                System.out.println(rawName.length());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).executeAsync();        }
    }

    private void changeTextViewColors(TextView activeView, TextView inactiveView, TextView inactiveView2) {
        activeView.setTextColor(getActivity().getResources().getColor(R.color.white));
        activeView.setBackgroundResource(R.drawable.rectangle_38);
        inactiveView.setBackgroundColor(Color.WHITE);
        inactiveView.setTextColor(getActivity().getResources().getColor(R.color.grey));
        inactiveView2.setBackgroundColor(Color.WHITE);
        inactiveView2.setTextColor(getActivity().getResources().getColor(R.color.grey));
    }

       @Override
    public void onStart() {
        super.onStart();
        CommonData.getInstance().imageLoadedIf =  this;

    }

    private void showContacts() {
        ArrayList<PhoneContact> contacts = getNameEmailDetails();
        listView.setAdapter(new ContactsAdapter(getActivity(), contacts));
    }

    private ArrayList<PhoneContact> getNameEmailDetails(){
        ArrayList<PhoneContact> names = new ArrayList<PhoneContact>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names
                    String name=cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.e("Name :", name);
                    String contactNumber = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.e("phone :", contactNumber);
                    int type = cur1.getInt(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    Log.e("type :", "" + type);
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.e("Email", email);
                    if(email!=null){
                        names.add(new PhoneContact(name, email, contactNumber));
                    }
                }
                cur1.close();
            }
        }
        return names;
    }

    @Override
    public void onStop() {
        super.onStop();
        CommonData.getInstance().imageLoadedIf =  null;

    }

    @Override
    public void imageLoaded(int postId) {

    }

    @Override
    public void dataLoaded() {
            listView.invalidateViews();
    }
}