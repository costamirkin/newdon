package cm.com.newdon.common;

import org.json.JSONException;
import org.json.JSONObject;

import cm.com.newdon.classes.FoundCategory;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.classes.User;

/**
 * Created by Marina on 23.12.2016.
 */
public class JsonHandler {

    public static Foundation parseFoundationFromJson(JSONObject item) throws JSONException {
        int id = item.getInt("id");
        String title = item.getString("title");
        String description = item.getString("description");
        String nubmer = item.getString("number");
//                        int yearFounded = item.getInt("yearFounded");
        String address = item.getString("address");
        String logoUrl = item.getString("logo");
//        int donatorCount = item.getInt("donatorCount");

        JSONObject categotyObj = item.getJSONObject("category");
        String categoryName = categotyObj.getString("name");
        String color = categotyObj.getString("color");

        Foundation foundation = new Foundation(id,title,new FoundCategory(categoryName,color), address, logoUrl);

//        System.out.println("**************************************");
//        System.out.println(foundation.getCategory().getName());

        return foundation;
    }

    public static Post parsePostFromJson(JSONObject item) throws JSONException {
        Post post = new Post();
        int id = item.getInt("id");
        String message = item.getString("message");
        String imageUrl = item.getString("image");

        JSONObject foundationObj = item.getJSONObject("foundation");
        Foundation foundation = parseFoundationFromJson(foundationObj);

        JSONObject userObj = item.getJSONObject("user");
        User user = parseUserFromJson(userObj);

        post.setFoundation(foundation);
        post.setUser(user);

        post.setId(id);
        post.setMessage(message);
        post.setImageUrl(imageUrl);

        return post;
    }

    public static User parseUserFromJson(JSONObject item) throws JSONException {
        User user = new User();
        int id = item.getInt("id");
        String username = item.getString("username");
        user.setId(id);
        user.setUserName(username);
        return user;
    }
}
