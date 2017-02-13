package cm.com.newdon.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import cm.com.newdon.adapters.FollowAdapter;
import cm.com.newdon.adapters.SuggestedConnectionsAdapter;
import cm.com.newdon.classes.PhoneContact;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;

public class FollowFragment extends Fragment implements DataLoadedIf {

    private String type = "";
    private ImageView backBtn;

    public void setType(String type) {
        this.type = type;
    }

    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_follow, container, false);
        listView = (ListView) view.findViewById(R.id.lvFollow);
        DataLoader.getFollowUsers(type, CommonData.getInstance().getSelectedUserId());

        followAdapter = new FollowAdapter(getActivity(), type);
        listView.setAdapter(followAdapter);

        final TextView tvFollow = (TextView) view.findViewById(R.id.tvFollow);
        if (type.equals("following")) {
            tvFollow.setText("Following");
        }
        else {
            tvFollow.setText("Followers");

        }
        backBtn      = (ImageView) view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(FollowFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });



        return view;
    }

       @Override
    public void onStart() {
        super.onStart();
        CommonData.getInstance().imageLoadedIf =  this;

    }

    private FollowAdapter followAdapter = null;



    private ProgressDialog          progressDialog;


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
