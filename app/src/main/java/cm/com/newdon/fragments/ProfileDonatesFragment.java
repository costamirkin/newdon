package cm.com.newdon.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import cm.com.newdon.R;
import cm.com.newdon.adapters.UserPostsAdapter;
import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.OnPostSelectedListener;
import cm.com.newdon.common.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class ProfileDonatesFragment extends Fragment {

    private ListView lv;
    OnPostSelectedListener mCallBack;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (OnPostSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPostSelectedListener");
        }
    }

    private ImageView smallImage1;
    private ImageView smallImage2;
    private ImageView line;
    private ImageView settingsIv;
    private ImageView changeImage;
    private ImageView followButton;
    private ProfileFragment profileFragment = new ProfileFragment();
    private FollowFragment  followFragment  = new FollowFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private CircleImageView profileImage;
    private ImageView backBtn;


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
        UserPostsAdapter adapter = new UserPostsAdapter(getActivity().getApplicationContext(),
                mCallBack, CommonData.getInstance().getUserPosts());
        lv.setAdapter(adapter);
        StikkyHeaderBuilder.stickTo(lv)
                .setHeader(R.id.header, (ViewGroup) v)
                .minHeightHeader(100)
//                .animator(new IconAnimator())

                .build();


        final User selectedUser = CommonData.getInstance().getSelectedUser();

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
                smallImage1.setImageResource(R.drawable.don_icns);
                smallImage2.setImageResource(R.drawable.tag_icn_grey);

            }
        });
        smallImage2 = (ImageView) v.findViewById(R.id.smallImage2);
        smallImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line.setImageResource(R.drawable.lineopp);
                smallImage1.setImageResource(R.drawable.don_icns_grey);
                smallImage2.setImageResource(R.drawable.tag_icn);
            }
        });


        settingsIv = (ImageView) v.findViewById(R.id.settingsIv);
        settingsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, settingsFragment);

                ft.addToBackStack("This Fragment");
                ft.commit();
            }
        });


        backBtn      = (ImageView) v.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(ProfileDonatesFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        profileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        changeImage  = (ImageView) v.findViewById(R.id.edit_btn);
        followButton = (ImageView) v.findViewById(R.id.follow_btn);
        if (!selectedUser.equals(CommonData.getInstance().getCurrentUser())) {
            changeImage.setVisibility(View.INVISIBLE);
            followButton.setVisibility(View.VISIBLE);
            if (selectedUser.isFollowed()) {
                followButton.setImageResource(R.drawable.follow_btn1);
            }
            else {
                followButton.setImageResource(R.drawable.follow_btn2);
                followButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.followUser(selectedUser.getId(), getActivity());
                        followButton.setImageResource(R.drawable.follow_btn1);
                    }
                });

            }
            settingsIv.setVisibility(View.INVISIBLE);
            backBtn.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(selectedUser.getPictureUrl()).into(profileImage);
        }
        else {
            File profileImageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                    CommonData.profileImageName);
            if (profileImageFile.exists()) {
                profileImage.setImageURI(null);
                profileImage.setImageURI(Uri.fromFile(profileImageFile));
            }
            changeImage.setVisibility(View.VISIBLE);
            settingsIv.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.INVISIBLE);
            followButton.setVisibility(View.INVISIBLE);

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
