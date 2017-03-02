package cm.com.newdon.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import cm.com.newdon.R;
import cm.com.newdon.SignAcitvity;
import cm.com.newdon.common.CommonData;

public class SettingsFragment extends Fragment {

    private Button notificationSettingsButton;
    private Button linkedAccountsButton;
    private Button findFriendsButton;
    private Button tutorialButton;
    private Button logoutButton;
    private ImageView supportBg;

    NotifSettingsFragment notifSettingsFragment = new NotifSettingsFragment();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_settings,container,false);


        supportBg = (ImageView) v.findViewById(R.id.supportBg);
        supportBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "asdfasd", Toast.LENGTH_LONG).show();
            }
        });

        notificationSettingsButton = (Button) v.findViewById(R.id.notificationSettings);
        notificationSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, notifSettingsFragment);

                ft.addToBackStack("This Fragment");
                ft.commit();

            }
        });

        linkedAccountsButton = (Button) v.findViewById(R.id.linkedAccounts);
        linkedAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findFriendsButton = (Button) v.findViewById(R.id.findFriends);
        findFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonData.bottomBarActivity.changeToConnectionsFragment();

            }
        });

        tutorialButton = (Button) v.findViewById(R.id.tutorial);
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logoutButton = (Button) v.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("email");
                editor.remove("password");
                editor.commit();
                CommonData.getInstance().isFirstStart = true;
                Intent intent = new Intent(getActivity(), SignAcitvity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        return v;
    }
}
