package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cm.com.newdon.adapters.FoundationsAdapter;
import cm.com.newdon.classes.FoundCategory;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.JsonHandler;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class FoundationGrid extends AppCompatActivity {

    private FoundationsAdapter adapter;
    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundation_grid);

//        to hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//        getAllFoundations();

        adapter = new FoundationsAdapter(getApplicationContext());
        gv = (GridView) findViewById(R.id.gvFounds);
        gv.setAdapter(adapter);
        gv.invalidate();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          open Donate page
//          transfer foundation id
                System.out.println("PRESS!!!!!!!!!!!!!!!!!!");

                Intent intent = new Intent(getApplicationContext(), DonateActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });


    }



}
