package cm.com.newdon.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.File;

import cm.com.newdon.LotteryActivity;
import cm.com.newdon.R;
import cm.com.newdon.adapters.PostsAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;


public class HomeFragment extends Fragment implements DataLoadedIf {
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CommonData.getInstance().imageLoadedIf =  this;
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        lv = (ListView) view.findViewById(R.id.lvPosts);
        lv.setAdapter(new PostsAdapter(getActivity().getApplicationContext()));
        lv.invalidateViews();

//        DataLoader.getUserPosts();

        return view;
    }

    @Override
    public void imageLoaded(int postId) {
        int position = CommonData.getInstance().findPostIndexById(postId);
        Log.e("Posts", "" + position + " " + lv.getLastVisiblePosition());

        if (position >= lv.getFirstVisiblePosition() &&
                position < lv.getLastVisiblePosition() ) {
            RelativeLayout layout = (RelativeLayout) lv.getChildAt(position + 1);
            ImageView imageView = (ImageView) layout.findViewById(R.id.ivUser);
            File imgFile = new File(CommonData.getInstance().getPosts().get(position).getLocalImagePath());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }
        }
    }

    @Override
    public void postsLoaded() {
        lv.invalidateViews();
    }

    public void openLottery(View view) {
        startActivity(new Intent(getActivity().getApplicationContext(), LotteryActivity.class ));
    }
}
