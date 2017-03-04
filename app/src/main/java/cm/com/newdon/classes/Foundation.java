package cm.com.newdon.classes;

import android.graphics.Bitmap;

/**
 * Class represents foundation
 */

public class Foundation {

    private int id;
    private String title;
    private String description;
    private String number;
    private int yearFounded;
    private String address;
    private int followersCount;
    private String headquarter;


    //    ???
    private String logoUrl;
    private Bitmap logo;

    private int donatorCount;
    private FoundCategory category;

    public int[] defaultPicsId = new int[5];
    public String[] defaultPicsUrl = new String[5];

    public Foundation(
            int id,
            String title,
            String description,
            String number,
            int yearFounded,
            String address,
            int followersCount,
            String headquarter,
            String logoUrl,
            int donatorCount,
            FoundCategory category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.number = number;
        this.yearFounded = yearFounded;
        this.address = address;
        this.followersCount = followersCount;
        this.headquarter = headquarter;
        this.logoUrl = logoUrl;
        this.donatorCount = donatorCount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(int yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getDonatorCount() {
        return donatorCount;
    }

    public void setDonatorCount(int donatorCount) {
        this.donatorCount = donatorCount;
    }

    public FoundCategory getCategory() {
        return category;
    }

    public void setCategory(FoundCategory category) {
        this.category = category;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Foundation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", logoUrl='" + logoUrl + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }
}
