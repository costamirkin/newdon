package cm.com.newdon.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by costa on 24/12/16.
 */
public class ImageLoader extends AsyncTask<Void, Void, Bitmap>{


    private String url;
    private int foundationId;

    public ImageLoader(String url, int foundationId) {
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

