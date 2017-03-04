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
import cm.com.newdon.MakeDonActivity;
import cm.com.newdon.R;
import cm.com.newdon.ShareDialogActivity;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DateHandler;
import cm.com.newdon.common.OnPostSelectedListener;
import cm.com.newdon.common.PostQuery;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

/**
 * adapter for posts
 */
public abstract class BasePostsAdapter extends BaseAdapter {

    protected Context context;
    private Intent intent;
    protected TextView tvLikesBadge;
    protected TextView tvCommentsBadge;

    protected List<Post> posts;


    //  we use mCallBack to say BottomBarActivity which fragment to commit
    OnPostSelectedListener mCallBack;

    RelativeLayout layout;

    public BasePostsAdapter(Context context, OnPostSelectedListener mCallBack, List<Post> posts) {
        this.context = context;
        this.mCallBack = mCallBack;
        this.posts = posts;
    }

    protected abstract int count();

    @Override
    public int getCount() {
        return count();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // This functions creates RelativeLayout for single post
    protected void createLayout(final Post post, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (RelativeLayout) inflater.inflate(R.layout.post, parent, false);

        CircleImageView ivUser = (CircleImageView) layout.findViewById(R.id.ivUser);

//          !!!! only for current user
        if (post.getUser().getId() == CommonData.getInstance().getCurrentUserId()) {
            File profileImageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                    CommonData.profileImageName);
            if (profileImageFile.exists()) {
                ivUser.setImageURI(null);
                ivUser.setImageURI(Uri.fromFile(profileImageFile));
            }
            //            for all other users
        } else {
            String path = post.getUser().getPictureUrl();
            if (path != null && !path.equals(""))
                Picasso.with(context).load(path).into(ivUser);
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

            TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
            tvCategory.setText(post.getFoundation().getCategory().getName());
            tvCategory.setTextColor(Color.parseColor(post.getFoundation().getCategory().getColor()));

        }

        TextView tvDate = (TextView) layout.findViewById(R.id.tvDate);
        tvDate.setText(DateHandler.howLongAgoWasDate(post.getCreatedAt()));

        TextView tvUser = (TextView) layout.findViewById(R.id.tvUserName);
        tvUser.setText(post.getUser().getRealName());

        TextView tvComment = (TextView) layout.findViewById(R.id.tvComment);
        try {
            tvComment.setText(post.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView tvDonated = (TextView) layout.findViewById(R.id.tvDonated);
        if (post.getDonatorCount() > 0) {
            tvDonated.setText(post.getDonatorCount() + " donated");
            tvDonated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CommonData.bottomBarActivity != null) {
                        CommonData.bottomBarActivity.changePostDonationsFragment(post.getId());
                    }

                }
            });
        }

        tvLikesBadge = (TextView) layout.findViewById(R.id.tvLikesBadge);
        changeLikesBadge(post);

        //            on click on Like icon
        final ImageView ivLike = (ImageView) layout.findViewById(R.id.ivLike);
        if (post.isLiked()) ivLike.setImageResource(R.drawable.black_like);
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    change icon
                if (post.isLiked()) {
                    ivLike.setImageResource(R.drawable.black_like);
                } else {
                    ivLike.setImageResource(R.drawable.layer_5);
                }

                PostQuery.likePost(post.getId(), post.isLiked());

                //change isLiked to opposite
                post.setIsLiked(!post.isLiked());

                //chane amount of likes
                post.setLikesCount(post.getLikesCount() + (post.isLiked() ? 1 : -1));

//             change amount on badge
                changeLikesBadge(post);
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }
        });

        tvCommentsBadge = (TextView) layout.findViewById(R.id.tvCommentsBadge);
        changeCommentsBadge(post);

        //            on click on Comment icon CommentsActivity will open
        ImageView ivComment = (ImageView) layout.findViewById(R.id.ivComment);
        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("postId", post.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//            on click on Share icon dialog will open
        ImageView ivShare = (ImageView) layout.findViewById(R.id.ivShare);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShareDialogActivity.class);
                intent.putExtra("postId", post.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //            on click on options icon dialog will be open
        ImageView ivOptions = (ImageView) layout.findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.getUser().getId() == CommonData.getInstance().getCurrentUserId()) {
                    intent = new Intent(context, HideDeleteDialogActivity.class);
                } else {
                    intent = new Intent(context, HideReportDialogActivity.class);
                }
                intent.putExtra("postId", post.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//            on click on Coin we open Donate activity and transfer foundation ID
        ImageView ivCoin = (ImageView) layout.findViewById(R.id.ivCoin);
        ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, MakeDonActivity.class);
                intent.putExtra("foundationId", post.getFoundation().getId());
                intent.putExtra("postId", post.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//        with Picasso
        if (post.getImageUrl() != null && !post.getImageUrl().equals("")) {
            ImageView imageView = (ImageView) layout.findViewById(R.id.ivPost);
            Picasso.with(context).load(post.getImageUrl()).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

        //old way with downloaded images
//        if (post.getLocalImagePath() != null) {
//            ImageView imageView = (ImageView) layout.findViewById(R.id.ivPost);
//            File imgFile = new File(post.getLocalImagePath());
//            if (imgFile.exists()) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                imageView.setImageBitmap(myBitmap);
//                imageView.setVisibility(View.VISIBLE);
//            }
//        }

    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        return layout;
    }

    private void changeLikesBadge(Post post) {
        if (post.getLikesCount() == 0) {
            tvLikesBadge.setVisibility(View.INVISIBLE);
        } else {
            tvLikesBadge.setVisibility(View.VISIBLE);
            tvLikesBadge.setText(post.getLikesCount() + "");
        }
    }

    private void changeCommentsBadge(Post post) {
        if (post.getCommentsCount() == 0) {
            tvCommentsBadge.setVisibility(View.INVISIBLE);
        } else {
            tvCommentsBadge.setVisibility(View.VISIBLE);
            tvCommentsBadge.setText(post.getCommentsCount() + "");
        }
    }
}
