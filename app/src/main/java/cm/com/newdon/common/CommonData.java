package cm.com.newdon.common;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cm.com.newdon.classes.Comment;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.classes.Notification;
import cm.com.newdon.classes.Post;
import cm.com.newdon.classes.User;

/**
 * Class stores common data, like list of foundations,...
 */
public class CommonData {

    private static CommonData ourInstance = new CommonData();

    public static CommonData getInstance() {
        return ourInstance;
    }

    private CommonData() {
        foundations = new ArrayList<>();
        posts = new ArrayList<>();
        userPosts = new ArrayList<>();
        foundationPosts = new ArrayList<>();
        featuredLotteries = new ArrayList<>();
        lotteryList = new ArrayList<>();
        notifications = new ArrayList<>();
        comments = new ArrayList<>();
    }

    private List<Foundation> foundations;

    public List<Foundation> getFoundations() {
        return foundations;
    }

    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    private List<Post> userPosts;

    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void copyUserPosts() {
        userPosts.clear();
        for (Post post :
                posts) {
            if (post.getUser().getId() == selectedUserId) {
                userPosts.add(post);
            }

        }
    }

    private List<Post> foundationPosts;

    public List<Post> getFoundationPosts() {
        return foundationPosts;
    }

    public void copyFoundationPosts() {
        foundationPosts.clear();
        for (Post post :
                posts) {
            if (post.getFoundation().getId() == selectedFoundId) {
                foundationPosts.add(post);
            }
        }
    }

    private List<Lottery> featuredLotteries;

    public List<Lottery> getFeaturedLotteries() {
        return featuredLotteries;
    }

    private List<Lottery> lotteryList;

    public List<Lottery> getLotteryList() {
        return lotteryList;
    }

    public Foundation findFoundById(int id) {
        for (Foundation foundation : foundations) {
            if (foundation.getId() == id) {
                return foundation;
            }
        }
        return null;
    }

    public Post findPostById(int id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    public Comment findCommentById(int id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                return comment;
            }
        }
        return null;
    }

    public int findPostIndexById(int id) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private String token = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private int currentUserId = 0;

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    private Post tempPost = null;

    public Post getTempPost() {
        return tempPost;
    }

    public void setTempPost(Post tempPost) {
        this.tempPost = tempPost;
    }

    public boolean isFirstStart = true;

    public DataLoadedIf imageLoadedIf = null;

    private Uri tempPicUri;

    public Uri getTempPicUri() {
        return tempPicUri;
    }

    public void setTempPicUri(Uri tempPicUri) {
        this.tempPicUri = tempPicUri;
    }

    public static String profileImageName = "image.jpg";

    private int selectedFoundId = -1;

    public int getSelectedFoundId() {
        return selectedFoundId;
    }

    public void setSelectedFoundId(int selectedFoundId) {
        this.selectedFoundId = selectedFoundId;
    }

    //why we use both selectedUserId and selected user???
    private int selectedUserId = -1;

    public int getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private User selectedUser;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    // Connections
    private ArrayList<User> suggestedUsers = new ArrayList<>();

    public ArrayList<User> getSuggestedUsers() {
        return suggestedUsers;
    }

    private ArrayList<User> searchUsers = new ArrayList<>();

    public ArrayList<User> getSearchUsers() {
        return searchUsers;
    }

    // Following
    private ArrayList<User> followUsers = new ArrayList<>();

    public ArrayList<User> getFollowUsers() {
        return followUsers;
    }
    //Notifications

    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    //Comments

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }
}
