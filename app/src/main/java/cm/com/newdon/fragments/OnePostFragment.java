package cm.com.newdon.fragments;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.UserPostsAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.OnPostSelectedListener;


public class OnePostFragment extends Fragment implements DataLoadedIf {

    OnPostSelectedListener mCallBack;
    ListView listView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one_post, container, false);

        UserPostsAdapter adapter = new UserPostsAdapter(getActivity().getApplicationContext(),
                mCallBack, CommonData.getInstance().getUserPosts());

        listView = (ListView) v.findViewById(R.id.listViewPost);
        listView.setAdapter(adapter);

        ImageView backBtn = (ImageView) v.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(OnePostFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return v;
    }

    @Override
    public void imageLoaded(int postId) {

    }

    @Override
    public void dataLoaded() {
        listView.invalidateViews();
    }
}
