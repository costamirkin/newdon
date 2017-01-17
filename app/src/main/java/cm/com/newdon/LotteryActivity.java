package cm.com.newdon;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import cm.com.newdon.adapters.TicketsListAdapter;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.DateHandler;

public class LotteryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        Lottery lottery = CommonData.getInstance().getLotteryList().get(position);

        ImageView imLotteryLogo = (ImageView) findViewById(R.id.imLotteryLogo);
        Picasso.with(this).load(lottery.getLogoUrl()).into(imLotteryLogo);

        TextView tvLotteryTitle = (TextView) findViewById(R.id.tvLotteryTitle);
        tvLotteryTitle.setText(lottery.getTitle());

        TextView tvLotteryPromo = (TextView) findViewById(R.id.tvLotteryPromo);
        tvLotteryPromo.setText(lottery.getPromoText());

        TextView tvLotteryDate = (TextView) findViewById(R.id.tvLotteryDate);
        TextView tvLotteryDay = (TextView) findViewById(R.id.tvLotteryDay);
        Date lotteryDate = lottery.getScheduleDay();

        TextView tvParticipants = (TextView) findViewById(R.id.tvParticipants);
        String patricipation = "";

        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);

        if (lottery.getStatus().equals("finished")){
            tvLotteryDate.setText("Lottery Closed");
            tvLotteryDay.setText(DateHandler.getDaySimpleFormat(lotteryDate));
            tvLotteryDay.setTextColor(getResources().getColor(R.color.blueLottery));
            patricipation = " People Participated";
            tvParticipants.setTextColor(getResources().getColor(R.color.greyLottery));
            if(lottery.isYouWin()){
                tvDescription.setText("Congratulations!");
                findViewById(R.id.tvWinner).setVisibility(View.VISIBLE);
            }else tvDescription.setText("Thanks for participating!");
        } else {
            tvLotteryDate.setText(DateHandler.getTimeCountDown(lotteryDate));
            tvLotteryDay.setText("Days    Hours    Minutes");
            tvLotteryDay.setTextColor(getResources().getColor(R.color.greyLottery));
            patricipation = " People Participating";
            tvParticipants.setTextColor(getResources().getColor(R.color.blueLottery));
            tvDescription.setText(lottery.getDescription());
            tvDescription.setTextColor(Color.BLACK);
            tvDescription.setTextSize(11);
            findViewById(R.id.tvNeedToDo).setVisibility(View.VISIBLE);
            findViewById(R.id.btnDonateNow).setVisibility(View.VISIBLE);
        }

        tvParticipants.setText(lottery.getParticipantCount() + patricipation);

        ImageView imLottery = (ImageView) findViewById(R.id.imLottery);
        Picasso.with(this).load(lottery.getImageUrl()).into(imLottery);

        TextView tvComfort = (TextView) findViewById(R.id.tvComfort);
        tvComfort.setText(lottery.getComfortText());

        ListView lvTickets = (ListView) findViewById(R.id.lvTickets);
        lvTickets.setAdapter(new TicketsListAdapter(this, lottery.getTickets()));
        lvTickets.invalidateViews();
    }

    public void lotteryHistory(View view) {
        DataLoader.getLotteryList();
        startActivity(new Intent(this, LotteryListActivity.class));
    }

//    // TODO: 14.01.2017
//    where to go???
    public void donateNow(View view) {
    }
}
