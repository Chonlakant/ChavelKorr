package com.twentyfour.chavel.activity.MainTab;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.twentyfour.chavel.bus.BusProvider;
import com.twentyfour.chavel.bus.event.Events;
import com.twentyfour.chavel.bus.event.Events_Route_Details;
import com.twentyfour.chavel.R;
import com.twentyfour.chavel.adapter.ExpandableListAdapter;
import com.twentyfour.chavel.fragment.GetMapFragment;
import com.twentyfour.chavel.fragment.OverviewFragmentEmty;
import com.twentyfour.chavel.fragment.PinsFragment;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;


public class ViewRouteFragment extends Fragment {

    private ExpandableLayout expandableLayout0;
    public static final String KEY_MESSAGE = "message";

    Toolbar toolbar;
    Button btn_location_map;

    LinearLayout fragment_map;
    LinearLayout fragment_container3;
    LinearLayout ls_save_lin;
    LinearLayout ls_location_map;
    LinearLayout ls_feed;

    EditText dt_period;
    EditText ed_suggesstion;
    EditText et_travel_method;
    EditText dt_location;
    EditText dt_activity;
    EditText dt_route_descrition;
    EditText dt_name;
    TextView dt_details;
    LinearLayout ls_budget, ls_cover;
    TextView txt_rout_name;
    TextView txt_loction;
    private LinearLayout ls_loction;
    private TextView txt_counrty;
    private TextView txt_city;

    Button ls_save;
    ImageView img_click;

    List<ExpandableListAdapter.Item> data = new ArrayList<>();

    LinearLayout ls_1;
    LinearLayout ls_2;
//    View view_1;
//    View view_2;
//    View view_border1;
//    View view_border2;

    String key1;
    String key2;

    public static ViewRouteFragment newInstance(String message) {
        ViewRouteFragment fragment = new ViewRouteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setRetainInstance(true);


        Bundle bundle = getArguments();
        if (bundle != null) {
            key1 = bundle.getString(KEY_MESSAGE);
        }

    }

    Dialog dialogLoading;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_route, container, false);

        dialogLoading = new Dialog(getActivity(), R.style.FullHeightDialog);
        dialogLoading.setContentView(R.layout.dialog_loading_f);

        txt_counrty = (TextView) rootView.findViewById(R.id.txt_counrty);
        txt_city = (TextView) rootView.findViewById(R.id.txt_city);

        txt_rout_name = (TextView) rootView.findViewById(R.id.txt_rout_name);
        txt_loction = (TextView) rootView.findViewById(R.id.txt_loction);
        dt_period = (EditText) rootView.findViewById(R.id.dt_period);
        ed_suggesstion = (EditText) rootView.findViewById(R.id.ed_suggesstion);
        ls_budget = (LinearLayout) rootView.findViewById(R.id.ls_budget);
        et_travel_method = (EditText) rootView.findViewById(R.id.et_travel_method);
        dt_activity = (EditText) rootView.findViewById(R.id.dt_activity);
        dt_location = (EditText) rootView.findViewById(R.id.dt_location);
        ls_loction = (LinearLayout) rootView.findViewById(R.id.ls_loction);
        dt_route_descrition = (EditText) rootView.findViewById(R.id.dt_route_descrition);
        dt_name = (EditText) rootView.findViewById(R.id.dt_name);
        dt_details = (TextView) rootView.findViewById(R.id.dt_details);

        ls_1 = (LinearLayout) rootView.findViewById(R.id.ls_1);
        ls_2 = (LinearLayout) rootView.findViewById(R.id.ls_2);

//        view_1 = (View) rootView.findViewById(R.id.view_1);
//        view_2 = (View) rootView.findViewById(R.id.view_2);
//
//        view_border1 = (View) rootView.findViewById(R.id.view_border1);
//        view_border2 = (View) rootView.findViewById(R.id.view_border2);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


        ls_feed = (LinearLayout) rootView.findViewById(R.id.ls_feed);
        btn_location_map = (Button) rootView.findViewById(R.id.btn_locaion_map);
        ls_save_lin = (LinearLayout) rootView.findViewById(R.id.ls_save_lin);
        ls_location_map = (LinearLayout) rootView.findViewById(R.id.ls_locaion_map);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        fragment_map = (LinearLayout) rootView.findViewById(R.id.fragment_map);
        fragment_container3 = (LinearLayout) rootView.findViewById(R.id.fragment_container3);
        ls_save = (Button) rootView.findViewById(R.id.ls_save);

        toolbar.setTitle("Route");

        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorTitle));

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        GetMapFragment addPinActivity = new GetMapFragment();
                        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container8, addPinActivity);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        return true;
                    }
                });
        // toolbar.inflateMenu(R.menu.menu_over_map);
        ls_loction.setVisibility(View.GONE);

        toolbar.setBackgroundColor(getResources().getColor(R.color.whitePrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        img_click = (ImageView) rootView.findViewById(R.id.img_click);
        expandableLayout0 = (ExpandableLayout) rootView.findViewById(R.id.expandable_layout_0);


        btn_location_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddPinFragment addPinActivity = new AddPinFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_2, addPinActivity);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        ls_location_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddPinFragment addPinActivity = new AddPinFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_2, addPinActivity);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ls_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogLoading.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialogLoading.dismiss();

                    }
                }, 1000);
            }
        });


        img_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout0.isExpanded()) {
                    expandableLayout0.collapse();
                    img_click.setImageResource(R.drawable.down_icon);
                    ls_feed.setVisibility(View.VISIBLE);


                } else {
                    expandableLayout0.expand();
                    img_click.setImageResource(R.drawable.up_icon);
                    ls_feed.setVisibility(View.GONE);

                }
            }
        });


        ls_save_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogLoading.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialogLoading.dismiss();

                    }
                }, 1000);

                Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();

            }
        });

//        view_border1.setVisibility(View.VISIBLE);
//        view_border2.setVisibility(View.GONE);
//        view_1.setVisibility(View.GONE);
//        view_2.setVisibility(View.VISIBLE);
        PinsFragment twoFragment = new PinsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_fragment_container, twoFragment);
        transaction.commit();


        ls_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), OverViewPinsActivity.class);
                startActivity(i);

                OverviewFragmentEmty twoFragment = new OverviewFragmentEmty();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_fragment_container, twoFragment);
                transaction.commit();

//                view_1.setVisibility(View.VISIBLE);
//                view_2.setVisibility(View.GONE);
//                view_border2.setVisibility(View.VISIBLE);
//                view_border1.setVisibility(View.GONE);

            }
        });


        ls_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                view_border1.setVisibility(View.VISIBLE);
//                view_border2.setVisibility(View.GONE);
//                view_1.setVisibility(View.GONE);
//                view_2.setVisibility(View.VISIBLE);
                PinsFragment twoFragment = new PinsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_fragment_container, twoFragment);
                transaction.commit();

            }
        });

        return rootView;
    }


    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage message) {

    }

    @Subscribe
    public void getRouteState(Events_Route_Details.Events_RoutDetails texts) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO Add your menu entries here
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_location:


                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
