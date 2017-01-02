package cm.com.newdon.common;

import java.util.ArrayList;
import java.util.List;

import cm.com.newdon.classes.Foundation;
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
    }

    private List<Foundation> foundations;
    public List<Foundation> getFoundations() {
        return foundations;
    }

    private List<Post> posts;
    public List<Post> getPosts() {
        return posts;
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
}
