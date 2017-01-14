package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

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

        adapter = new FoundationsAdapter(getApplicationContext());
        gv = (GridView) findViewById(R.id.gvFounds);
        gv.setAdapter(adapter);
        gv.invalidate();

        final EditText search = (EditText) findViewById(R.id.etSearchFound);
        // Capture Text in EditText
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          open Donate page
//          transfer foundation id
                Intent intent = new Intent(getApplicationContext(), DonateActivity.class);
                int foundationId = (int) adapter.getItemId(position);
                intent.putExtra("foundationId", foundationId);
                startActivity(intent);
            }
        });
    }
}
