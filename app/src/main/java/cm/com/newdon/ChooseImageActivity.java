package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.GalleryHelper;
import cm.com.newdon.common.RestClient;
import cm.com.newdon.common.Utils;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChooseImageActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private Uri profileImageUri = null; // Profile image uri
    private TextView helloTv;
    private boolean  imageChoosen = false;
    private static final int REQUESTCAMERA = 0;
    private static final int REQUESTGALLERY = 1;
    private static final int PIC_CROP = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        profileImage = (CircleImageView) findViewById(R.id.image);
        helloTv = (TextView) findViewById(R.id.helloTv);
        helloTv.setText("Hello " + CommonData.myName);
    }

    public void attachImage(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.attach_image_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.camera:
                        capture();
                        return true;
                    case R.id.gallery:
                        gallery(item);
                        return true;
                    default:
                        return true;
                }
            }
        });
        popup.show();
    }

    public void gallery(MenuItem item) {
        //open gallery
        Intent intent = GalleryHelper.openGallery();
        startActivityForResult(intent, REQUESTGALLERY);
    }


    public void capture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                    CommonData.profileImageName);
            profileImageUri = Uri.fromFile(output);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, profileImageUri);
            //start camera intent
            startActivityForResult(intent, REQUESTCAMERA);
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
        if (imageChoosen) {
            Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
            intent.putExtra("signup", "signup");
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
        else {
            Utils.showAlertDialog("Please select a photo or skip", getApplicationContext());

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imageChoosen = true;
            if (requestCode == REQUESTCAMERA) {
                profileImage.setImageURI(null);
                profileImage.setImageURI(profileImageUri);

            }
            else if (requestCode == REQUESTGALLERY) {
                profileImageUri = data.getData();

                if (profileImageUri != null) {
                    CommonData.getInstance().setTempPicUri(profileImageUri);
                    profileImage.setImageURI(null);
                    profileImage.setImageURI(profileImageUri);
                }
            }
        }
    }
}
