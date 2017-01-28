package cm.com.newdon.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cm.com.newdon.R;
import cm.com.newdon.adapters.NotificationsAdapter;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.DataLoadedIf;
import cm.com.newdon.common.DataLoader;

public class NotificationFragment extends Fragment implements DataLoadedIf {
    final static boolean NOTIFICATIONS = false;
    final static boolean ACTIVITIES = true;

    ListView listView;
    TextView tvNoNotifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        DataLoader.getNotificationList(NOTIFICATIONS);

        listView = (ListView) view.findViewById(R.id.lvNotifications);
        listView.setAdapter(new NotificationsAdapter(getActivity().getApplicationContext()));
        listView.invalidateViews();

        tvNoNotifications = (TextView) view.findViewById(R.id.tvNoNotifications);

        final TextView tvNotifications = (TextView) view.findViewById(R.id.tvNotifications);
        final TextView tvActivity = (TextView) view.findViewById(R.id.tvActivity);

        tvNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextViewColors(tvNotifications, tvActivity);
                DataLoader.getNotificationList(NOTIFICATIONS);
            }
        });
        tvActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextViewColors(tvActivity, tvNotifications);
                DataLoader.getNotificationList(ACTIVITIES);
            }
        });

        return view;
    }

    private void changeTextViewColors(TextView activeView, TextView inactiveView) {
        activeView.setTextColor(getActivity().getResources().getColor(R.color.white));
        activeView.setBackgroundResource(R.drawable.rectangle_38);
        inactiveView.setBackgroundColor(Color.WHITE);
        inactiveView.setTextColor(getActivity().getResources().getColor(R.color.grey));
    }

    @Override
    public void onStart() {
        super.onStart();
        CommonData.getInstance().imageLoadedIf = this;
    }

    @Override
    public void onStop() {
        super.onStop();
        CommonData.getInstance().imageLoadedIf = null;
    }

    @Override
    public void imageLoaded(int postId) {
    }

    @Override
    public void dataLoaded() {
        if(CommonData.getInstance().getNotifications().size()==0){
            tvNoNotifications.setVisibility(View.VISIBLE);
        }else {
            tvNoNotifications.setVisibility(View.INVISIBLE);
        }
        listView.invalidateViews();
    }
}
