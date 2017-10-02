package com.twentyfour.chavel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twentyfour.chavel.R;
import com.twentyfour.chavel.adapter.DraftRouteAdapter;
import com.twentyfour.chavel.model.DraftModel;

import java.util.ArrayList;

public class DraftFragment extends Fragment {

    DraftRouteAdapter dratfAdapter;
    RecyclerView ryc;
    ArrayList<DraftModel> list = new ArrayList<>();

    public static DraftFragment newInstance() {
        return new DraftFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_draft, container, false);
        ryc = (RecyclerView) rootView.findViewById(R.id.ryc);
        ryc.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        ryc.setLayoutManager(llm);

        for(int i=0; i < 3;i++){
            DraftModel item = new DraftModel();
            item.setTitle("test24"+i);
            list.add(item);
            dratfAdapter = new DraftRouteAdapter(getActivity(),list);
            ryc.setAdapter(dratfAdapter);
        }

        return rootView;
    }

}