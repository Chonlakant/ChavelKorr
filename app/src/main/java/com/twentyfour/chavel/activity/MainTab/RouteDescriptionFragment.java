package com.twentyfour.chavel.activity.MainTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.twentyfour.chavel.BusProvider.BusProvider;
import com.twentyfour.chavel.Event.Events;
import com.twentyfour.chavel.Event.Events_Desc;
import com.twentyfour.chavel.R;


public class RouteDescriptionFragment extends Fragment {


    Toolbar toolbar;

    EditText dt_desc;

    public static CommentActivity newInstance() {
        return new CommentActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getBus().register(this);
        setRetainInstance(true);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route_description, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        dt_desc = (EditText) rootView.findViewById(R.id.dt_desc);

        toolbar.setTitle("Route Description");
       // setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorTitle));
        toolbar.setBackgroundColor(getResources().getColor(R.color.whitePrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Events_Desc.Events_DescFragmentMessage fragmentActivityMessageEvent = new Events_Desc.Events_DescFragmentMessage(dt_desc.getText().toString());
                BusProvider.getBus().post(fragmentActivityMessageEvent);
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getBus().unregister(this);
    }

}
