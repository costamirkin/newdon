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

import java.io.File;

import cm.com.newdon.DonateActivity;
import cm.com.newdon.HideDeleteDialogActivity;
import cm.com.newdon.HideReportDialogActivity;
import cm.com.newdon.R;
import cm.com.newdon.ShareDialogActivity;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DateHandler;
import cm.com.newdon.fragments.HomeFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

/**
 * adapter for posts
 */
public class PostsAdapter extends BaseAdapter {

    private Context context;
    private Intent intent;

            //  we use mCallBack to say BottomBarActivity which fragment to commit
    HomeFragment.OnPostSelectedListener mCallBack;

    RelativeLayout layout;

    public PostsAdapter(Context context, HomeFragment.OnPostSelectedListener mCallBack) {
        this.context = context;
        this.mCallBack = mCallBack;
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
    public View getView(int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (position == 0) {
//            Lottery view page in the first row of listview
            layout = (RelativeLayout) View.inflate(context, R.layout.lottery_view_pager, null);
            ViewPager viewPager = (ViewPager) layout.findViewById(R.id.viewpager);
            CircleIndicator indicator = (CircleIndicator) layout.findViewById(R.id.indicator);
            LotteryViewPagerAdapter viewPagerAdapter = new LotteryViewPagerAdapter(context);

            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(0);
            indicator.setViewPager(viewPager);
            viewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        } else {
            layout = (RelativeLayout) inflater.inflate(R.layout.post, parent, false);

            TextView tvUser = (TextView) layout.findViewById(R.id.tvUserName);
            TextView tvDate = (TextView) layout.findViewById(R.id.tvDate);
            TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
            TextView tvComment = (TextView) layout.findViewById(R.id.tvComment);
            TextView tvDonated = (TextView) layout.findViewById(R.id.tvDonated);

//            I use -1 because in the first item we put lottery view pager
            final Post post = CommonData.getInstance().getPosts().get(position - 1);

            CircleImageView ivUser = (CircleImageView) layout.findViewById(R.id.ivUser);
//            // TODO: 20.01.2017
//            for all users

//          !!!! only for current user
            if (post.getUser().getId() == CommonData.getInstance().getCurrentUserId()) {
                File profileImageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                        CommonData.profileImageName);
                if (profileImageFile.exists()) {
                    ivUser.setImageURI(null);
                    ivUser.setImageURI(Uri.fromFile(profileImageFile));
                }
            }

//          on click on User picture we should show profileDonatesFragment
            ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonData.getInstance().setSelectedUser(post.getUser());
                    mCallBack.onUserSelected(post.getUser().getId());
                }
            });

            ImageView imFoundation = (ImageView) layout.findViewById(R.id.imFound);
            TextView tvFoundationTitle = (TextView) layout.findViewById(R.id.tvFoundTitle);
            Foundation foundation = CommonData.getInstance().findFoundById(post.getFoundation().getId());
            if (foundation != null) {
                if (foundation.getLogo() != null) {
                    imFoundation.setImageBitmap(foundation.getLogo());
                }
                //            on click on FoundationLogo we should show foundationDonatesFragment
                imFoundation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallBack.onFoundationSelected(post.getFoundation().getId());
                    }
                });

                tvFoundationTitle.setText(foundation.getTitle());
                tvCategory.setText(post.getFoundation().getCategory().getName());
                tvCategory.setTextColor(Color.parseColor(post.getFoundation().getCategory().getColor()));

            }

            tvDate.setText(DateHandler.howLongAgoWasDate(post.getCreatedAt()));
            tvUser.setText(post.getUser().getRealName());
            try {
                tvComment.setText(post.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

//            on click on Share icon dialog will open
            ImageView ivShare = (ImageView) layout.findViewById(R.id.ivShare);
            ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShareDialogActivity.class);
                    intent.putExtra("postId",post.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            //            on click on Share icon dialog will open
            ImageView ivOptions = (ImageView) layout.findViewById(R.id.ivOptions);
            ivOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(post.getUser().getId()==CommonData.getInstance().getCurrentUserId()) {
                        intent = new Intent(context, HideDeleteDialogActivity.class);
                    }else {
                        intent = new Intent(context, HideReportDialogActivity.class);
                    }
                    intent.putExtra("postId",post.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

//            on click on Coin we open Donate activity and transfer foundation ID
            ImageView ivCoin = (ImageView) layout.findViewById(R.id.ivCoin);
            ivCoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(context, DonateActivity.class);
                    intent.putExtra("foundationId", post.getFoundation().getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            if (post.getLocalImagePath() != null) {
                ImageView imageView = (ImageView) layout.findViewById(R.id.ivPost);
                File imgFile = new File(post.getLocalImagePath());
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        }
        return layout;
    }
}
