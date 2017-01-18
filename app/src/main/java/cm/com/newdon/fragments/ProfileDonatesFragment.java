package cm.com.newdon.fragments;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cm.com.newdon.R;
import cm.com.newdon.adapters.PostsAdapter;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class ProfileDonatesFragment extends Fragment {

    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_profile_donates,container,false);

        lv = (ListView) v.findViewById(R.id.listView);
        PostsAdapter adapter = new PostsAdapter(getActivity().getApplicationContext());
        lv.setAdapter(adapter);
        StikkyHeaderBuilder.stickTo(lv)
                .setHeader(R.id.header, (ViewGroup) v)
                .minHeightHeader(200)
//                .animator(new IconAnimator())

                .build();

        return v;
    }
}
