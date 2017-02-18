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

    private int id;
    private User user;
    private Date createdAt;
    private Type type;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
