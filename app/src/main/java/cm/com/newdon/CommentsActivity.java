package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.CommentsAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.PostQuery;

public class CommentsActivity extends Activity implements DataLoadedIf {

    int postId;
    ListView lvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        CommonData.getInstance().imageLoadedIf = this;

        Intent intent = getIntent();
        postId = intent.getIntExtra("postId",0);
        DataLoader.getComments(postId);

        lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(new CommentsAdapter(getApplicationContext()));
    }

    public void sendComment(View view) {
        EditText etComment = (EditText) findViewById(R.id.etComment);
        PostQuery.createComment(getApplicationContext(),postId,etComment.getText().toString());
    }

    @Override
    public void imageLoaded(int postId) {}

    @Override
    public void dataLoaded() {
        lvComments.invalidateViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CommonData.getInstance().imageLoadedIf = null;
    }
}
