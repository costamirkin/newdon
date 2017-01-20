package cm.com.newdon.common;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Lottery;
import cm.com.newdon.classes.Post;

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
        foundationPosts = new ArrayList<>();
        featuredLotteries = new ArrayList<>();
        lotteryList = new ArrayList<>();
    }

    private List<Foundation> foundations;
    public List<Foundation> getFoundations() {
        return foundations;
    }

    private List<Post> posts;
    public List<Post> getPosts() {
        return posts;
    }

    private List<Post> foundationPosts;
    public List<Post> getFoundationPosts() {
        return foundationPosts;
    }

    private List<Lottery> featuredLotteries;
    public List<Lottery> getFeaturedLotteries() {
        return featuredLotteries;
    }

    private List<Lottery> lotteryList;
    public List<Lottery> getLotteryList() {
        return lotteryList;
    }

    public Foundation findFoundById(int id){
        for (Foundation foundation: foundations) {
            if (foundation.getId()==id){
                return foundation;
            }
        }
        return null;
    }

    public Post findPostById(int id){
        for (Post post: posts) {
            if (post.getId()==id){
                return post;
            }
        }
        return null;
    }

    public int findPostIndexById(int id){
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId()==id){
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

    private int currentFoundId = -1;

    public int getCurrentFoundId() {
        return currentFoundId;
    }

    public void setCurrentFoundId(int currentFoundId) {
        this.currentFoundId = currentFoundId;
    }
}
