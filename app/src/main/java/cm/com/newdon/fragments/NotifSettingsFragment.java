package cm.com.newdon.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import cm.com.newdon.R;
import cm.com.newdon.SignAcitvity;

public class NotifSettingsFragment extends Fragment {

    private ToggleButton allowPush;
    private ToggleButton enableSound;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_notif_settings,container,false);
        settings = getActivity().getSharedPreferences("settings", 0);
        editor   = settings.edit();

        ImageView back = (ImageView) v.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(NotifSettingsFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        allowPush = (ToggleButton) v.findViewById(R.id.allowPush);
        allowPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        enableSound = (ToggleButton) v.findViewById(R.id.enableSound);
        enableSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}
