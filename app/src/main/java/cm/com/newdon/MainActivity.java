package cm.com.newdon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.JsonHandler;
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


    public void getAllFoundations(View view) {
        CommonData.getInstance().getFoundations().clear();

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        Foundation foundation = JsonHandler.parseFoundationFromJson(array.getJSONObject(i));
                        new ImageLoadTask(foundation.getLogoUrl(),foundation.getId()).execute();
                        CommonData.getInstance().getFoundations()
                                .add(foundation);
                    }
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

        Toast.makeText(getApplicationContext(), new String("All foundations downloaded"),Toast.LENGTH_SHORT).show();
    }

    public void home(View view) {
        startActivity(new Intent(this, BottomBarActivity.class));
    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private int foundationId;

        public ImageLoadTask(String url, int foundationId) {
            this.url = url;
            this.foundationId=foundationId;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            CommonData.getInstance().findFoundById(foundationId).setLogo(result);
        }
    }
}
