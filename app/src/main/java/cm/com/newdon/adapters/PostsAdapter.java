package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
import me.relex.circleindicator.CircleIndicator;

/**
 * adapter for posts
 */
public class PostsAdapter extends BasePostsAdapter {

    public PostsAdapter(Context context, OnPostSelectedListener mCallBack, List<Post> posts) {
        super(context, mCallBack, posts);
    }


    @Override
    protected int count() {
        // One for lottery
        return  posts.size() + 1;
    }




    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        if (position == 0) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) View.inflate(context, R.layout.lottery_view_pager, null);
            ViewPager viewPager = (ViewPager) layout.findViewById(R.id.viewpager);
            CircleIndicator indicator = (CircleIndicator) layout.findViewById(R.id.indicator);
            LotteryViewPagerAdapter viewPagerAdapter = new LotteryViewPagerAdapter(context);

            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(0);
            indicator.setViewPager(viewPager);
            viewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        } else {
            Post post = posts.get(position - 1);
            createLayout(post, parent);
        }
        return layout;
    }


}
