package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.CommentsAdapter;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.PostQuery;

public class CommentsActivity extends Activity implements DataLoadedIf {

    int postId;
    Post post;
    ListView lvComments;
    TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        CommonData.getInstance().imageLoadedIf = this;

        Intent intent = getIntent();
        postId = intent.getIntExtra("postId",0);
        DataLoader.getComments(postId);

        tvHeader = (TextView) findViewById(R.id.tvHeaderComments);
        post = CommonData.getInstance().findPostById(postId);
        setCommentsCount();

        lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(new CommentsAdapter(getApplicationContext(), post));
    }

    public void sendComment(View view) {
        EditText etComment = (EditText) findViewById(R.id.etComment);
        PostQuery.createComment(postId,etComment.getText().toString(), this);
        post.setCommentsCount(post.getCommentsCount() + 1);
        setCommentsCount();
        etComment.setText("");
    }

    @Override
    public void imageLoaded(int postId) {}

    @Override
    public void dataLoaded() {
        setCommentsCount();
        lvComments.invalidateViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CommonData.getInstance().imageLoadedIf = null;
    }

    private void setCommentsCount(){
        int commentsCount =  CommonData.getInstance().getComments().size();
        if(commentsCount==1) {
            tvHeader.setText("1 Comment");
        }else{
            tvHeader.setText(commentsCount+ " Comments");
        }
    }
}
