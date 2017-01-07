package cm.com.newdon.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * downloads pics from URL to Bitmap
 */
public class ImageLoaderToBitmap extends AsyncTask<Void, Void, Bitmap>{

    private String stringUrl;
    private int foundationId;
    private DownloadOption option;
    public enum DownloadOption{
        FOUNDATION, POST, DEFAULT_IMAGE
    }

    public ImageLoaderToBitmap(String stringUrl, int foundationId, DownloadOption option) {
        this.stringUrl = stringUrl;
        this.foundationId=foundationId;
        this.option = option;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL url= new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url
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
        switch (option){
            case FOUNDATION:
                CommonData.getInstance().findFoundById(foundationId).setLogo(result);
                break;
        }
    }
}

