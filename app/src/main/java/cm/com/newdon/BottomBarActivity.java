package cm.com.newdon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.File;

import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.OnPostSelectedListener;
import cm.com.newdon.fragments.ConnectionsFragment;
import cm.com.newdon.fragments.FoundationDonatesFragment;
import cm.com.newdon.fragments.HomeFragment;
import cm.com.newdon.fragments.NotificationFragment;
import cm.com.newdon.fragments.ProfileDonatesFragment;
import cm.com.newdon.fragments.SearchFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class BottomBarActivity extends AppCompatActivity implements OnPostSelectedListener {

    private BottomBar bottomBar;

    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    ProfileDonatesFragment profileDonatesFragment = new ProfileDonatesFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    FoundationDonatesFragment foundationDonatesFragment = new FoundationDonatesFragment();
    ConnectionsFragment connectionsFragment = new ConnectionsFragment();
    CircleImageView profileImage;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);

        setupBottomBar();
        Intent intent = getIntent();
        String signup = intent.getStringExtra("signup");
        if (signup == null) {
            bottomBar.setDefaultTabPosition(0);
        }
        profileImage = (CircleImageView) findViewById(R.id.profileImage);
        File profileImageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                CommonData.profileImageName);
        if (profileImageFile.exists()) {
            profileImage.setImageURI(null);
            profileImage.setImageURI(Uri.fromFile(profileImageFile));
        }

//        ProgressDialog progressDialog =
//                ProgressDialog.show(getApplicationContext(), "Altru", "Udpadting posts...", true);
//        progressDialog.show();

        if (CommonData.getInstance().isFirstStart) {
//            get userID (and download posts?)
            DataLoader.getUserId(getApplicationContext());

//            get posts for home screen
            DataLoader.getHomeScreenPosts(getApplicationContext());

//            foundations
            DataLoader.getAllFoundations();

//            lottery
            DataLoader.getFeaturedLotteries();
            CommonData.getInstance().isFirstStart = false;
        }

        final RelativeLayout layoutDonSuccess = (RelativeLayout) findViewById(R.id.layoutDonSuccessful);
        layoutDonSuccess.setVisibility(View.GONE);

//        when return to the activity after Donate
        if (intent.getIntExtra("success", 0) == 1) {
            //            renew posts for home screen
            DataLoader.getHomeScreenPosts(getApplicationContext());

            layoutDonSuccess.setVisibility(View.VISIBLE);
            ImageView imClose = (ImageView) findViewById(R.id.ivClose);
            imClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutDonSuccess.setVisibility(View.GONE);
                }
            });
            layoutDonSuccess.postDelayed(new Runnable() {
                public void run() {
                    layoutDonSuccess.setVisibility(View.GONE); } }, 3000);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }



    private void setupBottomBar() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.bottomBarHome:
                        commitFragment(homeFragment);
                        break;
                    case R.id.bottomBarSearch:
                        commitFragment(searchFragment);
                        break;
                    case R.id.bottomBarDonate:
                        commitFragment(connectionsFragment);
                        break;
                    case R.id.bottomBarNotification:
                        commitFragment(notificationFragment);
                        //commitFragment(connectionsFragment);
                        break;
                    case R.id.bottomBarProfile:
                        CommonData.getInstance().setSelectedUser(CommonData.getInstance().getCurrentUser());
                        CommonData.getInstance().setSelectedUserId(CommonData.getInstance().getCurrentUserId());
                        commitFragment(profileDonatesFragment);
                        break;
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.bottomBarHome) {
                    commitFragment(homeFragment);
                }
            }
        });
    }

    public void commitFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        if (currentFragment != null && currentFragment.equals(fragment)) {
            fragmentTransaction.detach(currentFragment);
            fragmentTransaction.attach(currentFragment);
        }
        else {
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack("This Fragment");
        }

        fragmentTransaction.commit();
    }

    public void startDonate(View view) {
        startActivity(new Intent(BottomBarActivity.this, FoundationGrid.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFoundationSelected(int foundId) {
        CommonData.getInstance().setSelectedFoundId(foundId);
        commitFragment(foundationDonatesFragment);
    }

    @Override
    public void onUserSelected(int userId) {
        CommonData.getInstance().setSelectedUserId(userId);
        commitFragment(profileDonatesFragment);
    }
    
    
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BottomBar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cm.com.newdon/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BottomBar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cm.com.newdon/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void changeNotificationBadge(){
        // Badge for the tab notification (index 3), with red background color.
        BottomBarTab notifications = bottomBar.getTabWithId(R.id.bottomBarNotification);
        int notificationCounter = CommonData.getInstance().getNotificationCounter();
        if (notificationCounter == 0) {
            // Remove the badge when you're done with it.
            notifications.removeBadge();
        }else {
            notifications.setBadgeCount(notificationCounter);
        }
    }
}
