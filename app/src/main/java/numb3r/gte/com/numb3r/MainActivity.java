package numb3r.gte.com.numb3r;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import numb3r.gte.com.numb3r.Common.Common;
import numb3r.gte.com.numb3r.Fragment.BetFragment;
import numb3r.gte.com.numb3r.Fragment.TxnFragment;
import numb3r.gte.com.numb3r.Fragment.UsherFragment;
import numb3r.gte.com.numb3r.Model.MyNumbers;
import numb3r.gte.com.numb3r.Model.Transaction;
import numb3r.gte.com.numb3r.Model.Usher;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    public static ArrayList<MyNumbers> myNumbers = new ArrayList<>();
    public static ArrayList<Transaction> transction = new ArrayList<>();
    public static Context mcontext;
    final int PERMISSIONS_REQUEST_SENT_SMS = 0005;
    BetFragment betFragment;
    public static Usher usher;
    public static TextView txtfullname;
    public static TextView txtinitial;
    NavigationView navigationView;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;


    //For Coordinator;
    public static ArrayList<Usher> ushersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        betFragment = new BetFragment();
        mcontext = this;

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        txtfullname = (TextView) header.findViewById(R.id.txtfullname);
        txtinitial = (TextView) header.findViewById(R.id.txtinitial);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                        PERMISSIONS_REQUEST_SENT_SMS);
            } else {

            }
        }

        View navFooter1 = findViewById(R.id.LogOut);
        navFooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mcontext);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("State", "Logout");
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        // Dealer selectedUser = new Dealer();
        usher = Common.getPreferenceObjectJson(mcontext, "Profile", Usher.class);


        if (usher != null) {
            setProfile();
        }

        prepareItemMenu();


    }

    public static void setProfile() {
        Log.d("Data", "" + MainActivity.usher.getRepresentative());
        Log.d("Data", "" + MainActivity.usher.getFirstNameChar());
        Log.d("Data", "" + MainActivity.usher.getFinancierID());
        Log.d("Data", "" + MainActivity.usher.getCoordinatorID());
        Log.d("Data", "" + MainActivity.usher.getProfile());
        txtfullname.setText(MainActivity.usher.getRepresentative());
        txtinitial.setText("" + MainActivity.usher.getFirstNameChar());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_SENT_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getBaseContext(), Common.getDeviceImei(getBaseContext()), Toast.LENGTH_SHORT).show();
                    //saveImei();
                } else {

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void prepareItemMenu() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        Fragment fragment = null;

        if (MainActivity.usher.getProfile().equalsIgnoreCase("Usher")) {
            nav_Menu.findItem(R.id.nav_bet).setVisible(true);
            nav_Menu.findItem(R.id.nav_txn).setVisible(true);
            nav_Menu.findItem(R.id.nav_add).setVisible(false);
            fragment = new BetFragment();
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        } else {
            nav_Menu.findItem(R.id.nav_bet).setVisible(false);
            nav_Menu.findItem(R.id.nav_txn).setVisible(false);
            nav_Menu.findItem(R.id.nav_add).setVisible(true);
            fragment = new UsherFragment();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.maincontent, fragment).commit();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_bet:
                fragment = new BetFragment();
                break;
            case R.id.nav_txn:
                fragment = new TxnFragment();
                break;
            case R.id.nav_add:
                fragment = new UsherFragment();
                break;
        }
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.maincontent, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
