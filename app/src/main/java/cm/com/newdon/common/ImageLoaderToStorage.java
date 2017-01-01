package cm.com.newdon.common;

import android.content.Context;
import android.os.AsyncTask;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * downloads pics from URL to storage
 */
public class ImageLoaderToStorage extends AsyncTask {

    private String stringUrl;
    private Context context;
    private int postId;

    public ImageLoaderToStorage(String stringUrl, Context context, int postId) {
        this.stringUrl = stringUrl;
        this.context = context;
        this.postId = postId;
    }

    //    ????
    private int aReasonableSize = 1024;

    @Override
    protected Object doInBackground(Object[] params) {
        InputStream input = null;
        try {
        URL url = new URL (stringUrl);
        input = url.openStream();
            String storagePath = context.getApplicationInfo().dataDir;
            OutputStream output = new FileOutputStream(storagePath + "/" + postId+ ".jpg");
            try {
                byte[] buffer = new byte[aReasonableSize];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                    output.write(buffer, 0, bytesRead);
                }
                CommonData.getInstance().findPostById(postId)
                        .setLocalImagePath(storagePath + "/" + postId+ ".jpg");
            } finally {
                output.close();
            }
        } catch (Exception e) {
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (CommonData.getInstance().imageLoadedIf != null) {
            CommonData.getInstance().imageLoadedIf.imageLoaded(postId);
        }
    }
}
