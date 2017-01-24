package cm.com.newdon.common;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Marina on 21.01.2017.
 */
public class PostQuery {
    public enum PostAction{
        HIDE("hide"),DELETE("delete"),LIKE("like"),UNLIKE("unlike");
        private String action;

        PostAction(String action) {
            this.action = action;
        }

        public String getValue(){
            return action;
        }
    }

    public static void managePost(final Context context, final int postId, final PostAction action) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                for testing
                Toast.makeText(context, "You successfully " + action.getValue() + " the post!", Toast.LENGTH_LONG).show();

                switch (action){
                    case HIDE:
                        CommonData.getInstance().getPosts().remove(CommonData.getInstance().findPostById(postId));
                        break;
                    case DELETE:
                        CommonData.getInstance().getPosts().remove(CommonData.getInstance().findPostById(postId));
                        break;
                    default:
                        break;
                }

                //                renew listview
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);

        RestClient.post("posts/"+action.getValue(), params, handler);
    }
}
