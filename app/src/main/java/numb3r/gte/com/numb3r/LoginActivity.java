package numb3r.gte.com.numb3r;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import numb3r.gte.com.numb3r.Common.Common;
import numb3r.gte.com.numb3r.Common.NetworkUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known usher names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // UI references.
    private EditText mMobileNumber;
    private EditText mPasswordView;
    private EditText mSPIN;
    private View mProgressView;
    private View mLoginFormView;
    final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0001;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        String state = preferences.getString("State", "");
        if (state.equalsIgnoreCase("LoggedIn")) {
            this.finish();
        }


        // Set up the login form.
        mMobileNumber = (EditText) findViewById(R.id.number);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSPIN = (EditText) findViewById(R.id.spin);

        mMobileNumber.setText("09951231231");
        mPasswordView.setText("password");
        mSPIN.setText("1234");


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                //Common.getDeviceImei(getBaseContext());
                //Toast.makeText(getBaseContext(), Common.getDeviceImei(getBaseContext()), Toast.LENGTH_SHORT).show();
                //saveImei();
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                Common.getDeviceImei(getBaseContext());
                Toast.makeText(getBaseContext(), Common.getDeviceImei(getBaseContext()), Toast.LENGTH_SHORT).show();
                saveImei();
            }
        }
    }

    public void saveImei() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.putString("imei", Common.getDeviceImei(getBaseContext()));
        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getBaseContext(), Common.getDeviceImei(getBaseContext()), Toast.LENGTH_SHORT).show();
                    saveImei();
                } else {
                    LoginActivity.this.finish();
                }
                break;
        }

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mMobileNumber.setError(null);
        mPasswordView.setError(null);
        mSPIN.setError(null);

        // Store values at the time of the login attempt.
        String number = mMobileNumber.getText().toString();
        String password = mPasswordView.getText().toString();
        String spin = mSPIN.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (NetworkUtil.getConnectivityStatus(getBaseContext()) == 0) {
            cancel = true;
            Toast.makeText(getBaseContext(), "Pleas check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check for a valid password, if the usher entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid spin, if the usher entered one.
        if (TextUtils.isEmpty(spin)) {
            mSPIN.setError(getString(R.string.error_invalid_spin));
            focusView = mSPIN;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(number)) {
            mMobileNumber.setError(getString(R.string.error_field_required));
            focusView = mMobileNumber;
            cancel = true;
        } else if (!isNumberValid(number)) {
            mMobileNumber.setError(getString(R.string.error_invalid_email));
            focusView = mMobileNumber;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the usher login attempt.


            JSONObject objectprofile = new JSONObject();

            try {
                objectprofile.put("AccountID", "12345");
                objectprofile.put("FinancierID", "12345");
                objectprofile.put("CoordinatorID", "12345");
                objectprofile.put("Representative", "Drew Developer");
                objectprofile.put("Profile", "USHER");
                objectprofile.put("TASNumber", "09951354943");
            }catch (JSONException e){
                e.printStackTrace();
            }

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("State", "LoggedIn");
            Common.setPreferenceObject(getBaseContext(), objectprofile, "Profile");
            editor.apply();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            //Commented for this occasion.
            //WebRequest.getInstance(getBaseContext()).Login(number, password, spin, imei);
        }
    }

    private boolean isNumberValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return email.matches("0[0-9]{10}");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}

