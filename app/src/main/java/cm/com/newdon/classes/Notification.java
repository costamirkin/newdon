package cm.com.newdon.classes;

import java.util.Date;

/**
 * Class represents notifiction item
 */
public class Notification {

    public enum Type{
        DONATE,SHARE, FOLLOW, UNFOLLOW, LIKE,
        COMMENT, FRIEND_APPROVE, FRIEND_REQUEST;
    }

    public enum ContentType{
        USER, POST, DONATION;
    }

    private int id;
    private User user;
    private Date createdAt;
    private Type type;
    private ContentType contentType;
    private User contentUser;
    private Post contentPost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public User getContentUser() {
        return contentUser;
    }

    public void setContentUser(User contentUser) {
        this.contentUser = contentUser;
    }

    public Post getContentPost() {
        return contentPost;
    }

    public void setContentPost(Post contentPost) {
        this.contentPost = contentPost;
    }
}
