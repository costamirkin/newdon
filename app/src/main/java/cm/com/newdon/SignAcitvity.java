package cm.com.newdon;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cm.com.newdon.adapters.ViewPagerAdapter;
import cm.com.newdon.classes.SlidingTabLayout;

public class SignAcitvity extends ActionBarActivity {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"LOGIN", "SIGNUP","FACEBOOK"};
    int Numboftabs = 3;

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            ImageView bgImage = (ImageView) findViewById(R.id.bgImage);
            ViewGroup.LayoutParams params1 = bgImage.getLayoutParams();
            ImageView logo = (ImageView) findViewById(R.id.logo);
            ViewGroup.LayoutParams params2 = logo.getLayoutParams();

            if(heightDiff <= 100){
                onHideKeyboard();
                params1.height=300;
                bgImage.setLayoutParams(params1);
                params2.height=200;
                logo.setLayoutParams(params2);

            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                rootLayout.invalidate();
                onShowKeyboard(keyboardHeight);
                params1.height=200;
                bgImage.setLayoutParams(params1);
                params2.height=100;
                logo.setLayoutParams(params2);

            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard(int keyboardHeight) {}
    protected void onHideKeyboard() {}

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = (ViewGroup) findViewById(R.id.rootLayout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_acitvity);


        // Creating The Toolbar and setting it as the Toolbar for the activity

//        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });


        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        attachKeyboardListeners();


    }


}