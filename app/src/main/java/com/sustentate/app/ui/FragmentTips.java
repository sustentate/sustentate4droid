package com.sustentate.app.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sustentate.app.Controller.TipController;
import com.sustentate.app.R;
import com.sustentate.app.adapter.RecyclerAdapter;
import com.sustentate.app.api.ResultListener;
import com.sustentate.app.models.Tip;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTips extends Fragment {

    private List<Tip> tipList;
    private RecyclerAdapter adapter;

    public FragmentTips() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        tipList = new ArrayList<>();

        adapter = new RecyclerAdapter(tipList);

        RecyclerView recyclerViewTips = (RecyclerView) view.findViewById(R.id.recyclerView_tips);
        recyclerViewTips.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerViewTips.setAdapter(adapter);

        TipController tipController = new TipController();
        tipController.obtenerTips(new ResultListener<List<Tip>>() {
            @Override
            public void loading() {

            }

            @Override
            public void finish(List<Tip> result) {
                tipList = result;
                adapter.cargarNuevaLista(tipList);

            }

            @Override
            public void error(Throwable error) {

            }
        });







        return view;
    }

}
