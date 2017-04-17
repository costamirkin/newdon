package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import cm.com.newdon.LotteryActivity;
import cm.com.newdon.R;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DateHandler;

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
    public Object instantiateItem(ViewGroup collection, final int position) {
        Lottery lottery = featuredLotteries.get(position);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.lottery_item_for_vp, collection, false);

        ImageView imLotteryLogo = (ImageView) view.findViewById(R.id.imLotteryLogo);
        Picasso.with(context).load(lottery.getLogoUrl()).into(imLotteryLogo);

        TextView tvLotteryTitle = (TextView) view.findViewById(R.id.tvLotteryTitle);
        tvLotteryTitle.setText(lottery.getTitle());

        TextView tvLotteryPromo = (TextView) view.findViewById(R.id.tvLotteryPromo);
        tvLotteryPromo.setText(lottery.getPromoText());

        TextView tvLotteryDate = (TextView) view.findViewById(R.id.tvLotteryDate);
        TextView tvLotteryDay = (TextView) view.findViewById(R.id.tvLotteryDay);
        Date lotteryDate = lottery.getScheduleDay();

        TextView tvParticipants = (TextView) view.findViewById(R.id.tvParticipants);
        String patricipation = "";

        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);

        if (lottery.getStatus().equals("finished")){
            tvLotteryDate.setText("Lottery Closed");
            tvLotteryDay.setText(DateHandler.getDaySimpleFormat(lotteryDate));
            tvLotteryDay.setTextColor(context.getResources().getColor(R.color.blueLottery));
            patricipation = " People Participated";
            tvParticipants.setTextColor(context.getResources().getColor(R.color.greyLottery));
            if(lottery.isYouWin()){
                tvDescription.setText("Congratulations!");
                view.findViewById(R.id.tvWinner).setVisibility(View.VISIBLE);
            }else tvDescription.setText("Thanks for participating!");
        } else {
            tvLotteryDate.setText(DateHandler.getTimeCountDown(lotteryDate));
            tvLotteryDay.setText("Days    Hours    Minutes");
            tvLotteryDay.setTextColor(context.getResources().getColor(R.color.greyLottery));
            patricipation = " People Participating";
            tvParticipants.setTextColor(context.getResources().getColor(R.color.blueLottery));
            tvDescription.setText(lottery.getDescription());
            tvDescription.setTextColor(Color.BLACK);
            tvDescription.setTextSize(11);
        }

        tvParticipants.setText(lottery.getParticipantCount()+patricipation);

        Button openLotteryButton = (Button) view.findViewById(R.id.btnOpenLottery);
        openLotteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LotteryActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("isFeatured", true);
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
