package numb3r.gte.com.numb3r.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import numb3r.gte.com.numb3r.Fragment.UsherFragment;
import numb3r.gte.com.numb3r.LoginActivity;
import numb3r.gte.com.numb3r.MainActivity;
import numb3r.gte.com.numb3r.Model.Usher;

/**
 * Created by andrewlaurienrsocia on 07/12/2017.
 */

public class WebRequest {

    private static WebRequest webRequest;
    private RequestQueue requestQueue;
    private static Context context;

    public WebRequest(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            requestQueue.start();
        }
        return requestQueue;
    }

    public static synchronized WebRequest getInstance(Context context) {
        if (webRequest == null) {
            webRequest = new WebRequest(context);
        }
        return webRequest;
    }

    public <T> void addToRequestQue(Request<T> request) {
        requestQueue.add(request);
    }

    public void Login(String username, String password, String spin, String imei) {

        String url = Constant.API + "&cmd=" + URLEncoder.encode(Constant.LOGIN);
        url = url + "&username=" + URLEncoder.encode(username);
        url = url + "&password=" + URLEncoder.encode(password);
        url = url + "&spin=" + URLEncoder.encode(spin);
        url = url + "&imei=" + URLEncoder.encode(imei);


        Log.d("URI", url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String result) {

                try {

                    JSONArray response = new JSONArray(result);

                    JSONObject loadwallet = response.getJSONObject(0);//LOADWALLET
                    JSONObject arrProfile = response.getJSONObject(1);//PROFILE
                    JSONArray profile = arrProfile.getJSONArray("PROFILE");

                    if (evaluateResult(loadwallet).equalsIgnoreCase("success")) {


//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<List<Dealer>>() {
//                        }.getType();
//                        List<Dealer> selectedDealer = gson.fromJson(profile.toString(), listType);


                        JSONObject objectprofile = profile.getJSONObject(0);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("State", "LoggedIn");
                        Common.setPreferenceObject(context, objectprofile, "Profile");
                        editor.apply();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        if (context instanceof LoginActivity) {
                            ((LoginActivity) context).finish();
                        }

                    }

                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);


    }

    public void addUsher(final String fullname, final String mobile, String city) {

        String url = Constant.API + "&cmd=" + URLEncoder.encode(Constant.ADDUSHER);
        url = url + "&accountid=" + URLEncoder.encode(MainActivity.usher.getAccountID());
        url = url + "&coordinatornumber=" + URLEncoder.encode(MainActivity.usher.getMobileNumber());
        url = url + "&fullname=" + URLEncoder.encode(fullname);
        url = url + "&mobile=" + URLEncoder.encode(mobile);
        url = url + "&city=" + URLEncoder.encode(city);

        Log.d("URI", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                if (Common.progressDialog != null)
                    if (Common.progressDialog.isShowing())
                        Common.progressDialog.dismiss();

                try {
                    JSONObject response = new JSONObject(result);
                    if (evaluateResult(response).equalsIgnoreCase("success")) {
                        if (Common.usherDialog != null)
                            if (Common.usherDialog.isShowing())
                                Common.usherDialog.dismiss();
                        Usher usher = new Usher();
                        usher.setMobileNumber(mobile);
                        usher.setRepresentative(fullname);
                        MainActivity.ushersList.add(usher);

                        Gson gson = new Gson();
                        String usherlst = gson.toJson(MainActivity.ushersList);

                        Common.setPreferenceObject(context, usherlst, "UsherList");
                        UsherFragment.adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

    }

    public String evaluateResult(JSONObject response) {

        String message = "";
        String resultcode = "";
        String result = "";


        try {
            JSONObject loadwallet = response.getJSONObject("NUMBER");
            message = loadwallet.getString("Message");
            resultcode = loadwallet.getString("ResultCode");
            result = loadwallet.getString("Result");
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return result;

    }

}

