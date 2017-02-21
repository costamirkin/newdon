package cm.com.newdon;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cz.msebera.android.httpclient.Header;

public class MakeDonActivity extends AppCompatActivity {

    private EditText etAmount;
    private Post post;
    private Foundation foundation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_don);

        post = CommonData.getInstance().getTempPost();
        foundation = post.getFoundation();

        TextView tvFound = (TextView) findViewById(R.id.tvFoundTitle);
        tvFound.setText(foundation.getTitle());

        etAmount = (EditText) findViewById(R.id.etAmount);
        etAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (getAmount() == 0 && keyCode != KeyEvent.KEYCODE_BACK) etAmount.setText("₪");
                int position = etAmount.getText().length();
                Editable editObj = etAmount.getText();
                Selection.setSelection(editObj, position);
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) setAmount(0);
                return false;
            }
        });
    }

    public void makeDone(View view) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(),"YOU made a DON!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),BottomBarActivity.class);
                intent.putExtra("success", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String errStr = "Donate Error!";
                if(responseBody != null)  {
                    errStr = new String(responseBody);
                }

                Toast.makeText(getApplicationContext(), errStr, Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("foundationId", foundation.getId());
        params.put("amount", getAmount());
        params.put("comment", post.getMessage());
        if(post.getUri()!=null) {
            try {
                params.put("image", new File(post.getUri()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        RestClient.post("foundations/donate", params, handler);

    }

    private int getAmount(){
        String text = etAmount.getText().toString();
        int amount = Integer.valueOf(text.subSequence(1, text.length()).toString());
        return amount;
    }

    private void setAmount(int amount){
        String text = "₪"+amount;
        etAmount.setText(text);
    }

    public void clearAmount(View view) {
        setAmount(0);
    }

    public void coin5(View view) {
        setAmount(5);
    }

    public void coin10(View view) {
        setAmount(10);
    }

    public void coin18(View view) {
        setAmount(18);
    }

    public void coin50(View view) {
        setAmount(50);
    }

    public void creditCard(View view) {
        startActivity(new Intent(getApplicationContext(), CardActivity.class));
    }

    public void showKeyboard(View view) {
//// TODO: 20.01.2017
//        open key board
        /* show keyboard */
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
