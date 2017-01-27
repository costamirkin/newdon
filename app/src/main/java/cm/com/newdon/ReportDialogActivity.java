package cm.com.newdon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cm.com.newdon.common.PostQuery;

public class ReportDialogActivity extends Activity {

    ArrayAdapter<String> adapter;
    String[] reasons = {"It's annoying or not interesting", "I'm in this photo and I don't like it",
            "I don't think it should be on Donder", "It's spam"};
    String reason = null;
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dialog);

        Intent intent = getIntent();
        postId = intent.getIntExtra("postId",0);

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_custom_item, reasons);
    }

    public void showSpinner(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Select reason")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reason = reasons[which];

//                        to check
                        TextView tvReason = (TextView) findViewById(R.id.tvReason);
                        tvReason.setText(reason);

                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void closeDialog(View view) {
        finish();
    }

    public void reportPost(View view) {
        if (reason==null){
            Toast.makeText(getApplicationContext(),"Please select reason!", Toast.LENGTH_SHORT).show();
        }else {
            EditText etMessage = (EditText) findViewById(R.id.etReportMessage);
            PostQuery.reportPost(getApplicationContext(), postId, reason, etMessage.getText().toString());
            finish();
        }
    }
}
