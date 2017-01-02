package cm.com.newdon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import cm.com.newdon.R;
import cm.com.newdon.common.RestClient;

public class ProfileFragment extends Fragment {

    private Button saveButton;
    private Button passwordButton;
    private EditText currPswdEd;
    private EditText newPswdEd;
    private EditText new1PswdEd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_profile,container,false);

        currPswdEd = (EditText) v.findViewById(R.id.currPswd);
        newPswdEd  = (EditText) v.findViewById(R.id.newPswd);
        new1PswdEd = (EditText) v.findViewById(R.id.newPswd1);


        saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        passwordButton = (Button) v.findViewById(R.id.passwordButton);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curPswd = currPswdEd.getText().toString();
                String newPswd = newPswdEd.getText().toString();
                String new1Pswd = new1PswdEd.getText().toString();

                if (curPswd.length() < 6 || newPswd.length() < 6 || new1Pswd.length() < 6) {
                    Toast.makeText(getActivity(), "Password to short", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!new1Pswd.equals(newPswd)) {
                    Toast.makeText(getActivity(), "Password doesnt match", Toast.LENGTH_LONG).show();
                    return;
                }

                RequestParams params = new RequestParams();
                params.put("oldPassword", curPswd);
                params.put("password", newPswd);
                params.put("confirmedPassword", new1Pswd);

                //RestClient.post();


            }
        });

        return v;
    }
}
