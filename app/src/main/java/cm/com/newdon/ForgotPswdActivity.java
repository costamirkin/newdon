package cm.com.newdon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class ForgotPswdActivity extends AppCompatActivity {

    private EditText emailEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pswd);

    }

    public void cancel(View view) {
        finish();
    }

    public void send(View view) {
        emailEt     = (EditText) findViewById(R.id.email);

        RequestParams params = new RequestParams();
        params.put("email", emailEt.getText().toString());

        RestClient.loginSignup("forgot", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(),
                        "New password was send to your mail", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),
                        "Wrong email, enter email again", Toast.LENGTH_LONG).show();

            }
        });

    }
}
