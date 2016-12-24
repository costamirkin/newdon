package cm.com.newdon.classes;

import java.sql.Date;

/**
 * Class represents one donation post
 */
public class Post {
    private int id;
    private String message;
    private Date createdAt;
    private int likeCount;
    private int commentsCoun;
    private String imageUrl;
    private User user;
    private Foundation foundation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentsCoun() {
        return commentsCoun;
    }

    public void setCommentsCoun(int commentsCoun) {
        this.commentsCoun = commentsCoun;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Foundation getFoundation() {
        return foundation;
    }

    public void setFoundation(Foundation foundation) {
        this.foundation = foundation;
    }
}
