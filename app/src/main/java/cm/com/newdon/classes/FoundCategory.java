package cm.com.newdon.classes;

import android.graphics.Color;

/**
 * Class represents foundation category
 */
public class FoundCategory {
    private String name;
    private String color;

    public FoundCategory(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
