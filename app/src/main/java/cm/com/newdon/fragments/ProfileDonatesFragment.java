package cm.com.newdon.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cm.com.newdon.R;
import cm.com.newdon.adapters.PostsAdapter;
import cm.com.newdon.adapters.UserPostsAdapter;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class ProfileDonatesFragment extends Fragment {

    private ListView lv;
    HomeFragment.OnPostSelectedListener mCallBack;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (HomeFragment.OnPostSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPostSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_profile_donates,container,false);

        lv = (ListView) v.findViewById(R.id.listView);
        CommonData.getInstance().copyUserPosts();
        UserPostsAdapter adapter = new UserPostsAdapter(getActivity().getApplicationContext(),mCallBack);
        lv.setAdapter(adapter);
        StikkyHeaderBuilder.stickTo(lv)
                .setHeader(R.id.header, (ViewGroup) v)
                .minHeightHeader(200)
//                .animator(new IconAnimator())

                .build();


        User selectedUser = CommonData.getInstance().getSelectedUser();

        TextView nameTv = (TextView) v.findViewById(R.id.name);
        nameTv.setText(selectedUser.getUserName());
        TextView fullNameTv = (TextView) v.findViewById(R.id.fullName);
        fullNameTv.setText(selectedUser.getRealName());

        TextView followersTv = (TextView) v.findViewById(R.id.follow);
        followersTv.setText("" + CommonData.getInstance().getCurrentUser().getFollowersCount() + " followers | " +
                selectedUser.getFollowingCount() + " following");

        TextView donationsTv = (TextView) v.findViewById(R.id.donations);
        donationsTv.setText("" + selectedUser.getDonCount() + " donates");

        return v;
    }
}
