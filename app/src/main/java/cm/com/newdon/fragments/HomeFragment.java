package cm.com.newdon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cm.com.newdon.R;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btn = (Button) view.findViewById(R.id.btnGet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPostWithImage(v, 1111111);
            }
        });
        return view;
    }


    public void getPostWithImage(final View view, int postID) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("*********************");
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    ImageView im = (ImageView) view.findViewById(R.id.imTest);
//                    im.setImageBitmap();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("*********************");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        //params.put("userId", 158);

        RestClient.get("posts/view?id=" + postID, params, handler);


    }
}
