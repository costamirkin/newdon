package cm.com.newdon.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cm.com.newdon.R;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class ProfileFragment extends Fragment {

    private Button saveButton;
    private Button passwordButton;
    private EditText currPswdEd;
    private EditText newPswdEd;
    private EditText new1PswdEd;
    private EditText nameEt;
    private EditText nameRealEt;
    private EditText emailEt;
    private ToggleButton toggleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_profile,container,false);

        currPswdEd   = (EditText) v.findViewById(R.id.currPswd);
        newPswdEd    = (EditText) v.findViewById(R.id.newPswd);
        new1PswdEd   = (EditText) v.findViewById(R.id.newPswd1);
        nameEt       = (EditText) v.findViewById(R.id.nameEt);
        nameRealEt   = (EditText) v.findViewById(R.id.nameRealEt);
        emailEt      = (EditText) v.findViewById(R.id.emailEt);
        toggleButton = (ToggleButton) v.findViewById(R.id.privacyToggle);


        saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("username", "Costa");
                params.put("firstName", nameEt.getText().toString());
                params.put("lastName", nameRealEt.getText().toString());
                params.put("email", emailEt.getText().toString());
                params.put("isPrivate", toggleButton.isChecked());

                RestClient.post("account/profile", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_LONG).show();
                        SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("firstName", nameEt.getText().toString());
                        editor.putString("lastName", nameRealEt.getText().toString());
                        editor.putString("email", emailEt.getText().toString());
                        editor.putBoolean("isPrivate", toggleButton.isChecked());
                        editor.commit();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("Details", new String(responseBody));

                    }
                });

            }
        });

        passwordButton = (Button) v.findViewById(R.id.passwordButton);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curPswd = currPswdEd.getText().toString();
                String newPswd = newPswdEd.getText().toString();
                String new1Pswd = new1PswdEd.getText().toString();

                if (curPswd.length() < 6 || newPswd.length() < 6 || new1Pswd.length() < 6) {
                    Toast.makeText(getActivity(), "Password to short", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!new1Pswd.equals(newPswd)) {
                    Toast.makeText(getActivity(), "Password doesnt match", Toast.LENGTH_LONG).show();
                    return;
                }

                RequestParams params = new RequestParams();
                params.put("oldPassword", curPswd);
                params.put("password", newPswd);
                params.put("confirmedPassword", new1Pswd);

                RestClient.post("account/change-password", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password", newPswdEd.getText().toString());
                        editor.commit();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("Posts", new String(responseBody));

                    }
                });


            }
        });

        return v;
    }
}
