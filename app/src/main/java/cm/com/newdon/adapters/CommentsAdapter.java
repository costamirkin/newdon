package cm.com.newdon.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        Comment comment = CommonData.getInstance().getComments().get(position);

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

        return layout;
    }
}
