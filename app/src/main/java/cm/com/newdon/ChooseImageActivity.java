package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cm.com.newdon.common.Utils;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChooseImageActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private Uri profileImageUri = null; // Profile image uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        profileImage = (CircleImageView) findViewById(R.id.image);
    }

    public void changeImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                    CommonData.profileImageName);
            profileImageUri = Uri.fromFile(output);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, profileImageUri);
            //start camera intent
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void skip(View view) {
        Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
        intent.putExtra("signup","signup");
        startActivity(intent);
    }

    public void save(View view) {
        Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
        intent.putExtra("signup","signup");
        if (profileImageUri != null) {
            RequestParams imageParams = new RequestParams();
            try {
                String str = Utils.getRealPathFromURI(profileImageUri, getApplicationContext().getContentResolver());
                imageParams.put("image", new File(str));
                RestClient.post("account/photo", imageParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Utils.showAlertDialog("IMAGE CHANGED", ChooseImageActivity.this);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Utils.showAlertDialog("IMAGE CHANGED " + responseBody != null ? new String(responseBody) : "", ChooseImageActivity.this);

                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                profileImage.setImageURI(null);
                profileImage.setImageURI(profileImageUri);

            }
        }
    }
}
