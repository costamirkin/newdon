package cm.com.newdon.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cm.com.newdon.classes.Comment;
import cm.com.newdon.classes.FoundCategory;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.classes.Notification;
import cm.com.newdon.classes.Post;
import cm.com.newdon.classes.Ticket;
import cm.com.newdon.classes.User;

/**
 * class for parsing data from Json to objects
 */
public class JsonHandler {

    public static Foundation parseFoundationFromJson(JSONObject item) throws JSONException {
        int id = item.getInt("id");
        String title = item.getString("title");
        String description = item.getString("description");
        String nubmer = item.getString("number");
        int followersCount = item.getInt("followersCount");
        String headquarter  = item.getString("headquarter");

        String address = item.getString("address");
        String logoUrl = item.getString("logo");
        int  yearFounded = 0; // item.getInt("yearFounded");
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

        Foundation foundation = new Foundation(
                id,
                title,
                description,
                nubmer,
                yearFounded,
                address,
                followersCount,
                headquarter,
                logoUrl,
                donatorCount,
                new FoundCategory(categoryName,color));

//        System.out.println("**************************************");
//        System.out.println(foundation.getCategory().getName());

        return foundation;
    }

    public static Post parsePostFromJson(JSONObject item) throws JSONException {
        Post post = new Post();
        int id = item.getInt("id");
        String message = item.getString("message");
        int likesCount = item.getInt("likesCount");
        int commentsCount = item.getInt("commentsCount");
        int donatorCount = item.getInt("donatorCount");
        String imageUrl = item.getString("image");
        String createdAt = item.getString("createdAt");
        Date date = DateHandler.parseDateFromString(createdAt);
        String action = item.getString("action");

        JSONObject foundationObj = item.getJSONObject("foundation");
        Foundation foundation = parseFoundationFromJson(foundationObj);

        JSONObject userObj = item.getJSONObject("user");
        User user = parseUserFromJson(userObj);

        post.setFoundation(foundation);
        post.setUser(user);

        post.setId(id);
        post.setMessage(message);
        post.setLikesCount(likesCount);
        post.setCommentsCount(commentsCount);
        post.setDonatorCount(donatorCount);
        post.setImageUrl(imageUrl);
        post.setCreatedAt(date);
        post.setAction(action);

        //check if the post already liked
        JSONArray likesArray = item.getJSONArray("likes");
        for (int i = 0; i < likesArray.length(); i++) {
            JSONObject like = likesArray.getJSONObject(i);
            if(like.getInt("id")==CommonData.getInstance().getCurrentUserId()){
                post.setIsLiked(true);
                break;
            }
        }

        return post;
    }

    public static User parseUserFromJson(JSONObject item) throws JSONException {
        User user = new User();
        int id = item.getInt("id");
        String username = item.getString("username");
        String realName = item.getString("realName");
//        String firstName = item.getString("firstName");
//        String lastName = item.getString("lastName");
        int followersCount = item.getInt("followersCount");
        int followingCount = item.getInt("followingCount");
        boolean isFollowed = item.getBoolean("isFollowed");

        String email  = "";
        if (item.has("email")) {
            email = item.getString("email");
        }
        user.setEmail(email);


        String pictureURL  = "";
        if (item.has("pictureURL")) {
            pictureURL = item.getString("pictureURL");
        }
        user.setPictureUrl(pictureURL);
        user.setEmail(email);
        user.setId(id);
        user.setUserName(username);
        user.setRealName(realName);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
        user.setFollowersCount(followersCount);
        user.setFollowingCount(followingCount);
        user.setIsFollowed(isFollowed);
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
        Date date = DateHandler.parseDateFromString(scheduleDay);

        String promoText = item.getString("promoText");
        String description = item.getString("description");
        String comfortText = item.getString("comfortText");
        boolean isYouWin = item.getBoolean("isYouWin");
        int participantCount = item.getInt("participantCount");

        JSONArray ticketsArray = item.getJSONArray("tickets");
        for (int i = 0; i < ticketsArray.length(); i++) {
            JSONObject ticketObj = ticketsArray.getJSONObject(i);
            String ticketNumber = ticketObj.getString("number");
            String ticketStatus = ticketObj.getString("status");
            Ticket ticket = new Ticket(ticketNumber,ticketStatus);
            lottery.getTickets().add(ticket);
        }

        lottery.setStatus(status);
        lottery.setLogoUrl(logoUrl);
        lottery.setImageUrl(imageUrl);
        lottery.setScheduleDay(date);
        lottery.setPromoText(promoText);
        lottery.setDescription(description);
        lottery.setComfortText(comfortText);
        lottery.setIsYouWin(isYouWin);
        lottery.setParticipantCount(participantCount);

        return lottery;
    }

    public static Notification parseNotificationFromJson(JSONObject item) throws JSONException {
        Notification notification = new Notification();
        int id = item.getInt("id");
        Notification.Type type = Notification.Type.valueOf(item.getString("type").toUpperCase());
        String createdAt = item.getString("createdAt");
        Date date = DateHandler.parseDateFromString(createdAt);

        JSONObject userObj = item.getJSONObject("user");
        User user = parseUserFromJson(userObj);

        Notification.ContentType contentType
                = Notification.ContentType.valueOf(item.getString("contentType").toUpperCase());

        JSONObject content = item.getJSONObject("content");
        switch (contentType){
            case USER:
                User contentUser = parseUserFromJson(content);
                notification.setContentUser(contentUser);
                break;
            case POST:
                Post contentPost = parsePostFromJson(content);
                notification.setContentPost(contentPost);
                break;
            default:
                System.out.println("Notification error!!!!!!!!!! Unknown content type!");
        }

        notification.setId(id);
        notification.setUser(user);
        notification.setType(type);
        notification.setCreatedAt(date);
        notification.setContentType(contentType);

        return notification;
    }

    public static Comment parseCommentFromJson(JSONObject item) throws JSONException {
        int id = item.getInt("id");
        String text = item.getString("text");
        String stringDate = item.getString("date");
        Date date = DateHandler.parseDateFromString(stringDate);

        JSONObject userObj = item.getJSONObject("user");
        User user = parseUserFromJson(userObj);

        Comment comment = new Comment(id,text,user,date);

        return comment;
    }
}
