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

import cm.com.newdon.DonateActivity;
import cm.com.newdon.R;
import cm.com.newdon.ShareDialogActivity;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DateHandler;
import cm.com.newdon.fragments.HomeFragment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marina on 17.12.2016.
 */
public class SingleFoundationAdapter extends BaseAdapter {

    private Context context;



    public SingleFoundationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
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

        TextView tv = new TextView(context);
        tv.setTextSize(20);
        tv.setTextColor(Color.BLACK);

        int foundationId = CommonData.getInstance().getSelectedFoundId();
        if (foundationId != -1) {
            Foundation f = CommonData.getInstance().findFoundById(foundationId);
            if (f != null && f.getDescription() != null) {

                tv.setText(f.getDescription());
            }
            else {
                tv.setText("dddd");
            }

        }
        return tv;
    }
}
