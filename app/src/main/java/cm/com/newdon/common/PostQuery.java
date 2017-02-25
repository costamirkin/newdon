package cm.com.newdon.common;

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

    public static void managePost(final int postId, final PostAction action) {

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
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);

        RestClient.post("posts/" + action.getValue(), params, handler);
    }

    public static void report(final int entity_id, boolean isPost, String reason, String message) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("entity_id", entity_id);
        params.put("reason", reason);
        params.put("message", message);
        params.put("entity", (isPost? "Post" : "Comment"));

        RestClient.post("posts/report", params, handler);
    }

    public static void sharePost(final int postId, String message) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);
        params.put("message", message);

        RestClient.post("posts/share", params, handler);
    }

    public static void createComment(final int postId, String text) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(responseBody);
                DataLoader.getComments(postId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("postId", postId);
        params.put("text", text);

        RestClient.post("comments/create", params, handler);
    }

    public static void likePost(final int postId, final boolean unLike) {

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

    public static void deleteComment(final int commentId) {

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
                System.out.println("!!!!!!!!!Delete comment ERROR!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("commentId", commentId);

        RestClient.delete("comments/delete", params, handler);
    }

}
