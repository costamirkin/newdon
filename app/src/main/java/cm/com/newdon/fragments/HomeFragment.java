package cm.com.newdon.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cm.com.newdon.R;
import cm.com.newdon.adapters.PostsAdapter;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.JsonHandler;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment {
    ProgressDialog progressDialog;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        lv = (ListView) view.findViewById(R.id.lvPosts);

//        progressDialog = ProgressDialog.show(getActivity().getApplicationContext(),"Altru","Udpadting posts...",true);
        getUserPosts(lv);
        return view;
    }


    public void getUserPosts(final ListView lv) {

        CommonData.getInstance().getPosts().clear();
        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("*********************");
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        Post post = JsonHandler.parsePostFromJson(array.getJSONObject(i));
                        System.out.println(post);
                        CommonData.getInstance().getPosts().add(post);
                    }

//                    ImageView im = (ImageView) view.findViewById(R.id.imTest);
//                    new ImageLoadTask(post.getImageUrl(),im).execute();
//                    im.setImageBitmap();
                    lv.setAdapter(new PostsAdapter(getActivity().getApplicationContext()));
                    lv.invalidate();
//                    progressDialog.dismiss();

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
        params.put("userId", CommonData.getInstance().getCurrentUserId());

        RestClient.get("users/posts", params, handler);
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url,ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
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
            imageView.setImageBitmap(result);
        }
    }
}
