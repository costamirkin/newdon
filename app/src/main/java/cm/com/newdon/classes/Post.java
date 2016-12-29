package cm.com.newdon.classes;

import android.net.Uri;

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
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", likeCount=" + likeCount +
                ", commentsCoun=" + commentsCoun +
                ", imageUrl='" + imageUrl + '\'' +
                ", user=" + user +
                ", foundation=" + foundation +
                '}';
    }
}
