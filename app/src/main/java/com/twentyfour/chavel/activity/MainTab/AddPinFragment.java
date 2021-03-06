package com.twentyfour.chavel.activity.MainTab;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.twentyfour.chavel.bus.BusProvider;
import com.twentyfour.chavel.bus.event.Events_Route_Name;
import com.twentyfour.chavel.R;
import com.twentyfour.chavel.model.GetMapData;
import com.twentyfour.chavel.util.CustomSupportMapFragment;
import com.twentyfour.chavel.util.MapMarker;
import com.twentyfour.chavel.util.PermissionUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;

import gun0912.tedbottompicker.TedBottomPicker;
import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;
import siclo.com.ezphotopicker.models.PhotoIntentException;

import static android.app.Activity.RESULT_OK;

public class AddPinFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        CustomSupportMapFragment.OnMapFragmentReadyListener, LocationListener {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static boolean mPermissionDenied = false;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public EditText txtPinnameEdit;
    public  ImageView btn_expand_toggle;
    public  ExpandableLayout expandableLayout0;

    public  LinearLayout ls_map;
    public  ImageView ls_next_2;
    public  ImageView take_photo;

    private  Location location;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private LatLngBounds mInitialMapBounds;

    private ArrayList<GetMapData> listMap = new ArrayList<>();

    public static ViewRouteFragment newInstance() {
        ViewRouteFragment fragment = new ViewRouteFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_pin, container, false);
        setHasOptionsMenu(true);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Add Pin");
        //  setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorTitle));
        toolbar.setBackgroundColor(getResources().getColor(R.color.whitePrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        txtPinnameEdit = (EditText) rootView.findViewById(R.id.txtPinnameEdit);
        expandableLayout0 = (ExpandableLayout) rootView.findViewById(R.id.expandable_layout_0);
        btn_expand_toggle = (ImageView) rootView.findViewById(R.id.btn_expand_toggle);
        ls_map = (LinearLayout) rootView.findViewById(R.id.ls_map);
        ls_next_2 = (ImageView) rootView.findViewById(R.id.ls_next_2);
        take_photo = (ImageView) rootView.findViewById(R.id.take_photo);

        mMapFragment = CustomSupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.flMapContainer2, mMapFragment).commit();

        btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout0.isExpanded()) {
                    expandableLayout0.collapse();
                    btn_expand_toggle.setImageResource(R.drawable.down_icon);
                } else {
                    expandableLayout0.expand();
                    btn_expand_toggle.setImageResource(R.drawable.up_icon);

                }
            }
        });

        ls_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(txtPinnameEdit.getText().toString())) {
                    Toast.makeText(getActivity(), "Pin Name", Toast.LENGTH_SHORT).show();
                } else {
                    ViewRouteFragment viewRouteFragment =  ViewRouteFragment.newInstance("Add_pin");
                    android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.content, viewRouteFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
        });

        setMultiShowButton();

        mLocationListener.onLocationChanged(location);
        return rootView;
    }

    public RequestManager mGlideRequestManager;
    ImageView iv_image;
    ArrayList<Uri> selectedUriList;
    Uri selectedUri;
    private ViewGroup mSelectedImagesContainer;

    private void setMultiShowButton() {

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getActivity())
                        .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(ArrayList<Uri> uriList) {
                                selectedUriList = uriList;
                                showUriList(uriList);
                            }
                        })
                        //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                        .setPeekHeight(1600)
                        .showTitle(false)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("No Select")
                        .setSelectedUriList(selectedUriList)
                        .create();

                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager());

            }
        });

    }

    private void showUriList(ArrayList<Uri> uriList) {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();

        iv_image.setVisibility(View.GONE);
        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


        for (Uri uri : uriList) {

            View imageHolder = LayoutInflater.from(getActivity()).inflate(R.layout.item_pin_image, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);



            Glide.with(this)
                    .load(uri.toString())

                    .bitmapTransform(new CenterCrop(context))
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));


        }

    }

    private void showAlertDialogOne() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setMessage("เลือกรูปภาพ")
                .setPositiveButton("แกลอรี่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something on Share
                        chooseImage();
                    }
                })
                .setNegativeButton("กล้อง", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something on Cancel
                        chooseCamera();
                    }
                });
        builder.show();
    }


    public void chooseImage() {
        EZPhotoPickConfig config = new EZPhotoPickConfig();
        config.photoSource = PhotoSource.GALLERY;
        config.exportingSize = 900;
        try {
            EZPhotoPick.startPhotoPickActivity(this, config);
        } catch (PhotoIntentException e) {
            e.printStackTrace();
        }
    }

    public void chooseCamera() {
        EZPhotoPickConfig config = new EZPhotoPickConfig();
        config.photoSource = PhotoSource.CAMERA;
        config.exportingSize = 900;
        try {
            EZPhotoPick.startPhotoPickActivity(this, config);
        } catch (PhotoIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedPhoto = new EZPhotoPickStorage(getActivity()).loadLatestStoredPhotoBitmap();
                take_photo.setImageBitmap(pickedPhoto);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                pickedPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);


            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        if (requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedCamera = new EZPhotoPickStorage(getActivity()).loadLatestStoredPhotoBitmap();
                take_photo.setImageBitmap(pickedCamera);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                pickedCamera.compress(Bitmap.CompressFormat.PNG, 100, stream);


            } catch (IOException e) {
                e.printStackTrace();

            }
        }
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
        //mMap.setMinZoomPreference(6.0f);
        //mMap.setMaxZoomPreference(14.0f);
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

                GetMapData location = new GetMapData();
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                listMap.add(location);

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
            LocationManager locationManager =
                    (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 11);
                mMap.animateCamera(cameraUpdate);
            }
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
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_add_pin, menu);
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
