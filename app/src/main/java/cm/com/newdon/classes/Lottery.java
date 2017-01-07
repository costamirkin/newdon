package cm.com.newdon.classes;

import java.util.List;

/**
 * Class represents lottery
 */
public class Lottery {

    int id;
    String title;
    String status;
    String logoUrl;
    String imageUrl;
    String scheduleDay;
    String promoText;
    String description;
    String comfortText;
    boolean isYouWin;
    int participantCount;
    List<Ticket> tickets;

    public Lottery(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(String scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public String getPromoText() {
        return promoText;
    }

    public void setPromoText(String promoText) {
        this.promoText = promoText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComfortText() {
        return comfortText;
    }

    public void setComfortText(String comfortText) {
        this.comfortText = comfortText;
    }

    public boolean isYouWin() {
        return isYouWin;
    }

    public void setIsYouWin(boolean isYouWin) {
        this.isYouWin = isYouWin;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
