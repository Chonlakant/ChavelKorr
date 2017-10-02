package com.twentyfour.chavel.activity.MainTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.twentyfour.chavel.bus.BusProvider;
import com.twentyfour.chavel.bus.event.Events_Route_Travel;
import com.twentyfour.chavel.R;

import butterknife.Bind;

public class SelectTravelMethodFragment extends Fragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    EditText dt_travel_method;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setRetainInstance(true);


    }

    public static SelectTravelMethodFragment newInstance() {
        return new SelectTravelMethodFragment();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_travel_method, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        dt_travel_method = (EditText) rootView.findViewById(R.id.dt_travel_method);

        toolbar.setTitle("Travel Method");
        // setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorTitle));
        toolbar.setBackgroundColor(getResources().getColor(R.color.whitePrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events_Route_Travel.Events_TravelFragmentMessage fragmentActivityMessageEvent = new Events_Route_Travel.Events_TravelFragmentMessage(dt_travel_method.getText().toString());
                BusProvider.getInstance().post(fragmentActivityMessageEvent);
                getActivity().onBackPressed();

            }
        });


        return rootView;
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

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_phone_email, menu);
//
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        return super.onOptionsItemSelected(item);
//    }
}
