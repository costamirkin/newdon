package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cm.com.newdon.classes.Lottery;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoader;

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
        if (lottery.getStatus().equals("finished")){
            tvLotteryDate.setText("Lottery closed");
        }
//        tvLotteryDate.setText(lottery.get());
        // TODO: 06.01.2017

        TextView tvParticipants = (TextView) findViewById(R.id.tvParticipants);
        tvParticipants.setText(lottery.getParticipantCount()+" Participants");

//        TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
//        tvStatus.setText(lottery.getStatus());
        
        ImageView imLottery = (ImageView) findViewById(R.id.imLottery);
        Picasso.with(this).load(lottery.getImageUrl()).into(imLottery);
    }

    public void lotteryHistory(View view) {
        DataLoader.getLotteryList();
        startActivity(new Intent(this, LotteryListActivity.class));
    }
}
