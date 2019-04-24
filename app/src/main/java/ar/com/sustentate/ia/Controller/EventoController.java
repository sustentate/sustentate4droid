package ar.com.sustentate.ia.Controller;

import android.content.Context;

import java.text.ParseException;
import java.util.List;

import ar.com.sustentate.ia.DAO.DAOEventosDeInternet;
import ar.com.sustentate.ia.DAO.DAOTablaEventos;
import ar.com.sustentate.ia.api.ResultListener;
import ar.com.sustentate.ia.models.Evento;
import ar.com.sustentate.ia.utils.HTTPConnectionManager;

/**
 * Created by emzas on 12/5/2018.
 */

public class EventoController {

    public void obtenerEventos(Context context, final ResultListener<List<Evento>> listenerFromView) throws ParseException {

        final DAOTablaEventos daoTablaEventos = new DAOTablaEventos(context);

        if(hayInternet(context)){

            DAOEventosDeInternet daoEventosDeInternet = new DAOEventosDeInternet();
            daoEventosDeInternet.obtenerEventosDeInternet(new ResultListener<List<Evento>>() {
                @Override
                public void loading() {

                }

                @Override
                public void finish(List<Evento> result) throws ParseException {
                    if(result != null){
                        daoTablaEventos.insertarLosEventos(result);
                        listenerFromView.finish(result);
                    }
                }

                @Override
                public void error(Throwable error) {

                }
            });

        }else{
            List<Evento> eventos = daoTablaEventos.consultaEventos();
            listenerFromView.finish(eventos);
        }

    }


    private Boolean hayInternet(Context context){
        return HTTPConnectionManager.isNetworkingOnline(context);
    }


}
