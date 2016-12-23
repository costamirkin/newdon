package cm.com.newdon;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import cm.com.newdon.adapters.PostsAdapter;
import cm.com.newdon.fragments.HomeFragment;
import cm.com.newdon.fragments.SearchFragment;

public class BottomBarActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private int numberNewNotifications = 5;
//    private LinearLayout lHome;
//    private LinearLayout lSearch;
//    private LinearLayout lNotification;
//    private LinearLayout lProfile;
//    private LinearLayout layouts[];
    private PostsAdapter postsAdapter;

    HomeFragment homeFragment = new HomeFragment();
    SearchFragment seachFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);

//        setLayouts();

        setupBottomBar();

//        postsAdapter = new PostsAdapter(getApplicationContext());
//        ListView postsLV = (ListView) lHome.findViewById(R.id.lvPosts);
//        postsLV.setAdapter(postsAdapter);

    }

//    private void setLayouts(){
//        lHome = (LinearLayout) findViewById(R.id.homeLayout);
//        lSearch = (LinearLayout) findViewById(R.id.searchLayout);
//        lNotification = (LinearLayout) findViewById(R.id.notificationLayout);
//        lProfile = (LinearLayout) findViewById(R.id.profileLayout);
//        layouts = new LinearLayout[4];
//        layouts[0]=lHome;
//        layouts[1]=lSearch;
//        layouts[2]=lNotification;
//        layouts[3]=lProfile;
//    }

//    private void changeLayout(int layoutIndex){
//        for (int i = 0; i < layouts.length; i++) {
//            layouts[i].setVisibility(View.INVISIBLE);
//        }
//        layouts[layoutIndex].setVisibility(View.VISIBLE);
//    }

    private void setupBottomBar(){
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.bottomBarHome:
                        commitFragment(homeFragment);
                        break;
                    case R.id.bottomBarSearch:
                        commitFragment(seachFragment);
//                        changeLayout(1);
                        break;
                    case R.id.bottomBarDonate:
                        startActivity(new Intent(BottomBarActivity.this, FoundationGrid.class));
                        break;
                    case R.id.bottomBarNotification:
//                        changeLayout(2);
                        break;
                    case R.id.bottomBarProfile:
//                        changeLayout(3);
                        break;
                }
            }
        });

        // Badge for the tab notification (index 3), with red background color.
        BottomBarTab notifications = bottomBar.getTabWithId(R.id.bottomBarNotification);
        notifications.setBadgeCount(numberNewNotifications);

        // Remove the badge when you're done with it.
//        notifications.removeBadge();
    }

    private void commitFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
