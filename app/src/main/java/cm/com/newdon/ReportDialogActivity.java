package cm.com.newdon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ReportDialogActivity extends Activity {

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dialog);

        String[] reasons = {"It's annoying or not interesting", "I'm in this photo and I don't like it",
                "I don't think it should be on Donder", "It's spam"};

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_custom_item, reasons);
    }

    public void showSpinner(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Select reason")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // TODO: user specific action

                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void closeDialog(View view) {
        finish();
    }

    public void reportPost(View view) {
//        TODO
    }
}
