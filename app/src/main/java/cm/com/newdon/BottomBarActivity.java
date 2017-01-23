package cm.com.newdon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.File;

import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoader;
import cm.com.newdon.common.RestClient;
import cm.com.newdon.fragments.FoundationDonatesFragment;
import cm.com.newdon.fragments.HomeFragment;
import cm.com.newdon.fragments.NotificationFragment;
import cm.com.newdon.fragments.ProfileDonatesFragment;
import cm.com.newdon.fragments.ProfileFragment;
import cm.com.newdon.fragments.SearchFragment;
import cm.com.newdon.fragments.SettingsFragment;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class BottomBarActivity extends AppCompatActivity implements HomeFragment.OnPostSelectedListener {

    private BottomBar bottomBar;



    private int numberNewNotifications = 5;

    HomeFragment              homeFragment = new HomeFragment();
    SearchFragment            searchFragment = new SearchFragment();
    ProfileFragment           profileFragment = new ProfileFragment();
    ProfileDonatesFragment    profileDonatesFragment = new ProfileDonatesFragment();
    NotificationFragment      notificationFragment = new NotificationFragment();
    SettingsFragment          settingsFragment = new SettingsFragment();
    FoundationDonatesFragment foundationDonatesFragment = new FoundationDonatesFragment();
    CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);

        setupBottomBar();

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

//            DataLoader.getSuggestedUsers(getApplicationContext());

//            posts of current user
//            DataLoader.getUserPosts(getApplicationContext(), CommonData.getInstance().getCurrentUserId());
            DataLoader.getAllFoundations();
            DataLoader.getFeaturedLotteries();
            CommonData.getInstance().isFirstStart = false;
        }
    }

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
                        commitFragment(searchFragment);
                        break;
                    case R.id.bottomBarDonate:
//                        startActivity(new Intent(BottomBarActivity.this, FoundationGrid.class));
                        break;
                    case R.id.bottomBarNotification:
                        //followUser(165);
                        commitFragment(notificationFragment);
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

        // Badge for the tab notification (index 3), with red background color.
        BottomBarTab notifications = bottomBar.getTabWithId(R.id.bottomBarNotification);
        notifications.setBadgeCount(numberNewNotifications);

        // Remove the badge when you're done with it.
//        notifications.removeBadge();
    }

    private void followUser(int userId) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        RestClient.put("connections/follow", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(new String(responseBody));

            }
        });
    }

    public void commitFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CommonData.getInstance().imageLoadedIf = null;
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
}
