package cm.com.newdon.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.FoundationPostsAdapter;
import cm.com.newdon.adapters.SingleFoundationAdapter;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.OnPostSelectedListener;
import de.hdodenhof.circleimageview.CircleImageView;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class FoundationDonatesFragment extends Fragment {

    private ListView lv;
    private ImageView smallImage1;
    private ImageView smallImage2;
    private ImageView smallImage3;
    private ImageView line;

    private OnPostSelectedListener mCallBack;
    private FoundationPostsAdapter  adapter;
    private SingleFoundationAdapter singleFoundationAdapter;

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

        lv = (ListView) v.findViewById(R.id.listView);
        CommonData.getInstance().copyFoundationPosts();
        adapter = new FoundationPostsAdapter(getActivity().getApplicationContext(), mCallBack);

        lv.setAdapter(adapter);
        StikkyHeaderBuilder.stickTo(lv)
                .setHeader(R.id.header, (ViewGroup) v)
                .minHeightHeader(70)
//                .animator(new IconAnimator())

                .build();

        CircleImageView image = (CircleImageView) v.findViewById(R.id.found_image);
        int foundationId = CommonData.getInstance().getSelectedFoundId();
        if (foundationId != -1) {
            Foundation f = CommonData.getInstance().findFoundById(foundationId);
            if (f != null && f.getLogo() != null) {
                image.setImageBitmap(f.getLogo());
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
                lv.setAdapter(adapter);
                lv.invalidateViews();
            }
        });


        return v;
    }
}
