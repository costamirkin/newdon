package cm.com.newdon.classes;

import com.facebook.FacebookRequestError;

import java.net.URL;

/**
 * Class represents foundation
 */

public class Foundation {

    private int id;
    private String title;
    private String description;
    private String nubmer;
    private int yearFounded;
    private String adress;

//    ???
    private String logoUrl;

    private int donatorCount;
    private FoundCategory category;

    public Foundation(int id, String title, FoundCategory category, String adress, String logoUrl) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.adress = adress;
        this.logoUrl = logoUrl;
    }

    public int getId() {
        return id;
    }
}
