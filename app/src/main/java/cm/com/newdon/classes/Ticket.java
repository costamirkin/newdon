package cm.com.newdon.classes;

/**
 * Class represents ticket for lottery
 */
public class Ticket {
    String number;
    String status;
    int postId;

    public Ticket(String number, String status) {
        this.number = number;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
