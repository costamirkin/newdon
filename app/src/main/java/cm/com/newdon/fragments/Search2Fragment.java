package cm.com.newdon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.FoundationsAdapter;
import cm.com.newdon.adapters.SuggestedConnectionsAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;


public class Search2Fragment extends Fragment implements DataLoadedIf {
    private SuggestedConnectionsAdapter adapter;
    private EditText searchEt;
    private TextView tvCancel;
    private ListView listView;
    private ImageView ivClose;
    private RelativeLayout beforeSearchRl;
    private RelativeLayout notFoundRl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search2, container, false);

        beforeSearchRl = (RelativeLayout) v.findViewById(R.id.beforeSearchRl);
        notFoundRl     = (RelativeLayout) v.findViewById(R.id.notFoundRl);


        listView = (ListView) v.findViewById(R.id.listView);
        adapter =  new SuggestedConnectionsAdapter(getActivity(), true, CommonData.getInstance().getSearchUsers());
        listView.setAdapter(adapter);

        ivClose = (ImageView) v.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
                beforeSearchRl.setVisibility(View.VISIBLE);
                notFoundRl.setVisibility(View.INVISIBLE);


            }
        });

        tvCancel = (TextView) v.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(Search2Fragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        searchEt = (EditText) v.findViewById(R.id.searchEt);
        searchEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (!str.equals("")) {
                    beforeSearchRl.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    adapter.setSearchStr(str);
                    DataLoader.searchUsers(str);
                }
                else {
                    beforeSearchRl.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);

                }



            }

            @Override
            public void afterTextChanged(Editable s) {

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
        CommonData.getInstance().imageLoadedIf =  null;

    }

    @Override
    public void imageLoaded(int postId) {

    }

    @Override
    public void dataLoaded() {
        if (CommonData.getInstance().getSearchUsers().size() == 0) {
            listView.setVisibility(View.INVISIBLE);
            notFoundRl.setVisibility(View.VISIBLE);
        }
        else {
            listView.setVisibility(View.VISIBLE);
            notFoundRl.setVisibility(View.INVISIBLE);
            listView.invalidateViews();
        }
    }


}
