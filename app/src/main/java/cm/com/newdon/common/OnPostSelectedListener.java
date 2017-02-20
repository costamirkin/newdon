package cm.com.newdon.common;

/**
 * Created by Marina on 20.02.2017.
 */
// Container Activity must implement this interface
public interface OnPostSelectedListener {
    void onFoundationSelected(int foundId);

    void onUserSelected(int userId);
}
