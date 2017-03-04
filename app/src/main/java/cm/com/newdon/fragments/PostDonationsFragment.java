package cm.com.newdon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.SuggestedConnectionsAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;


public class PostDonationsFragment extends Fragment implements DataLoadedIf {
    private SuggestedConnectionsAdapter adapter;
    private EditText searchEt;
    private TextView tvCancel;
    private ListView listView;
    private ImageView ivClose;
    private RelativeLayout beforeSearchRl;
    private RelativeLayout notFoundRl;
    private int postId;

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DataLoader.getPostDonateUsers(postId);
        View v = inflater.inflate(R.layout.fragment_post_donations, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        adapter =  new SuggestedConnectionsAdapter(getActivity(), true, CommonData.getInstance().getPostDonateUsers());
        listView.setAdapter(adapter);

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
