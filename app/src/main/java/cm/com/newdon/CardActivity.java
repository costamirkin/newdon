package cm.com.newdon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import cm.com.newdon.common.CommonData;

public class CardActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://direct.tranzila.com/altru/iframe.php?hidesum=1&currency=1&tranmode=K?userId=" +
                CommonData.getInstance().getToken();
        System.out.println(url);
        webView.loadUrl(url);
//        webView.loadUrl("https://direct.tranzila.com/altru/iframe.php?hidesum=1&currency=1&tranmode=K");

    }
}
