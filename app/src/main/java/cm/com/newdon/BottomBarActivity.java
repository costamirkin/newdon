package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class BottomBarActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private int numberNewNotifications = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);

        bottomBar = BottomBar.attach(this, savedInstanceState);

        bottomBar.setItemsFromMenu(R.menu.bottom_bar_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.bottomBarHome:

                        break;
                    case R.id.bottomBarSearch:

                        break;
                    case R.id.bottomBarDonate:
                        startActivity(new Intent(BottomBarActivity.this,FoundationGrid.class));
                        break;
                    case R.id.bottomBarNotification:

                        break;
                    case R.id.bottomBarProfile:

                        break;
                }
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        bottomBar.setActiveTabColor("#C2185B");

        // Badge for the tab notification (index 3), with red background color.
        BottomBarBadge unseenNotification = bottomBar.makeBadgeForTabAt(3, "#E91E63", numberNewNotifications);

        // Control the badge's visibility
        unseenNotification.show();
        //unreadMessages.hide();

    }
}
