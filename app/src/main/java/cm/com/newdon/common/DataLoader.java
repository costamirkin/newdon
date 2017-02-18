package cm.com.newdon.common;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cm.com.newdon.classes.Comment;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.classes.Notification;
import cm.com.newdon.classes.Post;
import cz.msebera.android.httpclient.Header;

/**
 * download data from server via REST API
 */
public class DataLoader {

//    get data ot current user
//    for now we use only id from it
//    could get more info
    public static void getUserId(final Context context){
        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    int userId = object.getInt("id");
                    CommonData.getInstance().setCurrentUserId(userId);
                    CommonData.getInstance().setCurrentUser(JsonHandler.parseUserFromJson(object));
//                    ? do we need posts here?
//                    getUserPosts(context, userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();
        RestClient.get("account/me", params, handler);
    }

//    get info about all foundations
    public static void getAllFoundations() {
        CommonData.getInstance().getFoundations().clear();

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    System.out.println(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        Foundation foundation = JsonHandler.parseFoundationFromJson(array.getJSONObject(i));
                        new ImageLoaderToBitmap(foundation.getLogoUrl(),foundation.getId(),
                                ImageLoaderToBitmap.DownloadOption.FOUNDATION).execute();
                        CommonData.getInstance().getFoundations()
                                .add(foundation);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();
            RestClient.get("foundations/find", params, handler);
    }

//    get posts of foundation by Foundation ID
    public static void getFoundationPosts(final Context context, int foundationId) {

        CommonData.getInstance().getFoundationPosts().clear();
        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        Post post = JsonHandler.parsePostFromJson(array.getJSONObject(i));
                        System.out.println(post);
                        if(!post.getImageUrl().equals("null")){
                            new ImageLoaderToStorage(post.getImageUrl(),context,post.getId(),
                                    ImageLoaderToBitmap.DownloadOption.POST).execute();
                        }
                        CommonData.getInstance().getFoundationPosts().add(post);
                        if (CommonData.getInstance().imageLoadedIf != null) {
                            CommonData.getInstance().imageLoadedIf.dataLoaded();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();
        params.put("foundationId", foundationId);

        RestClient.get("foundations/posts", params, handler);
    }

    //    get posts of user by User ID
   public static void getUserPosts(final Context context, int userID) {

        CommonData.getInstance().getPosts().clear();
        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        Post post = JsonHandler.parsePostFromJson(array.getJSONObject(i));
                        System.out.println(post);
                        if(!post.getImageUrl().equals("null")){
                            new ImageLoaderToStorage(post.getImageUrl(),context,post.getId(),
                                    ImageLoaderToBitmap.DownloadOption.POST).execute();
                        }
                        CommonData.getInstance().getPosts().add(post);
                        if (CommonData.getInstance().imageLoadedIf != null) {
                            CommonData.getInstance().imageLoadedIf.dataLoaded();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("userId", userID);

        RestClient.get("users/posts", params, handler);
    }

    public static void getFollowUsers(String type, int userId) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    CommonData.getInstance().getFollowUsers().clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        CommonData.getInstance().getFollowUsers().add(JsonHandler.parseUserFromJson(item));
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("userId", userId);

        RestClient.get("connections/list", params, handler);
    }

    public static void searchUsers(String search) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    CommonData.getInstance().getSearchUsers().clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        CommonData.getInstance().getSearchUsers().add(JsonHandler.parseUserFromJson(item));
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();
        params.put("type", "suggested");

        RestClient.get("users?query=" + search, params, handler);
    }

   public static void getSuggestedUsers() {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    CommonData.getInstance().getSuggestedUsers().clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        CommonData.getInstance().getSuggestedUsers().add(JsonHandler.parseUserFromJson(item));
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();
        params.put("type", "suggested");

        RestClient.get("connections/list", params, handler);
    }

    public static void getFoundationData(final int foundationId) {
        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("!!!!!!!!!!!!FoundationDATA!!!!!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    // int yearFounded = item.getInt("yearFounded");
                    JSONArray array = object.getJSONArray("postImages");
                    for (int i = 0; i < array.length(); i++) {
                        int imageId = array.getJSONObject(i).getInt("id");
                        CommonData.getInstance().findFoundById(foundationId).defaultPicsId[i]=imageId;
                        String imageUrl = array.getJSONObject(i).getString("url");
                        CommonData.getInstance().findFoundById(foundationId).defaultPicsUrl[i]= imageUrl;
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();
        params.put("id",foundationId);

        RestClient.get("foundations/get", params, handler);
    }

    //    get lotteries that we show in the listview Lottery History
    public static void getLotteryList() {
        CommonData.getInstance().getLotteryList().clear();

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    System.out.println(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        System.out.println(i);
                        Lottery lottery = JsonHandler.parseLotteryFromJson(array.getJSONObject(i));
                        CommonData.getInstance().getLotteryList()
                                .add(lottery);
                    }

//                    to renew listview
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };
        RequestParams params = new RequestParams();
        RestClient.get("lottery/list", params, handler);
    }

//    get lotteries that we show in the viewPager of home screen
    public static void getFeaturedLotteries() {
        CommonData.getInstance().getFeaturedLotteries().clear();
        CommonData.getInstance().getLotteryList().clear();

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONArray array = new JSONArray(new String(responseBody));
                    System.out.println(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        System.out.println(i);
                        Lottery lottery = JsonHandler.parseLotteryFromJson(array.getJSONObject(i));
                        CommonData.getInstance().getLotteryList()
                                .add(lottery);
                        CommonData.getInstance().getFeaturedLotteries()
                                .add(lottery);
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };
        RequestParams params = new RequestParams();
        RestClient.get("lottery/featured", params, handler);
    }

//    get posts that we show on the home screen
    public static void getHomeScreenPosts(final Context context) {

        CommonData.getInstance().getPosts().clear();
        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(responseBody);
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        Post post = JsonHandler.parsePostFromJson(array.getJSONObject(i));
                        System.out.println(post);
                        if(!post.getImageUrl().equals("null")){
                            new ImageLoaderToStorage(post.getImageUrl(),context,post.getId(),
                                    ImageLoaderToBitmap.DownloadOption.POST).execute();
                        }
//                        for now we are not showing shared post
                        if(!post.getAction().equals("share")) {
                            CommonData.getInstance().getPosts().add(post);
                        }
                        if (CommonData.getInstance().imageLoadedIf != null) {
                            CommonData.getInstance().imageLoadedIf.dataLoaded();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };

        RequestParams params = new RequestParams();

        RestClient.get("feed/wall?page=0&limit=30", params, handler);
    }

    //    get notification|activity list
    public static void getNotificationList(boolean isActivities) {
        CommonData.getInstance().getNotifications().clear();

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    System.out.println(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject notificationJson = array.getJSONObject(i);
                        Notification notification = JsonHandler.parseNotificationFromJson(notificationJson);
                        CommonData.getInstance().getNotifications().add(notification);
                        System.out.println(i);
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        RestClient.get((isActivities?"activity":"notification")+"/list?page=0", params, handler);
    }
   //    get notification|activity list
    public static void getSuggestedUsers(boolean isActivities) {

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    System.out.println(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        System.out.println(i);
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("type", "suggested");
        RestClient.get("connections/list", params, handler);
    }

    //    get comment by postId
    public static void getComments(int postId) {
        CommonData.getInstance().getComments().clear();

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    JSONArray array = jsonObject.getJSONArray("items");
                    System.out.println(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        Comment comment = JsonHandler.parseCommentFromJson(array.getJSONObject(i));
                        CommonData.getInstance().getComments()
                                .add(comment);
                    }
                    if (CommonData.getInstance().imageLoadedIf != null) {
                        CommonData.getInstance().imageLoadedIf.dataLoaded();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("!!!!!!!!!ERROR!!!!!!!!!!!!");
                if (responseBody != null) {
                    System.out.println(new String(responseBody));
                }
            }
        };
        RequestParams params = new RequestParams();
        params.put("postId", postId);
        RestClient.get("comments/list?post", params, handler);
    }
}
