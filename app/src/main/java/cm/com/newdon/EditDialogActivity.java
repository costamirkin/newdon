package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cm.com.newdon.classes.Comment;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.PostQuery;

public class EditDialogActivity extends Activity {

    int commentId;
    Comment comment;
    EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dialog);
        Intent intent = getIntent();
        commentId = intent.getIntExtra("commentId", 0);
        comment = CommonData.getInstance().findCommentById(commentId);

        etComment = (EditText) findViewById(R.id.etComment);
        etComment.setText(comment.getText());
    }

    public void delete(View view) {
        Intent intent = new Intent(this, DeleteDialogActivity.class);
        intent.putExtra("commentId",commentId);
        startActivity(intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        String text = etComment.getText().toString();
        comment.setText(text);
        PostQuery.updateComment(commentId,text, this);
        finish();
    }
}
