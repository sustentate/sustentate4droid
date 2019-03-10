package ar.com.sustentate.com.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ar.com.sustentate.com.Controller.EventoController;
import ar.com.sustentate.com.R;
import ar.com.sustentate.com.adapter.EventosAdapter;
import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.models.Evento;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEvento extends Fragment implements EventosAdapter.Comunicador {


    private List<Evento> eventos;
    private EventosAdapter adapter;

    public FragmentEvento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_evento, container, false);

        eventos = new ArrayList<>();

        adapter = new EventosAdapter(eventos, getContext(),this);

        RecyclerView recyclerViewEventos = (RecyclerView) view.findViewById(R.id.recyclerView_eventos);
        recyclerViewEventos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewEventos.setAdapter(adapter);

        EventoController eventoController = new EventoController();
        try {
            eventoController.obtenerEventos(getContext(), new ResultListener<List<Evento>>() {
                @Override
                public void loading() {

                }

                @Override
                public void finish(List<Evento> result) {
                    eventos = result;
                    adapter.cargarNuevaLista(eventos);
                }

                @Override
                public void error(Throwable error) {

                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void enviarInfo(Evento evento) {
       Comunicador comunicador = (Comunicador) getContext();

        comunicador.enviarInfo(evento);
    }
    public interface Comunicador {

        public void enviarInfo(Evento evento);

    }


}
