package cm.com.newdon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
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
                            token = object.getString("token");
                            System.out.println(token);
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

        RequestParams params = new RequestParams();
        params.put("foundationId", 1);
        params.put("amount", 10);


        client.addHeader("token", token);


        client.post("http://donation.s2.ideas-implemented.com/api/foundations/donate", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        Toast.makeText(getApplicationContext(), new String(response), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_LONG).show();

            }
        };

        RequestParams params = new RequestParams();
        params.put("token", token);

        RestClient.get("foundations/get", params, handler);
    }
}
