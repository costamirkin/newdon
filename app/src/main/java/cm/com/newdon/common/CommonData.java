package cm.com.newdon.common;

/**
 * Created by costa on 16/12/16.
 */
public class CommonData {
    private static CommonData ourInstance = new CommonData();

    public static CommonData getInstance() {
        return ourInstance;
    }

    private CommonData() {
    }
}
