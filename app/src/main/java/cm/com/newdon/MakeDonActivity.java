package cm.com.newdon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class MakeDonActivity extends AppCompatActivity {

    private Post post;
    private Foundation foundation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_don);

        post = CommonData.getInstance().getTempPost();
        foundation = post.getFoundation();

        TextView tvFound = (TextView) findViewById(R.id.tvFoundTitle);
        tvFound.setText(foundation.getTitle());
    }

    public void makeDone(View view) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(),"YOU made a DON!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("foundationId", foundation.getId());
        params.put("amount", getAmount());
        params.put("comment", post.getMessage());

//        image???
//        params.put("image",post.getImageUrl());

        RestClient.post("foundations/donate", params, handler);

    }

    private int getAmount(){
        EditText etAmount = (EditText) findViewById(R.id.etAmount);
        String text = etAmount.getText().toString();
        return Integer.valueOf(text.subSequence(1, text.length()).toString());
    }
}
