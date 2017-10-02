package com.twentyfour.chavel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.twentyfour.chavel.bus.BusProvider;
import com.twentyfour.chavel.bus.event.Events_State_Menu;
import com.twentyfour.chavel.R;
import com.twentyfour.chavel.activity.MainTab.AddPinFragment;
import com.twentyfour.chavel.activity.MainTab.EditPinFragment;
import com.twentyfour.chavel.adapter.PinsAdapter;
import com.twentyfour.chavel.model.ModelPins;

import java.util.ArrayList;


public class PinsFragment extends Fragment {

    PinsAdapter pinsAdapter;
    ArrayList<ModelPins> list = new ArrayList<>();
    LinearLayout view_all;
    LinearLayout add_pins;

    RecyclerView mRecyclerView;

    public PinsFragment() {
        // Required empty public constructor
    }

    public static PinsFragment newInstance() {
        PinsFragment fragment = new PinsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(EXTRA_KEY, key);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setRetainInstance(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pins, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ryc);
        view_all = (LinearLayout) view.findViewById(R.id.view_all);
        add_pins = (LinearLayout) view.findViewById(R.id.add_pins);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 5; i++) {
            ModelPins modelPins = new ModelPins();
            modelPins.setPins("PINS" + i);
            modelPins.setTitlePins("แวะกินข้าว อร่อยๆ" + i);
            list.add(modelPins);
        }

        pinsAdapter = new PinsAdapter(getActivity(), list);
        mRecyclerView.setAdapter(pinsAdapter);

        pinsAdapter.setOnItemClickListener(new PinsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View viewj, int position) {

                EditPinFragment pinDetailsFragment = new EditPinFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_2, pinDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();

//                Intent i = new Intent(getActivity(), EditPinFragment.class);
//                startActivity(i);
            }
        });

        pinsAdapter.setOnItemClickImageListener(new PinsAdapter.OnItemClickImageListener() {
            @Override
            public void onItemClickImage(View viewj, int position) {
                Toast.makeText(getActivity(),"DELETE",Toast.LENGTH_SHORT).show();
            }
        });



        view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Events_State_Menu.Events_StateMenuFragmentMessage fragmentActivityMessageEvent = new Events_State_Menu.Events_StateMenuFragmentMessage("orver_map");
                BusProvider.getInstance().post(fragmentActivityMessageEvent);

                GetMapFragment addPinActivity = new GetMapFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container8, addPinActivity);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        add_pins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddPinFragment addPinActivity = new AddPinFragment();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_2, addPinActivity);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;
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


}
