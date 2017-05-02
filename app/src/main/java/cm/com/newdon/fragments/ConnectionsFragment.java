package cm.com.newdon.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cm.com.newdon.BottomBarActivity;
import cm.com.newdon.ConnectionActivity;
import cm.com.newdon.R;
import cm.com.newdon.adapters.ContactsAdapter;
import cm.com.newdon.adapters.FbConnectionsAdapter;
import cm.com.newdon.adapters.NotificationsAdapter;
import cm.com.newdon.adapters.SuggestedConnectionsAdapter;
import cm.com.newdon.classes.FbUser;
import cm.com.newdon.classes.PhoneContact;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class ConnectionsFragment extends Fragment implements DataLoadedIf {

    private ListView    listView;
    private LoginButton loginButton;
    private boolean     isContactsFirst = true;
    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_connections, container, false);
        listView = (ListView) view.findViewById(R.id.lvConnections);
        loginButton = (LoginButton) view.findViewById(R.id.facebook);
        DataLoader.getSuggestedUsers();

        fbConnectionsAdapter = new FbConnectionsAdapter(getActivity());

        showSuggestedConnections();
        listView.setAdapter(new SuggestedConnectionsAdapter(getActivity(), false,
                CommonData.getInstance().getSuggestedUsers()));

        final TextView tvSuggested = (TextView) view.findViewById(R.id.tvSuggested);
        final TextView tvFacebook = (TextView) view.findViewById(R.id.tvFacebook);
        final TextView tvContacts = (TextView) view.findViewById(R.id.tvContacts);

        ImageView finishbtn = (ImageView) view.findViewById(R.id.finishbtn);
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getClass().equals(ConnectionActivity.class)) {
                    startActivity(new Intent(getActivity(), BottomBarActivity.class));
                    getActivity().finish();
                }
                else {
                    CommonData.bottomBarActivity.goHome();
                }
            }
        });

        tvSuggested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuggestedConnections();
                changeTextViewColors(tvSuggested, tvFacebook, tvContacts);
                listView.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
            }
        });
        callbackManager = CallbackManager.Factory.create();

        tvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextViewColors(tvFacebook, tvSuggested, tvContacts);
                //findFacebookConnections();
                listView.setVisibility(View.INVISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("SUCCESS");
                        ProfileTracker profileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                this.stopTracking();
                                Profile.setCurrentProfile(currentProfile);


                            }
                        };
                        profileTracker.startTracking();


                        AccessToken token = AccessToken.getCurrentAccessToken();
                        findFacebookConnections();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("CANCEL");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        System.out.println("Error " + error.getMessage());

                    }
                });
            }
        });
        tvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextViewColors(tvContacts, tvFacebook, tvSuggested);
                progressDialog = ProgressDialog.show(getActivity(),
                        "Please wait ...", "Reading Contacts ...", true);
                progressDialog.setCancelable(false);
                listView.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);

                showContacts();
            }
        });

        contacts = new ArrayList<PhoneContact>();
        contactsAdapter = new ContactsAdapter(getActivity(), contacts);

        if (isContactsFirst) {
            isContactsFirst = false;
            new GetContactsThread().start();
        }
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
                            //System.out.println(response.toString());
                            try {
                                CommonData.getInstance().fbUsers.clear();
                                JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                System.out.println("length     " + rawName.length());
                                for (int i = 0; i < rawName.length(); i++) {
                                    JSONObject fbUserObject = rawName.getJSONObject(i);
                                    String id   = fbUserObject.getString("id");
                                    String name = fbUserObject.getString("name");
                                    JSONObject picture = fbUserObject.getJSONObject("picture");
                                    JSONObject data    = picture.getJSONObject("data");
                                    String url = data.getString("url");
                                    CommonData.getInstance().fbUsers.add(new FbUser(id, name, url));

                                }
                                System.out.println(rawName.length());


                                listView.setAdapter(fbConnectionsAdapter);
                                listView.setVisibility(View.VISIBLE);
                                loginButton.setVisibility(View.INVISIBLE);
                            } catch (Exception e) {
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

    private SuggestedConnectionsAdapter suggestedConnectionsAdapter = null;
    private FbConnectionsAdapter        fbConnectionsAdapter = null;
    private ContactsAdapter             contactsAdapter             = null;

    private void showSuggestedConnections() {
        if (suggestedConnectionsAdapter == null) {
            suggestedConnectionsAdapter = new SuggestedConnectionsAdapter(getActivity(), false,
                    CommonData.getInstance().getSuggestedUsers());
        }
        listView.setAdapter(suggestedConnectionsAdapter);
    }


    private ArrayList<PhoneContact> contacts;
    private ProgressDialog          progressDialog;

    private void showContacts() {
        new TimerThread().start();

    }

    private boolean stopLoad = false;
    class TimerThread extends Thread {
        @Override
        public void run() {
            SystemClock.sleep(3000);
            stopLoad = true;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                    listView.setAdapter(contactsAdapter);

                }
            });

        }
    }

    class GetContactsThread extends Thread {
        @Override
        public void run() {
            super.run();
            ContentResolver cr = getActivity().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                    null, null, null);

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String phoneNumber = "";
                    String email = "";
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {
                        // Query phone here. Covered next
                        Cursor phones = getActivity().getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                null,
                                null);

                        while (phones.moveToNext()) {
                            phoneNumber = phones.getString(
                                    phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (phoneNumber != null && !phoneNumber.equals("")) {
                                break;
                            }
                        }
                        phones.close();

                    }


                    Cursor cur1 = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (cur1.moveToNext()) {
                        email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        if(email!=null){
                            break;
                        }
                    }
                    cur1.close();
                    if (stopLoad) {
                        return;
                    }
                    contacts.add(new PhoneContact(name, email, phoneNumber));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contactsAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }

         }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void dataLoaded() {
            listView.invalidateViews();
    }
}
