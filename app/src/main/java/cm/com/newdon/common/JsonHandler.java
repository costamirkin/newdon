package cm.com.newdon.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cm.com.newdon.classes.FoundCategory;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Lottery;
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

        String address = item.getString("address");
        String logoUrl = item.getString("logo");
        int donatorCount = 0;
        try {
            donatorCount = item.getInt("donatorCount");
        }
        catch (Exception e){
            e.printStackTrace();
        }

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

    public static Lottery parseLotteryFromJson(JSONObject item) throws JSONException {
        int id = item.getInt("id");
        String title = item.getString("title");
        Lottery lottery = new Lottery(id,title);

        String status = item.getString("status");
        String logoUrl  = item.getString("logo");
        String imageUrl = item.getString("image");
        String scheduleDay = item.getString("scheduleDay");
        String promoText = item.getString("promoText");
        String description = item.getString("description");
        String comfortText = item.getString("comfortText");
        boolean isYouWin = item.getBoolean("isYouWin");
        int participantCount = item.getInt("participantCount");

        lottery.setStatus(status);
        lottery.setLogoUrl(logoUrl);
        lottery.setImageUrl(imageUrl);
        lottery.setScheduleDay(scheduleDay);
        lottery.setPromoText(promoText);
        lottery.setDescription(description);
        lottery.setComfortText(comfortText);
        lottery.setIsYouWin(isYouWin);
        lottery.setParticipantCount(participantCount);

        return lottery;
    }
}
