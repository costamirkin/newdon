package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

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
        PostQuery.sharePost(postId, etMessage.getText().toString());
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

    //// TODO: 25.02.2017
    public void shareTwitter(View view) {
    }

    public void shareFacebook(View view) {
    }
}
