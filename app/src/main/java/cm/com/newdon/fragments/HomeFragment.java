package cm.com.newdon.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.File;

import cm.com.newdon.BottomBarActivity;
import cm.com.newdon.R;
import cm.com.newdon.adapters.PostsAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.OnPostSelectedListener;


public class HomeFragment extends Fragment implements DataLoadedIf {
    private ListView lv;

    private OnPostSelectedListener mCallBack;
    private ImageView altruImage;
    private PostsAdapter postsAdapter;

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


    boolean flag_loading = false;

    private void additems() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        lv = (ListView) view.findViewById(R.id.lvPosts);
        postsAdapter = new PostsAdapter(getActivity().getApplicationContext(), mCallBack, CommonData.getInstance().getPosts());
        lv.setAdapter(postsAdapter);
        lv.invalidateViews();

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (flag_loading == false) {
                        flag_loading = true;
                        CommonData.getInstance().feedPage++;
                        DataLoader.updateHomeScreenPosts(getActivity());
                    }
                }
            }
        });

        altruImage = (ImageView) view.findViewById(R.id.altruImage);
        altruImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setSelectionAfterHeaderView();
            }
        });

        //to get notification counter
        DataLoader.getNotificationCount();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        CommonData.getInstance().imageLoadedIf =  this;

        if (lv != null) {
            lv.setSelectionAfterHeaderView();
        }
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

        //the old code if we don't use Picasso
//        int position = CommonData.getInstance().findPostIndexById(postId);
//
//        if (position >= lv.getFirstVisiblePosition() &&
//                position < lv.getLastVisiblePosition() ) {
//            RelativeLayout layout = (RelativeLayout) lv.getChildAt(position + 1);
//            ImageView imageView = (ImageView) layout.findViewById(R.id.ivPost);
//            File imgFile = new File(CommonData.getInstance().getPosts().get(position).getLocalImagePath());
//            if(imgFile.exists()){
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                imageView.setImageBitmap(myBitmap);
//                imageView.setVisibility(View.VISIBLE);
//            }
//        }
    }

    @Override
    public void dataLoaded() {
        if (flag_loading) {
            flag_loading = false;
            postsAdapter.notifyDataSetChanged();

        }
        else {
            lv.invalidateViews();
            ((BottomBarActivity) getActivity()).changeNotificationBadge();
        }
    }
}
