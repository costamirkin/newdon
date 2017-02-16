package cm.com.newdon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.FoundationsAdapter;


public class SearchFragment extends Fragment {
    private FoundationsAdapter adapter;
    private GridView gv;
    private Search2Fragment search2Fragment = new Search2Fragment();
    private ImageView  magnifier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        adapter = new FoundationsAdapter(getActivity());
        gv = (GridView) v.findViewById(R.id.gvFounds);
        gv.setAdapter(adapter);
        gv.invalidate();

        magnifier = (ImageView) v.findViewById(R.id.magnifier);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, search2Fragment);

                ft.addToBackStack("This Fragment");
                ft.commit();
            }
        });
        return v;
    }

}
