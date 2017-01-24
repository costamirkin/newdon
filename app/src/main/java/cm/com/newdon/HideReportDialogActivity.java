package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cm.com.newdon.common.PostQuery;

public class HideReportDialogActivity extends Activity {
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_report_dialog);
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId",0);
    }

    public void reportPost(View view) {
        startActivity(new Intent(getApplicationContext(),ReportDialogActivity.class));
    }

    public void hidePost(View view) {
        PostQuery.managePost(getApplicationContext(), postId, PostQuery.PostAction.HIDE);
        finish();
    }

    public void followUser(View view) {
//        TODO
    }
}
