package cm.com.newdon.common;

import android.os.Environment;

import java.io.File;

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

}
