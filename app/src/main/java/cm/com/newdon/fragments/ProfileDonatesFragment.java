package cm.com.newdon.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;

import cm.com.newdon.BottomBarActivity;
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

    private ImageView smallImage1;
    private ImageView smallImage2;
    private ImageView line;
    private ImageView changeImage;
    private ProfileFragment profileFragment = new ProfileFragment();
    private FollowFragment  followFragment  = new FollowFragment();


    class FollowersListener implements View.OnClickListener {

        private String type;

        public FollowersListener(String type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            followFragment.setType(type);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, followFragment);

            ft.addToBackStack("This Fragment");
            ft.commit();

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

        // Followers following
        TextView followersTv = (TextView) v.findViewById(R.id.followers);
        followersTv.setText("" + selectedUser.getFollowersCount() + " followers");

        TextView followingTv = (TextView) v.findViewById(R.id.following);
        followingTv.setText("" + selectedUser.getFollowingCount() + " following");

        FollowersListener followersListener = new FollowersListener("followers");
        followersTv.setOnClickListener(followersListener);

        FollowersListener followingListener = new FollowersListener("following");
        followingTv.setOnClickListener(followingListener);

        // Donations
        TextView donationsTv = (TextView) v.findViewById(R.id.donations);
        donationsTv.setText("" + selectedUser.getDonCount() + " donates");


        line = (ImageView) v.findViewById(R.id.niceLine);
        smallImage1 = (ImageView) v.findViewById(R.id.smallImage1);
        smallImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line.setImageResource(R.drawable.line);

            }
        });
        smallImage2 = (ImageView) v.findViewById(R.id.smallImage2);
        smallImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line.setImageResource(R.drawable.lineopp);
            }
        });


        changeImage  = (ImageView) v.findViewById(R.id.edit_btn);
        if (!selectedUser.equals(CommonData.getInstance().getCurrentUser())) {
            changeImage.setVisibility(View.INVISIBLE);
        }
        else {
            changeImage.setVisibility(View.VISIBLE);

            changeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentContainer, profileFragment);

                    ft.addToBackStack("This Fragment");
                    ft.commit();

                }

            });
        }

        return v;
    }
}
