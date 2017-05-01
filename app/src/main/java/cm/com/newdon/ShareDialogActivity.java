package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.PostQuery;
import cm.com.newdon.common.Utils;

public class ShareDialogActivity extends Activity {
    int postId;
    Post post;
    Uri uri;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_dialog);

        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 0);
        post = CommonData.getInstance().findPostById(postId);

        if (post == null) {
            return;
        }
        // Create the URI from the media
        if(post.getImageUrl()!=null && !post.getImageUrl().equals("")) {
            Picasso.with(getApplicationContext())
                    .load(post.getImageUrl()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Bitmap currentBitmap = bitmap;
                    uri = Utils.getImageUri(getApplicationContext(), currentBitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }
    }

    public void closeShareDialog(View view) {
        finish();
    }

    public void sharePost(View view) {
        EditText etMessage = (EditText) findViewById(R.id.etShareComment);
        PostQuery.sharePost(postId, etMessage.getText().toString(), this);
        finish();
    }

    public void shareOther(View view) {
        text = CommonData.getInstance().getCurrentUser().getRealName() + " shared donation to "
                + post.getFoundation().getTitle() + " through Altru app";
        if (uri!=null) {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share to"));
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
    public void shareTwitter(View view) {
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

    public void shareFacebook(View view) {
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentTitle("Your Title")
                .setContentDescription("Your Description")
                        //.setContentUrl(Uri.parse("URL[will open website or app]"))
                        //.setImageUrl(Uri.parse("image or logo [if playstore or app store url then no need of this image url]"))
                .build();
        ShareDialog.show(this, shareLinkContent);
    }
}
