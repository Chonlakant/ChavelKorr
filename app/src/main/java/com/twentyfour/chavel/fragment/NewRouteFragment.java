package com.twentyfour.chavel.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kbeanie.multipicker.api.ImagePicker;
import com.squareup.otto.Subscribe;
import com.twentyfour.chavel.BusProvider.BusProvider;
import com.twentyfour.chavel.Event.Events;
import com.twentyfour.chavel.Event.Events_Desc;
import com.twentyfour.chavel.Event.Events_Route_Activity;
import com.twentyfour.chavel.Event.Events_Route_Details;
import com.twentyfour.chavel.Event.Events_Route_Loction;
import com.twentyfour.chavel.Event.Events_Route_Name;
import com.twentyfour.chavel.Event.Events_Route_Period;
import com.twentyfour.chavel.Event.Events_Route_Suggestion;
import com.twentyfour.chavel.Event.Events_Route_Travel;
import com.twentyfour.chavel.R;
import com.twentyfour.chavel.activity.MainTab.AddPinFragment;
import com.twentyfour.chavel.activity.MainTab.BudgetFragment;
import com.twentyfour.chavel.activity.MainTab.CrossProvinceActivity;
import com.twentyfour.chavel.activity.MainTab.LocationAddActivity;
import com.twentyfour.chavel.activity.MainTab.RouteDescriptionFragment;
import com.twentyfour.chavel.activity.MainTab.SelectActivityFragment;
import com.twentyfour.chavel.activity.MainTab.PeriodTimeFragment;
import com.twentyfour.chavel.activity.MainTab.RouteHistoryActivity;
import com.twentyfour.chavel.activity.MainTab.SuggestionFragment;
import com.twentyfour.chavel.activity.MainTab.SelectTravelMethodFragment;
import com.twentyfour.chavel.model.DetailsRoute;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;
import siclo.com.ezphotopicker.models.PhotoIntentException;

import static android.app.Activity.RESULT_OK;


public class NewRouteFragment extends Fragment {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int PICK_IMAGE = 1887;

    private ExpandableLayout expandableLayout0;

    ImageView imf_next;
    ImageView btn_expand_toggle;
    EditText dt_period;
    EditText ed_suggesstion;
    EditText et_travel_method;
    EditText dt_location;
    EditText dt_activity;
    EditText dt_route_desc;
    EditText dt_name;
    TextView dt_details;
    LinearLayout ls_budget, ls_cover;
    TextView txt_rout_name;
    TextView txt_loction;
    ImageView camera_cover;
    private String pickerPath;
    private ImageView imageView;
    private ImageView img_cover;
    private ImageView take_photo;
    private ImageView ls_next_2;
    private LinearLayout ls_loction;
    private TextView txt_counrty;
    private TextView txt_city;

    private ImagePicker imagePicker;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Bitmap bitmap;

    boolean status = false;

    DetailsRoute detailsRoute;


    public static NewRouteFragment newInstance() {
        NewRouteFragment fragment = new NewRouteFragment();

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    String desc;
    String activity;
    String locationText;
    String travel;
    String period;
    String suggestion;
    String routeName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = inflater.inflate(R.layout.fragment_new_route, null);
        ls_cover = (LinearLayout) view.findViewById(R.id.ls_cover);
        ls_next_2 = (ImageView) view.findViewById(R.id.ls_next_2);
        ls_loction = (LinearLayout) view.findViewById(R.id.ls_loction);
        txt_counrty = (TextView) view.findViewById(R.id.txt_counrty);
        txt_city = (TextView) view.findViewById(R.id.txt_city);

        txt_rout_name = (TextView) view.findViewById(R.id.txt_rout_name);
        txt_loction = (TextView) view.findViewById(R.id.txt_loction);
        expandableLayout0 = (ExpandableLayout) view.findViewById(R.id.expandable_layout_0);
        camera_cover = (ImageView) view.findViewById(R.id.camera_cover);

        img_cover = (ImageView) view.findViewById(R.id.img_cover);
        take_photo = (ImageView) view.findViewById(R.id.take_photo);

        imf_next = (ImageView) view.findViewById(R.id.imf_next);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        btn_expand_toggle = (ImageView) view.findViewById(R.id.btn_expand_toggle);
        dt_period = (EditText) view.findViewById(R.id.dt_period);
        ed_suggesstion = (EditText) view.findViewById(R.id.ed_suggesstion);
        ls_budget = (LinearLayout) view.findViewById(R.id.ls_budget);
        et_travel_method = (EditText) view.findViewById(R.id.et_travel_method);
        dt_activity = (EditText) view.findViewById(R.id.dt_activity);
        dt_location = (EditText) view.findViewById(R.id.dt_location);
        dt_route_desc = (EditText) view.findViewById(R.id.dt_route_descrition);
        dt_name = (EditText) view.findViewById(R.id.dt_name);
        dt_details = (TextView) view.findViewById(R.id.dt_details);

        ArrayList<View> views = new ArrayList<>();
        ls_loction.setVisibility(View.GONE);

        views.add(dt_details);
        views.add(dt_name);
        views.add(dt_route_desc);
        views.add(dt_location);
        views.add(dt_activity);
        views.add(et_travel_method);
        views.add(dt_period);
        views.add(ed_suggesstion);

        for (int i = 0; i < views.size(); i++) {
            views.get(i).setFocusable(false);
            views.get(i).setClickable(true);
        }

        txt_loction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrossProvinceActivity crossProvinceActivity = new CrossProvinceActivity();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.content, crossProvinceActivity);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        dt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RouteNameFragment routeNameFragment = new RouteNameFragment();
//                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.content, routeNameFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });


        imf_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(dt_name.getText().toString())) {
                    Toast.makeText(getActivity(), "Route name", Toast.LENGTH_SHORT).show();
                } else {

                    String routeName = dt_name.getText().toString();

                    detailsRoute = new DetailsRoute();
                    detailsRoute.setRouteName(routeName);
                    detailsRoute.setDesc(desc);
                    detailsRoute.setActivity(activity);
                    detailsRoute.setLocation(locationText);
                    detailsRoute.setSuggestion(suggestion);
                    detailsRoute.setTravel(travel);

//                    detailsRoute.setRouteName(routeName);
//                    detailsRoute.setDesc(desc);
//                    detailsRoute.setActivity(activity);
//                    detailsRoute.setLocation(locationText);
//                    detailsRoute.setSuggestion(suggestion);
//                    detailsRoute.setTravel(travel);

//                    Events_Route_Name.Events_RoutNameFragmentMessage fragmentActivityMessageEvent =
//                            new Events_Route_Name.Events_RoutNameFragmentMessage("ggggggggg");
//                    BusProvider.getInstance().post(fragmentActivityMessageEvent);

                    AddPinFragment addPinActivity = new AddPinFragment();
                    android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, addPinActivity);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }

            }
        });

        dt_period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PeriodTimeFragment periodTimeFragment = new PeriodTimeFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, periodTimeFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        ed_suggesstion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SuggestionFragment suggestionFragment = new SuggestionFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, suggestionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ls_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BudgetFragment budgetFragment = new BudgetFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, budgetFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        et_travel_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectTravelMethodFragment selectTravelMethodFragment = new SelectTravelMethodFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, selectTravelMethodFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        dt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationAddActivity locationFragment = new LocationAddActivity();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, locationFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        dt_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectActivityFragment routeDescriptionFragment = new SelectActivityFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.content, routeDescriptionFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        dt_route_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RouteDescriptionFragment routeDescriptionFragment = new RouteDescriptionFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, routeDescriptionFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        dt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RouteHistoryActivity.class);
                startActivity(i);
            }
        });

        btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (expandableLayout0.isExpanded()) {
                    ls_cover.setVisibility(View.VISIBLE);
                    expandableLayout0.collapse();
                    btn_expand_toggle.setImageResource(R.drawable.ic_down);
                } else {
                    ls_cover.setVisibility(View.GONE);
                    expandableLayout0.expand();
                    btn_expand_toggle.setImageResource(R.drawable.ic_up);
                }

            }
        });

        camera_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showAlertDialogOne();
                chooseCamera();
            }

        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(dt_name.getText().toString())) {
                    Toast.makeText(getActivity(), "Route name", Toast.LENGTH_SHORT).show();
                } else {
                    chooseCamera();
                    status = true;
                }


            }
        });

        ls_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(dt_name.getText().toString())) {
                    Toast.makeText(getActivity(), "Route name", Toast.LENGTH_SHORT).show();
                } else {

                    Events_Route_Details.Events_RoutDetails fragmentActivityMessageEvent = new Events_Route_Details.Events_RoutDetails(detailsRoute);
                    BusProvider.getInstance().post(fragmentActivityMessageEvent);

                    AddPinFragment addPinActivity = new AddPinFragment();
                    android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, addPinActivity);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }

            }
        });

        return view;
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

//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//// perform your action here
//
//        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE &&
//                resultCode == RESULT_OK) {
//            try {
//                Bitmap pickedPhoto = new EZPhotoPickStorage(getActivity()).loadLatestStoredPhotoBitmap();
//                camera_cover.setImageBitmap(pickedPhoto);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                pickedPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//                Log.e("pickedPhoto", pickedPhoto.toString());
//                Toast.makeText(getActivity(),"ggg",Toast.LENGTH_SHORT).show();
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
//        }
//        if (requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
//                resultCode == RESULT_OK) {
//            try {
//                Bitmap pickedCamera = new EZPhotoPickStorage(getActivity()).loadLatestStoredPhotoBitmap();
//                camera_cover.setImageBitmap(pickedCamera);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                pickedCamera.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                Log.e("pickedPhoto", pickedCamera.toString());
//                Toast.makeText(getActivity(),"ggg",Toast.LENGTH_SHORT).show();
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedCamera = new EZPhotoPickStorage(getActivity()).loadLatestStoredPhotoBitmap();
                imageView.setImageBitmap(pickedCamera);
                img_cover.setImageBitmap(pickedCamera);
                bitmap = pickedCamera;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                pickedCamera.compress(Bitmap.CompressFormat.PNG, 100, stream);

                if (status == true) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AddPinFragment addPinActivity = new AddPinFragment();
                            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.content, addPinActivity);
                            transaction.addToBackStack(null);
                            transaction.commit();



                        }
                    }, SPLASH_DISPLAY_LENGTH);
                }

            } catch (IOException e) {
                e.printStackTrace();

            }
        }


        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedPhoto = new EZPhotoPickStorage(getActivity()).loadLatestStoredPhotoBitmap();
                imageView.setImageBitmap(pickedPhoto);
                img_cover.setImageBitmap(pickedPhoto);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                pickedPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bitmap = pickedPhoto;
                if (status == true) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Events.ActivityFragmentMessage fragmentActivityMessageEvent = new Events.ActivityFragmentMessage("123456");
                            BusProvider.getInstance().post(fragmentActivityMessageEvent);


                            AddPinFragment addPinActivity = new AddPinFragment();
                            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.content, addPinActivity);
                            transaction.addToBackStack(null);
                            transaction.commit();


                        }
                    }, SPLASH_DISPLAY_LENGTH);
                }


            } catch (IOException e) {
                e.printStackTrace();

            }
        }
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
    public void onSaveInstanceState(Bundle outState) {
        // You have to save path in case your activity is killed.
        // In such a scenario, you will need to re-initialize the CameraImagePicker
//        outState.putString("picker_path", pickerPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_add_pin, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_new_friend:
//                Snackbar.make(view, item.getTitle() + " Clicked", Snackbar.LENGTH_SHORT).show();
//                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }


    @Subscribe
    public void getRouteDesc(Events_Desc.Events_DescFragmentMessage texts) {
        if (texts.getMessage() != "") {
            desc = texts.getMessage();
            dt_route_desc.setText(texts.getMessage());
        }

    }

    @Subscribe
    public void getRouteName(Events_Route_Name.Events_RoutNameFragmentMessage texts) {
        if (texts.getMessage() != "") {
            dt_name.setText(texts.getMessage());
            routeName = texts.getMessage();
        }

    }

    @Subscribe
    public void getRouteLocation(Events_Route_Loction.Events_RoutLocationFragmentMessage location) {
        if (location.getMessage().getCity() != "") {
            locationText = "Thailand, Japan";
            ls_loction.setVisibility(View.VISIBLE);
            txt_city.setText("Thailand, Japan");
            txt_counrty.setText("Bangkok(TH), Chiang Mai(TH),Hokkaido(JP),Tokyo(JP)");
        }

    }

    @Subscribe
    public void getRouteTravel(Events_Route_Travel.Events_TravelFragmentMessage texts) {
        if (texts.getMessage() != "") {
            travel = texts.getMessage();
            et_travel_method.setText(texts.getMessage());
        }

    }

    @Subscribe
    public void getRouteActivity(Events_Route_Activity.Events_ActivityFragmentMessage texts) {
        if (texts.getMessage() != "") {
            activity = texts.getMessage();
            dt_activity.setText(texts.getMessage());
        }

    }

    @Subscribe
    public void getRouteSuggestion(Events_Route_Suggestion.Events_RoutSuggestionFragmentMessage texts) {
        if (texts.getMessage() != "") {
            suggestion = texts.getMessage();
            ed_suggesstion.setText(texts.getMessage());
        }

    }

    @Subscribe
    public void getRoutePeriod(Events_Route_Period.Events_RoutPeriodFragmentMessage texts) {
        if (texts.getMessage() != "") {
            period = texts.getMessage();
            dt_period.setText(texts.getMessage());
        }

    }


}
