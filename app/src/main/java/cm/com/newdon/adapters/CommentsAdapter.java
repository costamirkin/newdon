package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cm.com.newdon.EditDialogActivity;
import cm.com.newdon.HideReportDialogActivity;
import cm.com.newdon.R;
import cm.com.newdon.classes.Comment;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DateHandler;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marina on 27.01.2017.
 */
public class CommentsAdapter extends BaseAdapter{

    Context context;

    public CommentsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return CommonData.getInstance().getComments().size();
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
        RelativeLayout layout;
        if (convertView != null) {
            layout = (RelativeLayout) convertView;
        } else {
            layout = (RelativeLayout) View.inflate(context, R.layout.comment_item_for_lv, null);
        }

        final Comment comment = CommonData.getInstance().getComments().get(position);

        CircleImageView ivUser = (CircleImageView) layout.findViewById(R.id.ivUser);
        if (comment.getUser().getPictureUrl() != null && !comment.getUser().getPictureUrl().equals("")) {
            Picasso.with(context).load(comment.getUser().getPictureUrl()).into(ivUser);
        }

        TextView tvUserName = (TextView) layout.findViewById(R.id.tvUserName);
        tvUserName.setText(comment.getUser().getRealName());

        TextView tvCommentTime = (TextView) layout.findViewById(R.id.tvCommentTime);
        tvCommentTime.setText(DateHandler.howLongAgoWasDate(comment.getDate()));

        TextView tvComment = (TextView) layout.findViewById(R.id.tvComment);
        tvComment.setText(comment.getText());

        //            on click on options icon dialog will be open
        ImageView ivOptions = (ImageView) layout.findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (comment.getUser().getId() == CommonData.getInstance().getCurrentUserId()) {
                    intent = new Intent(context, EditDialogActivity.class);
                } else {
//                    // TODO: 30.01.2017
                    //need to change dialog for comment
                    intent = new Intent(context, HideReportDialogActivity.class);
                }
                intent.putExtra("commentId", comment.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return layout;
    }
}
