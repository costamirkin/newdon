package cm.com.newdon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cm.com.newdon.R;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.common.CommonData;

/**
 * Created by Marina on 06.01.2017.
 */
public class LotteryListAdapter extends BaseAdapter{

    private Context context;

    public LotteryListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return CommonData.getInstance().getLotteryList().size();
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
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.lottery_item_for_lv, parent, false);

        Lottery lottery = CommonData.getInstance().getLotteryList().get(position);

        ImageView imLotteryLogo = (ImageView) layout.findViewById(R.id.imLotteryLogo);
        Picasso.with(context).load(lottery.getLogoUrl()).into(imLotteryLogo);

        TextView tvLotteryTitle = (TextView) layout.findViewById(R.id.tvLotteryTitle);
        tvLotteryTitle.setText(lottery.getTitle());

        TextView tvLotteryDate = (TextView) layout.findViewById(R.id.tvLotteryDate);
//        tvLotteryDate.setText(lottery.get());
        // TODO: 06.01.2017

        TextView tvParticipants = (TextView) layout.findViewById(R.id.tvParticipants);
        tvParticipants.setText(lottery.getParticipantCount()+" Participants");

        TextView tvStatus = (TextView) layout.findViewById(R.id.tvStatus);
        tvStatus.setText(lottery.getStatus());

        ImageView imStatus = (ImageView) layout.findViewById(R.id.imStatus);
        // TODO: 06.01.2017

        return layout;
    }
}
