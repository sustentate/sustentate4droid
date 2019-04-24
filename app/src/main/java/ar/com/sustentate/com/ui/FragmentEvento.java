package ar.com.sustentate.com.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.sustentate.com.Controller.EventoController;
import ar.com.sustentate.com.R;
import ar.com.sustentate.com.adapter.EventosAdapter;
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
        Long arancelada = Long.valueOf(1);
        Long gratis = Long.valueOf(0);

        EventoController eventoController = new EventoController();

        Evento evento = new Evento("Curso Jardineria de Otoño", "Objetivo del curso: Capacitar al participante para realizar tareas de ejecución y mantenimiento de un jardín. Uso de herramientas, propagación, sanidad, reconocimiento y selección de especies vegetales de acuerdo al uso en el jardín serán tratados.","De Los Reseros y N. Repetto (ex De Las Cabañas), Hurlingham Buenos Aires."
                ,new Date(119, 3,1,8,30),"https://gdurl.com/uTXj",null, arancelada);
        Evento evento1 = new Evento("Curso 'Cambio Climático y Ciudades'", "La Maestría en Derecho y Economía del Cambio Climático de FLACSO-Argentinainvita a participar del Curso “Cambio Climático y Ciudades” a cargo de la profesora Alejandra Ramírez Cuesta.  La materia se compone de 8 sesiones de 3 hs. cada una, que se dictarán a partir del martes 7 de mayo y hasta el jueves 30 de mayo.","Tucumán 1966, CABA."
                ,new Date(119, 4,7,17,0),"https://gdurl.com/lL50",null, arancelada);
        Evento evento2 = new Evento("Congreso de Derecho Ambiental 2019", "El nuevo libro del Dr. Ricardo Lorenzetti y el Dr. Pablo Lorenzetti, \"Derecho Ambiental\", va a ser presentado el 09 de mayo en el VII Congreso Argentino de Derecho Ambiental. Conocé todas las tendencias más actuales de la especialidad en este evento.","Aula Magna, Facultad de Derecho, CABA."
                ,new Date(119, 4,9,8,0),"https://gdurl.com/T6ST",null, arancelada);
        Evento evento3 = new Evento("Feria de consumo responsable", "La Feria de Consumo Responsable es el primer lugar establecido de manera permanente en la Ciudad de Buenos Aires donde diseñadores y emprendedores sustentables ofrecen sus productos de varios rubros, incluyendo indumentaria, juguetes, objetos artesanales y artísticos, accesorios, instrumentos, vajillas, plantas, productos orgánicos y naturales, mobiliaria objetos de cuidado personal entre otros.","Diagonal Sur y Bolívar, CABA"
                ,new Date(119, 4,12,10,30),"https://gdurl.com/cHwW",null, gratis);
        Evento evento4 = new Evento("4º Concurso Nacional PROESUS", "Desde 2016, el Programa Nacional de Emprendedores para el Desarrollo Sustentable (PROESUS) de la Secretaría de Ambiente y Desarrollo Sustentable lleva a cabo el Concurso Nacional PROESUS, una iniciativa pensada para identificar y potenciar los emprendimientos sustentables que provean soluciones innovadoras a los desafíos ambientales del país.","Web de PROESUS"
                ,new Date(119, 4,12,8,30),"https://gdurl.com/ihbD",null, gratis);
        Evento evento5 = new Evento("Huerta urbana: cultivar en suelo e hidroponia.", "Del autosustento al emprendimiento rentable. Ya podés inscribirte. Trayendo fotocopia un documento.","Av Angel Gallardo 246 CABA."
                ,new Date(119, 4,13,17,0),"https://gdurl.com/H3cV",null, gratis);
        Evento evento6 = new Evento("Movilización mundial por el cambio climático", "\"¿Por qué marchamos el 24 de Mayo? Porque queremos que se declare una emergencia climática mundial. Queremos Justicia. Salud. Equidad. Seguridad Alimentaria. Futuro. Queremos acciones NO promesas. Por la Justicia Climática ¿Te sumás?\"","Plaza Congreso, CABA."
                ,new Date(119, 4,24,16,0),"https://gdurl.com/hg71",null, gratis);
        Evento evento7= new Evento("Talleres gratuitos sobre cambio climático", "Los invitamos a trabajar en grupo e idear iniciativas para la mitigación del Cambio Climático, de cualquier tipo y escala. Para ello, expertos en distintos campos del problema climático darán charlas TED para proporcionar ideas y herramientas a los participantes. Los participantes podrían continuar trabajando sus propuestas fuera del taller, y participar de la competencia internacional, MIT Climate CoLab.","Av Eduardo Madero 399, Buenos Aires."
                ,new Date(119, 4,30,8,30),"https://gdurl.com/lg_a",null, gratis);

        eventos.add(evento1);
        eventos.add(evento2);
        eventos.add(evento3);
        eventos.add(evento4);
        eventos.add(evento5);
        eventos.add(evento6);
        eventos.add(evento7);
        eventos.add(evento);

        RecyclerView recyclerViewEventos = (RecyclerView) view.findViewById(R.id.recyclerView_eventos);
        recyclerViewEventos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewEventos.setAdapter(adapter);
        System.out.println(eventos.size());
        adapter.cargarNuevaLista(eventos);

        /*try {
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
        }*/


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
