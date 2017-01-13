package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cm.com.newdon.LotteryActivity;
import cm.com.newdon.R;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.common.CommonData;

/**
 * Created by Marina on 12.01.2017.
 */
public class LotteryViewPagerAdapter extends PagerAdapter {

    private List<Lottery> featuredLotteries;
    private LayoutInflater inflater;
    private Context context;

    public LotteryViewPagerAdapter(Context context) {
        featuredLotteries = CommonData.getInstance().getFeaturedLotteries();
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        Lottery lottery = featuredLotteries.get(position);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.lottery_item_for_vp, collection, false);

        TextView tvLotteryTitle = (TextView) view.findViewById(R.id.tvLotteryTitle);
        tvLotteryTitle.setText(lottery.getTitle());

        TextView tvLotteryPromo = (TextView) view.findViewById(R.id.tvLotteryPromo);
        tvLotteryPromo.setText(lottery.getPromoText());

        ImageView imLotteryLogo = (ImageView) view.findViewById(R.id.imLotteryLogo);
        Picasso.with(context).load(lottery.getLogoUrl()).into(imLotteryLogo);

        Button openLotteryButton = (Button) view.findViewById(R.id.btnOpenLottery);
        openLotteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LotteryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        collection.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return CommonData.getInstance().getFeaturedLotteries().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
