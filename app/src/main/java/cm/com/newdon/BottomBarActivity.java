package cm.com.newdon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import cm.com.newdon.adapters.FoundationsAdapter;
import cm.com.newdon.adapters.PostsAdapter;

public class BottomBarActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private int numberNewNotifications = 5;
    private LinearLayout lHome;
    private LinearLayout lSearch;
    private LinearLayout lNotification;
    private LinearLayout lProfile;
    private LinearLayout layouts[];
    private PostsAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);
        setLayouts();

        setupBottomBar(savedInstanceState);

        postsAdapter = new PostsAdapter(getApplicationContext());
        ListView postsLV = (ListView) lHome.findViewById(R.id.lvPosts);
        postsLV.setAdapter(postsAdapter);

    }

    private void setLayouts(){
        lHome = (LinearLayout) findViewById(R.id.homeLayout);
        lSearch = (LinearLayout) findViewById(R.id.searchLayout);
        lNotification = (LinearLayout) findViewById(R.id.notificationLayout);
        lProfile = (LinearLayout) findViewById(R.id.profileLayout);
        layouts = new LinearLayout[4];
        layouts[0]=lHome;
        layouts[1]=lSearch;
        layouts[2]=lNotification;
        layouts[3]=lProfile;
    }

    private void changeLayout(int layoutIndex){
        for (int i = 0; i < layouts.length; i++) {
            layouts[i].setVisibility(View.INVISIBLE);
        }
        layouts[layoutIndex].setVisibility(View.VISIBLE);
    }

    private void setupBottomBar(Bundle savedInstanceState){
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_bar_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.bottomBarHome:
                        changeLayout(0);
                        break;
                    case R.id.bottomBarSearch:
                        changeLayout(1);
                        break;
                    case R.id.bottomBarDonate:
                        startActivity(new Intent(BottomBarActivity.this,FoundationGrid.class));
                        break;
                    case R.id.bottomBarNotification:
                        changeLayout(2);
                        break;
                    case R.id.bottomBarProfile:
                        changeLayout(3);
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
