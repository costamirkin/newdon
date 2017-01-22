package cm.com.newdon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ShareDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_dialog);
    }

    public void closeShareDialog(View view) {
        finish();
    }
}
