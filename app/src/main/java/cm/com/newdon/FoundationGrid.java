package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import cm.com.newdon.adapters.FoundationsAdapter;
import cm.com.newdon.classes.Foundation;

public class FoundationGrid extends AppCompatActivity {

    private FoundationsAdapter adapter;
    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundation_grid);

        adapter = new FoundationsAdapter(getApplicationContext());
        gv = (GridView) findViewById(R.id.gvFounds);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          open Donate page
//          transfer foundation id
                startActivity(new Intent(FoundationGrid.this,DonateActivity.class));
            }
        });
    }
}
