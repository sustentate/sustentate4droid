package ar.com.sustentate.com.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ar.com.sustentate.com.Controller.TipController;
import ar.com.sustentate.com.R;
import ar.com.sustentate.com.adapter.RecyclerAdapter;
import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.models.Tip;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTips extends Fragment implements RecyclerAdapter.Comunicador {

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

        adapter = new RecyclerAdapter(tipList, getContext(),this);

        RecyclerView recyclerViewTips = (RecyclerView) view.findViewById(R.id.recyclerView_tips);
        recyclerViewTips.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerViewTips.setAdapter(adapter);

        TipController tipController = new TipController();
        tipController.obtenerTips(getContext() , new ResultListener<List<Tip>>() {
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

    @Override
    public void enviarInfo(Tip tip) {
        Comunicador comunicador = (Comunicador) getContext();
        //Le paso la información
        comunicador.enviarInfo(tip);
    }

    public interface Comunicador{

        public void enviarInfo(Tip tip);

    }


}
