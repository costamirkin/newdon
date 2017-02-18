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
    public enum PostAction {
        HIDE("hide"), DELETE("delete");
        private String action;

        PostAction(String action) {
            this.action = action;
        }

        public String getValue() {
            return action;
        }
    }

    public static void managePost(final Context context, final int postId, final PostAction action) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                for testing
                Toast.makeText(context, "You successfully " + action.getValue() + " the post!", Toast.LENGTH_LONG).show();
                CommonData.getInstance().getPosts().remove(CommonData.getInstance().findPostById(postId));
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
                Toast.makeText(context, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);

        RestClient.post("posts/" + action.getValue(), params, handler);
    }

    public static void report(final Context context, final int entity_id, boolean isPost, String reason, String message) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                for testing
                Toast.makeText(context, "You successfully report the post!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("entity_id", entity_id);
        params.put("reason", reason);
        params.put("message", message);
        params.put("entity", (isPost? "Post" : "Comment"));

        RestClient.post("posts/report", params, handler);
    }

    public static void sharePost(final Context context, final int postId, String message) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                for testing
                Toast.makeText(context, "You successfully shared the post!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);
        params.put("message", message);

        RestClient.post("posts/share", params, handler);
    }

    public static void createComment(final Context context, final int postId, String text) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                for testing
                Toast.makeText(context, "You created new comment!", Toast.LENGTH_LONG).show();
                System.out.println(responseBody);

                DataLoader.getComments(postId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);
        params.put("text", text);

        RestClient.post("comments/create", params, handler);
    }

    public static void likePost(final Context context, final int postId, final boolean unLike) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                for testing
                Toast.makeText(context, "You successfully " + (unLike? "un":"") + "liked the post!", Toast.LENGTH_LONG).show();
                CommonData.getInstance().findPostById(postId).setIsLiked(!unLike);
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
                Toast.makeText(context, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);

        RestClient.put("posts/" + (unLike ? "unlike" : "like"), params, handler);
    }

    public static void updateComment(int commentId, String text){
        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("commentId", commentId);
        params.put("text", text);

        RestClient.put("comments/edit", params, handler);
    }

    public static void deleteComment(final Context context, final int commentId) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                for testing
                Toast.makeText(context, "You successfully deleted the post!", Toast.LENGTH_LONG).show();
                CommonData.getInstance().getPosts().remove(CommonData.getInstance().findPostById(commentId));
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!Delete comment ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
                Toast.makeText(context, new String(responseBody), Toast.LENGTH_LONG).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("commentId", commentId);

        RestClient.delete("comments/delete", params, handler);
    }

}
