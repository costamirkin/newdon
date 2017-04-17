package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cm.com.newdon.BottomBarActivity;
import cm.com.newdon.R;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.common.CommonData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter for foundation grid
 */
public class FoundationsAdapter extends BaseAdapter {

    private Context context;
    private List<Foundation> foundationsAfterSearch;
    private List<Foundation> foundationsAll;
    private boolean  donateStep;

    public FoundationsAdapter(Context context,  boolean donateStep) {
        this.context = context;
        foundationsAll = CommonData.getInstance().getFoundations();
        foundationsAfterSearch = new ArrayList<>();
        foundationsAfterSearch.addAll(foundationsAll);
        this.donateStep = donateStep;
    }

    @Override
    public int getCount() {
        return foundationsAfterSearch.size();
    }

    @Override
    public Object getItem(int position) {
        return foundationsAfterSearch.get(position);
    }

    @Override
    public long getItemId(int position) {
        return foundationsAfterSearch.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.foundation, parent, false);
        final Foundation foundation = foundationsAfterSearch.get(position);

//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonData.bottomBarActivity.onFoundationSelected(foundation.getId());
//            }
//        });
//
        CircleImageView imLogo = (CircleImageView) layout.findViewById(R.id.imFoundLogo);
        imLogo.setImageBitmap(foundation.getLogo());

        TextView tvTitle = (TextView) layout.findViewById(R.id.tvFoundTitle);
        tvTitle.setText(foundation.getTitle());

        TextView tvAddress = (TextView) layout.findViewById(R.id.tvFoundAddress);
        tvAddress.setText(foundation.getAddress());

        if (donateStep) {

            ImageView imFoundationInfo = (ImageView) layout.findViewById(R.id.imFoundationInfo);
            imFoundationInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BottomBarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("foundId", foundation.getId());
                    context.startActivity(intent);
                }
            });
        }

        TextView tvCategory = (TextView) layout.findViewById(R.id.txFoundCategoryName);
        tvCategory.setText(foundation.getCategory().getName());
        int categoryColor = Color.parseColor(foundation.getCategory().getColor());
        tvCategory.setTextColor(categoryColor);
        GradientDrawable drawable = (GradientDrawable)tvCategory.getBackground();
        drawable.setStroke(1, categoryColor);

        return layout;
    }

//  filter the data
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        foundationsAfterSearch.clear();
        if (charText.length() == 0) {
            foundationsAfterSearch.addAll(foundationsAll);
        } else {
            for (Foundation foundation : foundationsAll) {
                if (foundation.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    foundationsAfterSearch.add(foundation);
                }
            }
        }
        notifyDataSetChanged();
    }
}
