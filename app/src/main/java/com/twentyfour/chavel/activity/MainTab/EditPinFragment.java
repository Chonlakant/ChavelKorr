package com.twentyfour.chavel.activity.MainTab;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twentyfour.chavel.R;
import com.twentyfour.chavel.model.GetMapData;
import com.twentyfour.chavel.util.CustomSupportMapFragment;
import com.twentyfour.chavel.util.MapMarker;
import com.twentyfour.chavel.util.PermissionUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.Bind;


public class EditPinFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        CustomSupportMapFragment.OnMapFragmentReadyListener, LocationListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    Location location;
    private LatLngBounds mInitialMapBounds;

    ImageView btn_expand_toggle;
    private ExpandableLayout expandableLayout0;

    ArrayList<GetMapData> listMap = new ArrayList<>();

    public static EditPinFragment newInstance() {
        return new EditPinFragment();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_pin_edit_activity, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        mMapFragment = CustomSupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.flMapContainer3, mMapFragment).commit();

        expandableLayout0 = (ExpandableLayout) rootView.findViewById(R.id.expandable_layout_0);
        btn_expand_toggle = (ImageView) rootView.findViewById(R.id.btn_expand_toggle);

        toolbar.setTitle("Edit Pin");
        toolbar.inflateMenu(R.menu.menu_phone_email);
        //setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorTitle));
        toolbar.setBackgroundColor(getResources().getColor(R.color.whitePrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout0.isExpanded()) {
                    expandableLayout0.collapse();
                    btn_expand_toggle.setImageResource(R.drawable.ic_down);
                } else {
                    expandableLayout0.expand();
                    btn_expand_toggle.setImageResource(R.drawable.ic_up);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                Log.e("dddd", location.getLongitude() + "");
            } else {
                Log.e("dddd", "dddddddddddd");
            }


        }
    };

    // This method gets called when CustomSupportMapFragment has been initialized and is ready for
    // map initialization
    public void onMapFragmentReady() {
        mMapFragment.getMapAsync(this);  // async create GoogleMap, calls onMapReady when ready

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // button in upper right of map that zooms to current location when pressed
        map.getUiSettings().setMyLocationButtonEnabled(true);
        enableMyLocation();

        // zoom controls in lower right of map
        map.getUiSettings().setZoomControlsEnabled(true);

        mInitialMapBounds = loadMapMarkers(map);
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mInitialMapBounds, 10));
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.e("Location ", latLng.latitude + " :" + latLng.longitude + "");

                GetMapData loction = new GetMapData();
                loction.setLatitude(latLng.latitude);
                loction.setLongitude(latLng.longitude);
                listMap.add(loction);

                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(latLng.latitude, latLng.longitude))
                        .title("Test")
                        .snippet("Thailand"));

                for (int i = 0; i < listMap.size(); i++) {
                    double lati = listMap.get(i).getLatitude();
                    double longLat = listMap.get(i).getLongitude();

                    Log.e("Loop", lati + ": " + longLat);


                }

            }
        });
    }


    private LatLngBounds loadMapMarkers(GoogleMap map) {

        ArrayList<MapMarker> mapMarkers = MapMarker.getMapMarkers();

        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        MarkerOptions markerOptions;
        Marker marker;
        for (MapMarker mapMarker : mapMarkers) {
            markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(mapMarker.getLat(), mapMarker.getLng()));
            marker = map.addMarker(markerOptions);
            bounds.include(marker.getPosition());
        }
        return bounds.build();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     * // https://gist.github.com/MariusVolkhart/618a51bb09c4fc7f86a4
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

            // zoom map to current location, if known
//            LocationManager locationManager =
//                    (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 11);
//                mMap.animateCamera(cameraUpdate);
//            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior till occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(getActivity(), location.getLatitude() + " : " + location.getLongitude(), Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO Add your menu entries here
        inflater.inflate(R.menu.menu_phone_email, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
