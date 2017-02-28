package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import cm.com.newdon.common.CommonData;
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
        if (profileImageUri != null) {

        }
        Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
        intent.putExtra("signup","signup");
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
