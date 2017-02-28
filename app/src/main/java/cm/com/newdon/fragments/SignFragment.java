package cm.com.newdon.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cm.com.newdon.BottomBarActivity;
import cm.com.newdon.ChooseImageActivity;
import cm.com.newdon.R;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by costa on 17/12/16.
 */

public class SignFragment extends Fragment {
    private Button   button;
    private EditText name;
    private EditText fullName;
    private EditText emailEt;
    private EditText pswdEt;
    private EditText pswd2Et;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v   = inflater.inflate(R.layout.fragment_sign,container,false);
        button   = (Button) v.findViewById(R.id.loginBtn);
        name     = (EditText) v.findViewById(R.id.userName);
        fullName = (EditText) v.findViewById(R.id.fullName);
        emailEt  = (EditText) v.findViewById(R.id.email);
        pswdEt   = (EditText) v.findViewById(R.id.password);
        pswd2Et  = (EditText) v.findViewById(R.id.password2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pswd  = pswdEt.getText().toString();
                String pswd2 = pswd2Et.getText().toString();

                if (!pswd.equals(pswd2)) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "PASSWORD DOESNT MATCH!", Toast.LENGTH_LONG).show();
                    return;

                }

                RequestParams params = new RequestParams();
                params.put("username", name.getText().toString());
                params.put("name", fullName.getText().toString());
                params.put("email", emailEt.getText().toString());
                params.put("password", pswdEt.getText().toString());
                params.put("confirmedPassword", pswd2Et.getText().toString());

                RestClient.loginSignup("signup", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                new String(responseBody), Toast.LENGTH_LONG).show();
                        try {
                            JSONObject object = new JSONObject(new String(responseBody));
                            CommonData.getInstance().setToken(object.getString("token"));
                            SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("email", emailEt.getText().toString());
                            editor.putString("password", pswdEt.getText().toString());
                            editor.commit();
                            Intent intent = new Intent(getActivity(), ChooseImageActivity.class);
                            startActivity(intent);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Login failed: " + new String(responseBody), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
        return v;
    }
}