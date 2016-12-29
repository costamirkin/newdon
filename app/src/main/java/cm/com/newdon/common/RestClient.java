package cm.com.newdon.common;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by costa on 16/12/16.
 */
public class RestClient  {
    private static final String BASE_URL = "http://donation.s2.ideas-implemented.com/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setBasicAuth("egenesis", "rhk@Wf54");
        client.addHeader("Authorization", "Basic " + CommonData.getInstance().getToken());
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setBasicAuth("egenesis", "rhk@Wf54");
        client.addHeader("Authorization", "Basic " + CommonData.getInstance().getToken());
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void loginSignup(String cmd, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String url = "";
        if (cmd.equals("login")) {
            url = "account/login";
        }
        else if (cmd.equals("signup")) {
            url = "account/register";
        }
        client.setBasicAuth("egenesis", "rhk@Wf54");
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}