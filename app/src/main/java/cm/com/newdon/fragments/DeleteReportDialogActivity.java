package cm.com.newdon.fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cm.com.newdon.DeleteDialogActivity;
import cm.com.newdon.R;
import cm.com.newdon.ReportDialogActivity;

public class DeleteReportDialogActivity extends AppCompatActivity {

    int commentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_report_dialog);
        Intent intent = getIntent();
        commentId = intent.getIntExtra("commentId",0);
    }

    public void deleteComment(View view) {
        Intent intent = new Intent(this, DeleteDialogActivity.class);
        intent.putExtra("commentId",commentId);
        startActivity(intent);
        finish();
    }

    public void reportComment(View view) {
        Intent intent = new Intent(this, ReportDialogActivity.class);
        intent.putExtra("commentId",commentId);
        startActivity(intent);
        finish();
    }
}
