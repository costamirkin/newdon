package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth("egenesis", "rhk@Wf54");

        RequestParams params = new RequestParams();
        params.put("email", "john_doe1@mail.com");
        params.put("password", "123123");

        client.post("http://donation.s2.ideas-implemented.com/api/account/login", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        Toast.makeText(getApplicationContext(), new String(response), Toast.LENGTH_LONG).show();
                        try {
                            JSONObject object = new JSONObject(new String(response));
                            CommonData.getInstance().setToken(object.getString("token"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        System.out.println(errorResponse.toString());
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });

    }


    public void test1(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth("egenesis", "rhk@Wf54");
        client.addHeader("Authorization", "Basic " + CommonData.getInstance().getToken());

        RequestParams params = new RequestParams();
        params.put("foundationId", 1);
        params.put("amount", 100);


        client.post("http://donation.s2.ideas-implemented.com/api/foundations/donate", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        TextView tv = (TextView) findViewById(R.id.textView);
                        tv.setText(new String(response));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        System.out.println(errorResponse.toString());
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });

    }


    public void found(View view) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                TextView tv = (TextView) findViewById(R.id.textView);
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    tv.setText("" + array.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_LONG).show();

            }
        };

        RequestParams params = new RequestParams();
        //params.put("userId", 158);

        RestClient.get("foundations/find", params, handler);
    }

    public void home(View view) {
        startActivity(new Intent(this, BottomBarActivity.class));
    }
}
