package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cm.com.newdon.adapters.LotteryListAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;

public class LotteryListActivity extends AppCompatActivity implements DataLoadedIf{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_list);
        CommonData.getInstance().imageLoadedIf =  this;

        listView = (ListView) findViewById(R.id.lvLotteryHistory);
        LotteryListAdapter adapter = new LotteryListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LotteryActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void imageLoaded(int postId) {

    }

    @Override
    public void dataLoaded() {
        listView.invalidateViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CommonData.getInstance().imageLoadedIf = null;
    }
}
