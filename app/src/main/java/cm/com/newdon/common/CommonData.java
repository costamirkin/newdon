package cm.com.newdon.common;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import cm.com.newdon.classes.FoundCategory;
import cm.com.newdon.classes.Foundation;

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
        testFoundations();

    }

    private void testFoundations(){
        foundations.add(new Foundation(1,"Test1",
                        new FoundCategory("category1", "#6d9eeb"), "Blalala",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT6Dj2qBfHrdn_av0opAy7K5ufv7qpcK1mVmuOj1We8vp8nYK1I"));
        foundations.add(new Foundation(1,"Test2",
                        new FoundCategory("category1", "#6d9eeb"), "Blalala",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT6Dj2qBfHrdn_av0opAy7K5ufv7qpcK1mVmuOj1We8vp8nYK1I"));
        foundations.add(new Foundation(1, "Test3",
                new FoundCategory("category1", "#6d9eeb"), "Blalala",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT6Dj2qBfHrdn_av0opAy7K5ufv7qpcK1mVmuOj1We8vp8nYK1I"));
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
}
