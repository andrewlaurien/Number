package numb3r.gte.com.numb3r.Common;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import numb3r.gte.com.numb3r.LoginActivity;
import numb3r.gte.com.numb3r.R;

/**
 * Created by andrewlaurienrsocia on 29/11/2017.
 */

public class Common {

    static Dialog usherDialog = null;
    static ProgressDialog progressDialog = null;

    private boolean isNumberValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return email.matches("0[0-9]{10}");
    }

    public static int getRandomNumber() {
        return (new Random()).nextInt(9);
    }

    public static boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumberMatch(String number) {
        return number.matches("0[0-9]{10}");
    }


    public static void setPreferenceObject(Context c, Object model, String key) {
        /**** storing object in preferences  ****/
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(
                c.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        Gson gson = new Gson();
        String jsonObject; // = gson.toJson(model);

        if (key.equalsIgnoreCase("Transactions")) {
            jsonObject = gson.toJson(model);
        } else {
            jsonObject = model.toString();
        }

        Log.d("UserPre", "" + jsonObject);
        prefsEditor.putString(key, jsonObject);//model.toString());
        prefsEditor.commit();

    }

    public static <GenericClass> GenericClass getPreferenceObjectJson(Context c, String key, Class<GenericClass> classType) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        if (sharedPreferences.contains(key)) {
            Gson gson = new Gson();

            Log.d("UserObj", sharedPreferences.getString(key, "").toString());
            return gson.fromJson(sharedPreferences.getString(key, ""), classType);
        }
        return null;

        //  return selectedUser;
    }

    public static String getLongDate() {

        Long currentTime = Calendar.getInstance().getTimeInMillis();
        return currentTime.toString();
    }


    public static String getDeviceImei(Context context) {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                deviceUniqueIdentifier = tm.getDeviceId();
            }
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        LoginActivity.imei = deviceUniqueIdentifier;
        return deviceUniqueIdentifier;
    }

    public static String getHour() {
        String localTime = "";
//        SimpleDateFormat format = new SimpleDateFormat("HH", Locale.US);
//        String hour = format.format(new Date());
//        Calendar calendar = Calendar.getInstance();
//        int hourOfDay = calendar.get(Calendar.HOUR);
//        return String.valueOf(hourOfDay);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        Date currentLocalTime = cal.getTime();
        //DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        // date.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);//24

        if (hourofday < 11) {
            localTime = "11:00 AM";
        }

        if (hourofday > 11 && hourofday < 16) {
            localTime = "4:00 PM";
        }

        if (hourofday > 16 && hourofday < 21) {
            localTime = "9:00 PM";
        }

        //String localTime = date.format(currentLocalTime);

        return localTime;

    }

    public static String getDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    public static void showAddDialog(final Context mcontext) {

        usherDialog = new Dialog(mcontext);
        usherDialog.setCancelable(true);
        usherDialog.setContentView(R.layout.pop_add_usher);

        final EditText txtfullname = (EditText) usherDialog.findViewById(R.id.txtrepresentative);
        final EditText txtmobile = (EditText) usherDialog.findViewById(R.id.txtmobilenumber);
        final EditText txtcity = (EditText) usherDialog.findViewById(R.id.txtcity);
        final Button btnSubmit = usherDialog.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Common.validate(new EditText[]{txtfullname, txtmobile})) {
                    Toast.makeText(mcontext, "Please fill the blank fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Common.isNumberMatch(txtmobile.getText().toString())) {
                    Toast.makeText(mcontext, "Invalid mobile number format.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (NetworkUtil.getConnectivityStatus(mcontext) == 0) {
                    Toast.makeText(mcontext, "Pleas check internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                WebRequest.getInstance(mcontext).addUsher(txtfullname.getText().toString(),
                        txtmobile.getText().toString(), txtcity.getText().toString());

            }
        });


        usherDialog.show();

    }

    public static void showProgress(Context mcontext, String message) {

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

}
