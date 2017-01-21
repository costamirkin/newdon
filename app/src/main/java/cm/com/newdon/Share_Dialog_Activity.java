package cm.com.newdon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Share_Dialog_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share__dialog_);
    }

    public void closeShareDialog(View view) {
        finish();
    }
}
