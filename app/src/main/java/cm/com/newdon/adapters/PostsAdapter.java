package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import cm.com.newdon.LotteryActivity;
import cm.com.newdon.R;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Marina on 17.12.2016.
 */
public class PostsAdapter extends BaseAdapter {

    private Context context;

    RelativeLayout layout;

    public PostsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return CommonData.getInstance().getPosts().size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (position==0){

            layout = (RelativeLayout) View.inflate(context, R.layout.lottery_view_pager, null);
            ViewPager viewPager = (ViewPager) layout.findViewById(R.id.viewpager);
            CircleIndicator indicator = (CircleIndicator) layout.findViewById(R.id.indicator);
            LotteryViewPagerAdapter viewPagerAdapter = new LotteryViewPagerAdapter(context);

            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(0);
            indicator.setViewPager(viewPager);
            viewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        }else {
            layout = (RelativeLayout) inflater.inflate(R.layout.post, parent, false);

            TextView tvUser = (TextView) layout.findViewById(R.id.tvUserName);
            TextView tvDate = (TextView) layout.findViewById(R.id.tvDate);
            TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
            TextView tvComment = (TextView) layout.findViewById(R.id.tvComment);
            TextView tvDonated = (TextView) layout.findViewById(R.id.tvDonated);

            Post post = CommonData.getInstance().getPosts().get(position-1);

            //for test
            tvDate.setText(post.getId()+"");

            tvUser.setText(post.getUser().getUserName());
            tvCategory.setText(post.getFoundation().getCategory().getName());
            tvCategory.setTextColor(Color.parseColor(post.getFoundation().getCategory().getColor()));
            try {
                tvComment.setText(post.getMessage());
            }catch (Exception e){
                e.printStackTrace();
            }
            if (post.getLocalImagePath() != null) {
                ImageView imageView = (ImageView) layout.findViewById(R.id.ivPost);
                File imgFile = new File(post.getLocalImagePath());
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                }
            }

        }
        return layout;
    }
}
