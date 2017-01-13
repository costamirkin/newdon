package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class MakeDonActivity extends AppCompatActivity {

    private EditText etAmount;
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

        etAmount = (EditText) findViewById(R.id.etAmount);
        etAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    setAmount(0);
                }
                return false;
            }
        });
    }

    public void makeDone(View view) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(),"YOU made a DON!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), BottomBarActivity.class));
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
        if(post.getUri()!=null) {
            try {
                params.put("image", new File(post.getUri()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

//        params.put("postId", 1111111);

        RestClient.post("foundations/donate", params, handler);

    }

    private int getAmount(){
        String text = etAmount.getText().toString();
        int amount = Integer.valueOf(text.subSequence(1, text.length()).toString());
        System.out.println("!!!!!!!PAY " + amount);
        return amount;
    }

    private void setAmount(int amount){
        String text = "₪"+amount;
        etAmount.setText(text);
    }

    public void clearAmount(View view) {
        setAmount(0);
    }

    public void coin5(View view) {
        setAmount(5);
    }

    public void coin10(View view) {
        setAmount(10);
    }

    public void coin18(View view) {
        setAmount(18);
    }

    public void coin50(View view) {
        setAmount(50);
    }

    public void creditCard(View view) {
        startActivity(new Intent(getApplicationContext(), CardActivity.class));
    }
}
