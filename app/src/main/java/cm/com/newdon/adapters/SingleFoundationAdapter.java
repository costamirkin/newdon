package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
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


        RelativeLayout rl = (RelativeLayout) View.inflate(context, R.layout.foundation_desc_item, null);

        TextView year       = (TextView) rl.findViewById(R.id.year);
        TextView country    = (TextView) rl.findViewById(R.id.country);
        TextView headquater = (TextView) rl.findViewById(R.id.headquater);
        TextView number     = (TextView) rl.findViewById(R.id.number);
        TextView desciption = (TextView) rl.findViewById(R.id.desciption);



        int foundationId = CommonData.getInstance().getSelectedFoundId();
        Log.e("aaa", "" + foundationId);
        if (foundationId != -1) {
            Foundation f = CommonData.getInstance().findFoundById(foundationId);
            if (f != null) {

                year.setText("" + f.getYearFounded());
                country.setText(f.getAddress());
                number.setText("" + f.getNumber());
                headquater.setText(f.getHeadquarter());

                desciption.setText(f.getDescription());
            }


        }

        return rl;
    }
}
