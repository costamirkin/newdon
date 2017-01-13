package cm.com.newdon.common;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.classes.Post;
import cz.msebera.android.httpclient.Header;

/**
 * download data from server via REST API
 */
public class DataLoader {

    public static void getUserId(final Context context){
        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    int userId = object.getInt("id");
                    CommonData.getInstance().setCurrentUserId(userId);
                    getUserPosts(context);
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
        RestClient.get("account/me", params, handler);
    }

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
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
            RestClient.get("foundations/find", params, handler);
    }

    public static void getUserPosts(final Context context) {

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
        params.put("userId", CommonData.getInstance().getCurrentUserId());

        RestClient.get("users/posts", params, handler);
    }

    public static void getSuggestedUsers(final Context context) {

        AsyncHttpResponseHandler handler =  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        int id =  item.getInt("id");
                        System.out.println(id);
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
                System.out.println(new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.put("id",foundationId);

        RestClient.get("foundations/get", params, handler);
    }

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
//                        new ImageLoaderToBitmap(foundation.getLogoUrl(),foundation.getId(),
//                                ImageLoaderToBitmap.DownloadOption.FOUNDATION).execute();
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
                System.out.println(new String(responseBody));
            }
        };
        RequestParams params = new RequestParams();
        RestClient.get("lottery/list", params, handler);
    }

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
//                        new ImageLoaderToBitmap(foundation.getLogoUrl(),foundation.getId(),
//                                ImageLoaderToBitmap.DownloadOption.FOUNDATION).execute();
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
                System.out.println(new String(responseBody));
            }
        };
        RequestParams params = new RequestParams();
        RestClient.get("lottery/featured", params, handler);
    }
}
