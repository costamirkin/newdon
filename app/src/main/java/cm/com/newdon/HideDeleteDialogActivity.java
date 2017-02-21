package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.PostQuery;

public class HideDeleteDialogActivity extends Activity {
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_delete_dialog);
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId",0);
    }

    public void hidePost(View view) {
        PostQuery.managePost(postId, PostQuery.PostAction.HIDE);
        finish();
    }

    public void deletePost(View view) {
        PostQuery.managePost(postId, PostQuery.PostAction.DELETE);
        finish();
    }
}
