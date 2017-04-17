package cm.com.newdon.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cm.com.newdon.R;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.RestClient;
import cm.com.newdon.common.Utils;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final int REQUESTCAMERA = 0;

    private Button saveButton;
    private Button passwordButton;
    private EditText currPswdEd;
    private EditText newPswdEd;
    private EditText new1PswdEd;
    private EditText nameEt;
    private EditText nameRealEt;
    private EditText emailEt;
    private ToggleButton toggleButton;
    private ImageView changeImage;
    private ImageView backBtn;

    private CircleImageView profileImage;

    private Uri profileImageUri = null; // Profile image uri

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_profile,container,false);

        currPswdEd   = (EditText) v.findViewById(R.id.currPswd);
        newPswdEd    = (EditText) v.findViewById(R.id.newPswd);
        new1PswdEd   = (EditText) v.findViewById(R.id.newPswd1);
        nameEt       = (EditText) v.findViewById(R.id.nameEt);
        nameEt.setText(CommonData.getInstance().getCurrentUser().getUserName());
        nameRealEt   = (EditText) v.findViewById(R.id.nameRealEt);
        nameRealEt.setText(CommonData.getInstance().getCurrentUser().getRealName());
        emailEt      = (EditText) v.findViewById(R.id.emailEt);
        emailEt.setText(CommonData.getInstance().getCurrentUser().getEmail());
        toggleButton = (ToggleButton) v.findViewById(R.id.privacyToggle);
        profileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        changeImage  = (ImageView) v.findViewById(R.id.edit_btn);
        backBtn      = (ImageView) v.findViewById(R.id.backBtn);

        File profileImageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                CommonData.profileImageName);
        if (profileImageFile.exists()) {
            profileImage.setImageURI(null);
            profileImageUri = Uri.fromFile(profileImageFile);
            profileImage.setImageURI(profileImageUri);
        }

        saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("username", nameEt.getText().toString());
                params.put("firstName", nameRealEt.getText().toString());
                params.put("lastName", nameRealEt.getText().toString());
                params.put("email", emailEt.getText().toString());
                params.put("isPrivate", toggleButton.isChecked());

                RestClient.post("account/profile", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //Utils.showAlertDialog("DETAILS CHANGED", getActivity());
                        SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("firstName", nameEt.getText().toString());
                        editor.putString("lastName", nameRealEt.getText().toString());
                        editor.putString("email", emailEt.getText().toString());
                        editor.putBoolean("isPrivate", toggleButton.isChecked());
                        editor.commit();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Utils.showAlertDialog("DETAILS CHANGED " + responseBody != null ? new String(responseBody) : "", getActivity());


                    }
                });

                // profile image
                if (profileImageUri != null) {
                    RequestParams imageParams = new RequestParams();
                    try {
                        String str = Utils.getRealPathFromURI(profileImageUri, getActivity().getContentResolver());
                        imageParams.put("image", new File(str));
                        RestClient.post("account/photo", imageParams, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Utils.showAlertDialog("DETAILS CHANGED", getActivity());
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Utils.showAlertDialog("IMAGE CHANGED " + responseBody != null ? new String(responseBody) : "", getActivity());

                            }
                        });

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }


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
                    Utils.showAlertDialog("Password too short", getActivity());
                    return;
                }

                if (!new1Pswd.equals(newPswd)) {
                    Utils.showAlertDialog("Password  doesnt match", getActivity());
                    return;
                }

                RequestParams params = new RequestParams();
                params.put("oldPassword", curPswd);
                params.put("password", newPswd);
                params.put("confirmedPassword", new1Pswd);

                RestClient.post("account/change-password", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password", newPswdEd.getText().toString());
                        editor.commit();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getActivity(), "Failed to change password", Toast.LENGTH_LONG).show();


                    }
                });
            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                            CommonData.profileImageName);
                    profileImageUri = Uri.fromFile(output);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, profileImageUri);
                    //start camera intent
                    startActivityForResult(intent, REQUESTCAMERA);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(ProfileFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUESTCAMERA) {
                profileImage.setImageURI(null);
                profileImage.setImageURI(profileImageUri);

            }
        }
    }
}
