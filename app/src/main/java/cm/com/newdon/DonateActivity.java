package cm.com.newdon;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.GalleryHelper;
import cm.com.newdon.common.Utils;

public class DonateActivity extends AppCompatActivity implements DataLoadedIf {

    private static final int REQUESTCAMERA = 0;
    private static final int REQUESTGALLERY = 1;
    private static final int PIC_CROP = 2;
    private Uri pUri;
    private Bitmap currentBitmap;
    private Foundation foundation;
    Intent intent;

    ImageView imageMain;
    ImageView defaultImage1;
    ImageView defaultImage2;
    ImageView defaultImage3;
    ImageView defaultImage4;
    ImageView defaultImage5;
    ImageView[] defaultImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        CommonData.getInstance().imageLoadedIf = this;

        //to hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        intent = getIntent();
        int foundationId = intent.getIntExtra("foundationId", 0);
        foundation = CommonData.getInstance().findFoundById(foundationId);

        DataLoader.getFoundationData(foundation.getId());

        TextView tvTitle = (TextView) findViewById(R.id.tvFoundTitle);
        tvTitle.setText(foundation.getTitle());
        tvTitle.setTextColor(Color.parseColor(foundation.getCategory().getColor()));

        ImageView imFoundLogo = (ImageView) findViewById(R.id.imFound);
        imFoundLogo.setImageBitmap(foundation.getLogo());

        initImages();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CommonData.getInstance().imageLoadedIf = null;
    }

    private void initImages() {
        imageMain = (ImageView) findViewById(R.id.imPost);
        defaultImage1 = (ImageView) findViewById(R.id.image1);
        defaultImage2 = (ImageView) findViewById(R.id.image2);
        defaultImage3 = (ImageView) findViewById(R.id.image3);
        defaultImage4 = (ImageView) findViewById(R.id.image4);
        defaultImage5 = (ImageView) findViewById(R.id.image5);
        defaultImages = new ImageView[]{defaultImage1, defaultImage2, defaultImage3, defaultImage4, defaultImage5};

        for (int i = 0; i < defaultImages.length; i++) {
            final int finalI = i;
            defaultImages[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Picasso.with(getApplicationContext())
                            .load(foundation.defaultPicsUrl[finalI]).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            imageMain.setImageBitmap(bitmap);
                            currentBitmap = bitmap;
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
                }
            });
        }
    }

    /*go to the next step - make a Don
    need to transfer foundation, comment and image
    */
    public void next(View view) {
        Post tempPost = new Post();
        tempPost.setFoundation(foundation);
        tempPost.setMessage(((EditText) findViewById(R.id.etComment)).getText().toString());

        if (foundation.getLogo() != null) {
            ImageView imLogo = (ImageView) findViewById(R.id.imFound);
            imLogo.setImageBitmap(foundation.getLogo());
        }

        CommonData.getInstance().setTempPost(tempPost);

        if (currentBitmap != null) {
            Uri uri = Utils.getImageUri(getApplicationContext(), currentBitmap);
            if (uri != null) {
                CommonData.getInstance().getTempPost().setUri(Utils.getRealPathFromURI(uri, getContentResolver()));
            }
        }

        intent = new Intent(getApplicationContext(), MakeDonActivity.class);
        startActivity(intent);
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
                        capture(item);
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


    public void capture(MenuItem item) {
        //open camera
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo;
        try {
            // place where to store camera taken picture
            photo = this.createTemporaryFile("picture", ".jpg");
            photo.delete();
            pUri = Uri.fromFile(photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pUri);
            //start camera intent
            startActivityForResult(intent, REQUESTCAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public void gallery(MenuItem item) {
        //open gallery
        intent = GalleryHelper.openGallery();
        startActivityForResult(intent, REQUESTGALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCAMERA) {
                if (pUri != null) {
                    CommonData.getInstance().setTempPicUri(pUri);
                    intent = new Intent(this, CropActivity.class);
                    startActivityForResult(intent, PIC_CROP);
                }
            } else if (requestCode == REQUESTGALLERY) {
                pUri = data.getData();

                if (pUri != null) {
                    CommonData.getInstance().setTempPicUri(pUri);
                    intent = new Intent(this, CropActivity.class);
                    startActivityForResult(intent,PIC_CROP);
                }
//                    if there is a problem with rotated image
//                    try {
//                        currentBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//                        bitmap = GalleryHelper.getRotatedImage(getApplicationContext(), currentBitmap, selectedImageUri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
            } else if (requestCode == PIC_CROP) {
                Uri uriCroppedImage = CommonData.getInstance().getTempPicUri();
                imageMain.setImageURI(uriCroppedImage);
                try {
                    currentBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriCroppedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void imageLoaded(int postId) {
    }

    @Override
    public void dataLoaded() {
        Picasso.with(getApplicationContext())
                .load(foundation.defaultPicsUrl[0]).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageMain.setImageBitmap(bitmap);
                currentBitmap = bitmap;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });

//        5 default pics
        for (int i = 0; i < 5; i++) {
            Picasso.with(getApplicationContext())
                    .load(foundation.defaultPicsUrl[i]).into(defaultImages[i]);
        }
    }
    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            Log.wtf("AAA", "UTF-8 should always be supported", e);
            return "";
        }
    }
    public void tweet(View view) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
        tweetIntent.setType("text/plain");

        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for(ResolveInfo resolveInfo: resolvedInfoList){
            if(resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")){
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name );
                resolved = true;
                break;
            }
        }
        if(resolved){
            startActivity(tweetIntent);
        }else{
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, "MESSAGE");
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text="+urlEncode("MESSAGE")));
            startActivity(i);
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }


    }

    public void shareAppLinkViaFacebook(View v) {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        Intent chooserIntent = Intent.createChooser(shareIntent, getString(R.string.share_via));
//
//        // for 21+, we can use EXTRA_REPLACEMENT_EXTRAS to support the specific case of Facebook
//        // (only supports a link)
//        // >=21: facebook=link, other=text+link
//        // <=20: all=link
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
//            Bundle facebookBundle = new Bundle();
//            //facebookBundle.putString(Intent.EXTRA_TEXT, link);
//            Bundle replacement = new Bundle();
//            replacement.putBundle("com.facebook.katana", facebookBundle);
//            chooserIntent.putExtra(Intent.EXTRA_REPLACEMENT_EXTRAS, replacement);
//        } else {
//            //shareIntent.putExtra(Intent.EXTRA_TEXT, link);
//        }
//
//        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(chooserIntent);
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentTitle("Your Title")
                .setContentDescription("Your Description")
                //.setContentUrl(Uri.parse("URL[will open website or app]"))
                //.setImageUrl(Uri.parse("image or logo [if playstore or app store url then no need of this image url]"))
                .build();
        ShareDialog.show(this, shareLinkContent);

    }
}
