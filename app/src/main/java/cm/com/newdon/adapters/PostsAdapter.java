package cm.com.newdon.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cm.com.newdon.R;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;

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
            layout = (RelativeLayout) inflater.inflate(R.layout.lottery,parent,false);
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
        }
        return layout;
    }
}
