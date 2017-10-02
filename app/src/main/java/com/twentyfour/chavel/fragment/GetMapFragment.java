package com.twentyfour.chavel.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;


public class GetMapFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        CustomSupportMapFragment.OnMapFragmentReadyListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private LatLngBounds mInitialMapBounds;

    ArrayList<GetMapData> listMap = new ArrayList<>();

    public GetMapFragment() {
    }

    public static GetMapFragment newInstance() {
        GetMapFragment fragment = new GetMapFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(EXTRA_KEY, key);
//        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // create SupportMapFragment, and listen for onMapfragmentReady callback
        mMapFragment = CustomSupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.flMapContainer, mMapFragment).commit();

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

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
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mInitialMapBounds, 15));
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

                    Log.e("Loop",lati + ": " + longLat);


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

}

