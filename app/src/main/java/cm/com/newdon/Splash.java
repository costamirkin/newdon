package cm.com.newdon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    class MyRunnable implements Runnable {
        private Class<?> cls;

        public MyRunnable(Class<?> cls) {
            this.cls = cls;
        }

        @Override
        public void run() {

            startActivity(new Intent(Splash.this, cls));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences settings = getSharedPreferences("settings", 0);
        String email = settings.getString("email", "");
        String password = settings.getString("password","");
        if (!email.equals("") && !password.equals("") ) {
            RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("password", password);

            RestClient.loginSignup("login",params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(getApplicationContext(),
                            new String(responseBody), Toast.LENGTH_LONG).show();
                    try {
                        JSONObject object = new JSONObject(new String(responseBody));
                        CommonData.getInstance().setToken(object.getString("token"));

                        new Handler().postDelayed(new MyRunnable(BottomBarActivity.class),
                                SPLASH_TIME_OUT);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (responseBody == null) {
                        Toast.makeText(getApplicationContext(),
                                "Login failed: No internet connection", Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Login failed: " + new String(responseBody), Toast.LENGTH_LONG).show();
                    }

                    new Handler().postDelayed(new MyRunnable(SignAcitvity.class), SPLASH_TIME_OUT);
                }
            });

        }
        else {
            new Handler().postDelayed(new MyRunnable(SignAcitvity.class), SPLASH_TIME_OUT);

        }

    }
}
