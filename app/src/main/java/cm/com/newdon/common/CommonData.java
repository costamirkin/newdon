package cm.com.newdon.common;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import cm.com.newdon.classes.FoundCategory;
import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;

/**
 * Class stores common data, like arrays of foundations,...
 */
public class CommonData {

    private List<Foundation> foundations;

    private static CommonData ourInstance = new CommonData();

    public static CommonData getInstance() {
        return ourInstance;
    }

    private CommonData() {
        foundations = new ArrayList<>();
    }

    public List<Foundation> getFoundations() {
        return foundations;
    }

    public Foundation findFoundById(int id){
        for (Foundation foundation: foundations) {
            if (foundation.getId()==id){
                return foundation;
            }
        }
        return null;
    }

    private String token = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Post tempPost = null;

    public Post getTempPost() {
        return tempPost;
    }

    public void setTempPost(Post tempPost) {
        this.tempPost = tempPost;
    }
}
