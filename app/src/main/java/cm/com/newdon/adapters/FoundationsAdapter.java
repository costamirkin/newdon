package cm.com.newdon.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cm.com.newdon.R;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.common.CommonData;

/**
 * Created by costa on 16/12/16.
 */
public class FoundationsAdapter extends BaseAdapter {

    private Context context;

    public FoundationsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return CommonData.getInstance().getFoundations().size();
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

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.foundation, parent, false);
        Foundation foundation = CommonData.getInstance().getFoundations().get(position);

        ImageView imLogo = (ImageView) ll.findViewById(R.id.imFoundLogo);
        imLogo.setImageBitmap(foundation.getLogo());

        TextView tvTitle = (TextView) ll.findViewById(R.id.tvFoundTitle);
        tvTitle.setText(foundation.getTitle());
        tvTitle.setTextColor(Color.parseColor(foundation.getCategory().getColor()));

        TextView tvAdress = (TextView) ll.findViewById(R.id.tvFoundAdress);
        tvAdress.setText(foundation.getAddress());


        TextView tvCategory = (TextView) ll.findViewById(R.id.txFoundCategoryName);
        tvCategory.setText(foundation.getCategory().getName());
        tvCategory.setTextColor(Color.parseColor(foundation.getCategory().getColor()));

        return ll;
    }
}
