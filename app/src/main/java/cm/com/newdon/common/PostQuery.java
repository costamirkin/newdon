package cm.com.newdon.common;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * class for post and put queries
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


    public static void managePost(final int postId, final PostAction action, final Context context) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                CommonData.getInstance().getPosts().remove(CommonData.getInstance().findPostById(postId));
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,
                        Utils.createErrorStr(action + " failed", responseBody), Toast.LENGTH_SHORT).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);

        RestClient.post("posts/" + action.getValue(), params, handler);
    }

    public static void report(final int entity_id, boolean isPost, String reason, String message, final Context context) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,
                        Utils.createErrorStr("Report failed", responseBody), Toast.LENGTH_SHORT).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("entity_id", entity_id);
        params.put("reason", reason);
        params.put("message", message);
        params.put("entity", (isPost? "Post" : "Comment"));

        RestClient.post("posts/report", params, handler);
    }

    public static void sharePost(final int postId, String message, final Context context) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,
                        Utils.createErrorStr("Share failed", responseBody), Toast.LENGTH_SHORT).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);
        params.put("message", message);

        RestClient.post("posts/share", params, handler);
    }

    public static void createComment(final int postId, String text, final Context context) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(responseBody);
                DataLoader.getComments(postId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,
                        Utils.createErrorStr("Comment failed", responseBody), Toast.LENGTH_SHORT).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);
        params.put("text", text);

        RestClient.post("comments/create", params, handler);
    }

    public static void likePost(final int postId, final boolean unLike, final Context context) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,
                        Utils.createErrorStr("Like failed", responseBody), Toast.LENGTH_SHORT).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);

        RestClient.put("posts/" + (unLike ? "unlike" : "like"), params, handler);
    }

    public static void updateComment(int commentId, String text, final Context context){
        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,
                        Utils.createErrorStr("Update comment failed", responseBody), Toast.LENGTH_SHORT).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("commentId", commentId);
        params.put("text", text);

        RestClient.put("comments/edit", params, handler);
    }

    public static void deleteComment(final int commentId, final Context context) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                CommonData.getInstance().getPosts().remove(CommonData.getInstance().findPostById(commentId));
                if (CommonData.getInstance().imageLoadedIf != null) {
                    CommonData.getInstance().imageLoadedIf.dataLoaded();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,
                        Utils.createErrorStr("Delete omment failed", responseBody), Toast.LENGTH_SHORT).show();
            }
        };

        RequestParams params = new RequestParams();
        params.put("commentId", commentId);

        RestClient.delete("comments/delete", params, handler);
    }

}
