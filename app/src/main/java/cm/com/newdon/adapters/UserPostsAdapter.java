package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cm.com.newdon.CommentsActivity;
import cm.com.newdon.DonateActivity;
import cm.com.newdon.HideDeleteDialogActivity;
import cm.com.newdon.HideReportDialogActivity;
import cm.com.newdon.R;
import cm.com.newdon.ShareDialogActivity;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DateHandler;
import cm.com.newdon.common.OnPostSelectedListener;
import cm.com.newdon.common.PostQuery;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * adapter for posts
 */
public class UserPostsAdapter extends BasePostsAdapter {


    public UserPostsAdapter(Context context, OnPostSelectedListener mCallBack, List<Post> posts) {
        super(context, mCallBack, posts);
    }

    @Override
    protected int count() {
        return posts.size();
    }

    @Override
    public int getCount() {
        return posts.size();
    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        Post post = posts.get(position);
        createLayout(post, parent);
        return layout;
    }

}
