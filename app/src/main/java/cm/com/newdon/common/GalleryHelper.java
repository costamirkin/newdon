package cm.com.newdon.common;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Marina on 23.12.2016.
 */
public class GalleryHelper {
    public static Intent openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }

    public static Bitmap getRotatedImage(Context context, Bitmap currentBitmap, Uri uri){
        Bitmap bitmap = null;
        int orientation = getOrientation(context, uri);
        Matrix matrix = new Matrix();
        if (orientation != 0) {
            matrix.preRotate(orientation);
        }
        bitmap = Bitmap.createBitmap(currentBitmap, 0, 0, currentBitmap.getWidth(), currentBitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static int getOrientation(Context context, Uri photoUri) {
    /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);
        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
