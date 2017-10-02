package com.twentyfour.chavel;

import android.Manifest;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TabHost;

import com.twentyfour.chavel.fragment.NewRouteTabFragment;
import com.twentyfour.chavel.fragment.NotiFragment;
import com.twentyfour.chavel.fragment.SearchFragment;
import com.twentyfour.chavel.fragment.TabMyRouteFragment;
import com.twentyfour.chavel.fragment.UserProfileFragment;

public class MainTabActivity extends TabActivity {

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        ActivityCompat.requestPermissions(MainTabActivity.this,
                new String[]{Manifest.permission.CAMERA},
                2);
        ActivityCompat.requestPermissions(MainTabActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        ActivityCompat.requestPermissions(MainTabActivity.this,
                new String[]{Manifest.permission.LOCATION_HARDWARE},
                3);

        ActivityCompat.requestPermissions(MainTabActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                4);

        ActivityCompat.requestPermissions(MainTabActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                5);


        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        spec = tabHost.newTabSpec(""); // Create a new TabSpec using tab host
        Drawable dr1 = ResourcesCompat.getDrawable(getResources(), R.drawable.home_tab1, null);
        spec.setIndicator("", dr1); // set the “HOME” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, TabMyRouteFragment.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs

        spec = tabHost.newTabSpec(""); // Create a new TabSpec using tab host
        Drawable dr2 = ResourcesCompat.getDrawable(getResources(), R.drawable.home_tab2, null);
        spec.setIndicator("", dr2); // set the “CONTACT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, SearchFragment.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(""); // Create a new TabSpec using tab host
        Drawable dr3 = ResourcesCompat.getDrawable(getResources(), R.drawable.home_tab3, null);
        spec.setIndicator("", dr3); // set the “ABOUT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, NewRouteTabFragment.class);
        spec.setContent(intent);
        tabHost.addTab(spec);


        spec = tabHost.newTabSpec(""); // Create a new TabSpec using tab host
        Drawable dr4 = ResourcesCompat.getDrawable(getResources(), R.drawable.home_tab4, null);
        spec.setIndicator("", dr4); // set the “ABOUT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, NotiFragment.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(""); // Create a new TabSpec using tab host
        Drawable dr5 = ResourcesCompat.getDrawable(getResources(), R.drawable.home_tab5, null);
        spec.setIndicator("", dr5); // set the “ABOUT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, UserProfileFragment.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        //set tab which one you want to open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // display the name of the tab whenever a tab is changed
                // Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                   // Toast.makeText(MainTabActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 2: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                   // Toast.makeText(MainTabActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 3: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                   // Toast.makeText(MainTabActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 4: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                   // Toast.makeText(MainTabActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 5: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                  //  Toast.makeText(MainTabActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}