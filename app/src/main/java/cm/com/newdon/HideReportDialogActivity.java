package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cm.com.newdon.classes.User;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.PostQuery;
import cm.com.newdon.common.Utils;

public class HideReportDialogActivity extends Activity {
    private int postId;
    private User user;
    private TextView tvFollowUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_report_dialog);
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 0);

        user = CommonData.getInstance().findPostById(postId).getUser();
        tvFollowUser = (TextView) findViewById(R.id.tvFollowUser);
        if(user.isFollowed()){
            tvFollowUser.setText("Unfollow this user");
        }
    }

    public void reportPost(View view) {
        Intent intent = new Intent(getApplicationContext(),ReportDialogActivity.class);
        intent.putExtra("postId",postId);
        startActivity(intent);
    }

    public void hidePost(View view) {
        PostQuery.managePost(postId, PostQuery.PostAction.HIDE, this);
        finish();
    }

    public void followUser(View view) {
        if(user.isFollowed()){
            //if already follow
            boolean unFollow = true;
            Utils.followUser(user.getId(), getApplicationContext(), unFollow);
            user.setIsFollowed(false);

        }else {
            Utils.followUser(user.getId(), getApplicationContext());
            user.setIsFollowed(true);

        }
        finish();
    }
}
