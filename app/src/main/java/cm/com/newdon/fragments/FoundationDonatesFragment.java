package cm.com.newdon.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.FoundationPostsAdapter;
import cm.com.newdon.adapters.SingleFoundationAdapter;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.OnPostSelectedListener;
import cm.com.newdon.common.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class FoundationDonatesFragment extends Fragment implements DataLoadedIf {

    private ListView lv;
    private ImageView smallImage1;
    private ImageView smallImage2;
    private ImageView smallImage3;
    private ImageView vsign;
    private ImageView line;

    private OnPostSelectedListener mCallBack;
    private FoundationPostsAdapter  adapter;
    private FoundationPostsAdapter  donationAdapter;
    private SingleFoundationAdapter singleFoundationAdapter;

    private TextView postsTv;

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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_foundation_donates,container,false);
        final int foundationId = CommonData.getInstance().getSelectedFoundId();
        postsTv = (TextView) v.findViewById(R.id.posts);

        vsign = (ImageView) v.findViewById(R.id.vsign);

        lv = (ListView) v.findViewById(R.id.listView);
        DataLoader.getFoundationPosts(getActivity(), foundationId);
        DataLoader.getFoundationDonationPosts(getActivity(), foundationId);
        //CommonData.getInstance().copyFoundationPosts();
        adapter = new FoundationPostsAdapter(getActivity().getApplicationContext(),
                mCallBack, CommonData.getInstance().getFoundationPosts());

        donationAdapter = new FoundationPostsAdapter(getActivity().getApplicationContext(),
                mCallBack, CommonData.getInstance().getFoundationDonationPosts());

        lv.setAdapter(adapter);
        StikkyHeaderBuilder.stickTo(lv)
                .setHeader(R.id.header, (ViewGroup) v)
                .minHeightHeader(70)
//                .animator(new IconAnimator())

                .build();

        CircleImageView image = (CircleImageView) v.findViewById(R.id.found_image);
        TextView fullName = (TextView) v.findViewById(R.id.fullName);
        TextView tvCategory  = (TextView) v.findViewById(R.id.tvCategory);
        if (foundationId != -1) {
            Foundation f = CommonData.getInstance().findFoundById(foundationId);
            if (f != null && f.getLogo() != null) {

                if (f.isFeatured()) {
                    vsign.setVisibility(View.VISIBLE);
                }
                else {
                    vsign.setVisibility(View.INVISIBLE);

                }
                image.setImageBitmap(f.getLogo());
                fullName.setText(f.getTitle());
                tvCategory.setText(f.getCategory().getName());
                // Donates
                //postsTv.setText("" + CommonData.getInstance().getFoundationPosts().size() + " posts");

                // Followers following
                TextView followersTv = (TextView) v.findViewById(R.id.followers);
                followersTv.setText("" + f.getFollowersCount() + " followers");
                followersTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                final ImageView followFound = (ImageView) v.findViewById(R.id.followFound);
                if (f.isSubscribed()) {
                    followFound.setImageResource(R.drawable.follow_btn_brown);

                }
                else {

                    followFound.setImageResource(R.drawable.follow_found);
                }
                followFound.setOnClickListener(new FoundFollowListener(followFound, f, getActivity()));



            }

        }




        line = (ImageView) v.findViewById(R.id.niceLine);
        smallImage1 = (ImageView) v.findViewById(R.id.smallImage1);
        smallImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallImage1.setImageResource(R.drawable.don_icns);
                smallImage2.setImageResource(R.drawable.shape_30);
                smallImage3.setImageResource(R.drawable.tag_icn_grey);
                lv.setAdapter(adapter);
                lv.invalidateViews();
                line.setImageResource(R.drawable.line);

            }
        });
        smallImage2 = (ImageView) v.findViewById(R.id.smallImage2);
        smallImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallImage1.setImageResource(R.drawable.don_icns_grey);
                smallImage2.setImageResource(R.drawable.shape_30);
                smallImage3.setImageResource(R.drawable.tag_icn_grey);
                line.setImageResource(R.drawable.linemid);
                singleFoundationAdapter = new SingleFoundationAdapter(getActivity());
                lv.setAdapter(singleFoundationAdapter);
                lv.invalidateViews();

            }
        });

        smallImage3 = (ImageView) v.findViewById(R.id.smallImage3);
        smallImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallImage1.setImageResource(R.drawable.don_icns_grey);
                smallImage2.setImageResource(R.drawable.shape_30);
                smallImage3.setImageResource(R.drawable.tag_icn);
                line.setImageResource(R.drawable.lineopp);
                lv.setAdapter(donationAdapter);
                lv.invalidateViews();
            }
        });



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        CommonData.getInstance().imageLoadedIf =  this;

    }

    @Override
    public void onStop() {
        super.onStop();
        if(CommonData.getInstance().imageLoadedIf==this) {
            CommonData.getInstance().imageLoadedIf = null;
        }
    }

    @Override
    public void imageLoaded(int postId) {

    }

    @Override
    public void dataLoaded() {
        if (lv != null) {
            lv.invalidateViews();
            postsTv.setText("" + CommonData.getInstance().getFoundationPosts().size() + " posts");
        }

    }
}
