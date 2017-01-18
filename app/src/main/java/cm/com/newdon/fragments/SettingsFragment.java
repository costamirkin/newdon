package cm.com.newdon.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cm.com.newdon.R;
import cm.com.newdon.SignAcitvity;

public class SettingsFragment extends Fragment {

    private Button notificationSettingsButton;
    private Button linkedAccountsButton;
    private Button findFriendsButton;
    private Button tutorialButton;
    private Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_settings,container,false);



        notificationSettingsButton = (Button) v.findViewById(R.id.notificationSettings);
        notificationSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Intent intent = new Intent(getActivity(), SignAcitvity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        return v;
    }
}
