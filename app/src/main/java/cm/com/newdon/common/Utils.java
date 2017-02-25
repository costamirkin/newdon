package cm.com.newdon.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cz.msebera.android.httpclient.Header;

/**
 * Created by costa on 18/01/17.
 */
public class Utils {
    public static File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public static void followUser(int userId, final Context context) {
        followUser(userId, context, false);
    }

    public static void followUser(int userId, final Context context, boolean unFollow) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        RestClient.put("connections/" + (unFollow ? "unfollow" : "follow"), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String strErr = responseBody == null ? "" : new String(responseBody);
                Toast.makeText(context,
                        "Follow failed. " + strErr, Toast.LENGTH_LONG).show();
                System.out.println(new String(responseBody));

            }
        });
    }

    public static void showAlertDialog(String message, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);

        // set title
        alertDialogBuilder.setTitle(message);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",null);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //return Uri from Bitmap
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //return String realpath from Uri
    public static String getRealPathFromURI(Uri contentURI, ContentResolver resolver) {
        String result;
        Cursor cursor = resolver.query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
