package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cm.com.newdon.common.PostQuery;

public class ShareDialogActivity extends Activity {
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_dialog);

        Intent intent = getIntent();
        postId = intent.getIntExtra("postId",0);
    }

    public void closeShareDialog(View view) {
        finish();
    }

    public void sharePost(View view) {
        EditText etMessage = (EditText) findViewById(R.id.etShareComment);
        PostQuery.sharePost(getApplicationContext(),postId,etMessage.getText().toString());
        finish();
    }
}
