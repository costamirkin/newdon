package cm.com.newdon.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.PostsAdapter;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class FoundationDonatesFragment extends Fragment {

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
        View v =inflater.inflate(R.layout.fragment_foundation_donates,container,false);

        lv = (ListView) v.findViewById(R.id.listView);
        PostsAdapter adapter = new PostsAdapter(getActivity().getApplicationContext(), mCallBack);
        lv.setAdapter(adapter);
        StikkyHeaderBuilder.stickTo(lv)
                .setHeader(R.id.header, (ViewGroup) v)
                .minHeightHeader(200)
//                .animator(new IconAnimator())

                .build();

        return v;
    }
}
