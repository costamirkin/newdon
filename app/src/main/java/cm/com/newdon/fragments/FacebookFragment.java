package cm.com.newdon.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cm.com.newdon.BottomBarActivity;
import cm.com.newdon.R;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by costa on 17/12/16.
 */

public class FacebookFragment extends Fragment {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.facebook,container,false);
        loginButton = (LoginButton) v.findViewById(R.id.fbLogin);
        loginButton.setFragment(this   );
        loginButton.setReadPermissions(Arrays.asList(
                "user_friends", "public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                RequestParams params = new RequestParams();
                params.put("token", token.getToken());

                RestClient.post("account/connect", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(new String(responseBody));
                            CommonData.getInstance().setToken(object.getString("token"));
                            SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
                            SharedPreferences.Editor editor = settings.edit();
//                            editor.putString("email", emailEt.getText().toString());
//                            editor.putString("password", pswdEt.getText().toString());
                            editor.commit();
                            startActivity(new Intent(getActivity(), BottomBarActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Facebook Login failed", Toast.LENGTH_LONG).show();
                    }
                });


//               new GraphRequest(
//                        AccessToken.getCurrentAccessToken(),
//                        "/me/invitable_friends",
//                        null,
//                        HttpMethod.GET,
//                        new GraphRequest.Callback() {
//                            public void onCompleted(GraphResponse response) {
//                                System.out.println(response.toString());
//                                try {
//                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
//                                    System.out.println(rawName.length());
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//                ).executeAsync();
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.v("LoginActivity", response.toString());
//
//                                // Application code
//                                try {
//                                    String email = object.getString("email");
//                                    String birthday = object.getString("birthday"); // 01/31/1980 format
//                                    System.out.println(object.toString());
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,link");
//                request.setParameters(parameters);
//                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("Error " + error.getMessage());

            }
        });
        return v;
    }
}