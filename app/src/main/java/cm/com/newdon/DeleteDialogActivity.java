package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cm.com.newdon.R;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.PostQuery;

public class DeleteDialogActivity extends Activity {

    int commentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_dialog);
        Intent intent = getIntent();
        commentId = intent.getIntExtra("commentId",0);
    }

    public void close(View view) {
        finish();
    }

    public void delete(View view) {
        PostQuery.deleteComment(this,commentId);
        CommonData.getInstance().getComments().remove(CommonData.getInstance().findCommentById(commentId));
        finish();
    }
}
