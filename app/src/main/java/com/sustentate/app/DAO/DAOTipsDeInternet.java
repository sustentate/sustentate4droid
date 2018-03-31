package com.sustentate.app.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.sustentate.app.api.ResultListener;
import com.sustentate.app.models.ContenedoraTip;
import com.sustentate.app.models.Tip;
import com.sustentate.app.utils.HTTPConnectionManager;

import java.util.List;

/**
 * Created by emzas on 28/3/2018.
 */

public class DAOTipsDeInternet {


    public void obtenerProductosDeInternet(ResultListener<List<Tip>> listenerFromController){

        ObtenerListaTipsdeInternet tarea = new ObtenerListaTipsdeInternet(listenerFromController);
        tarea.execute();
    }

    private class ObtenerListaTipsdeInternet extends AsyncTask<String, Void, List<Tip>> {

        ResultListener<List<Tip>> listener;

        public ObtenerListaTipsdeInternet(ResultListener<List<Tip>> listener) {
            this.listener = listener;
        }

        @Override
        protected List<Tip> doInBackground(String... strings) {
            ContenedoraTip contenedora = null;
            try {
                HTTPConnectionManager connectionManager = new HTTPConnectionManager();
                String leerElJsonDeInternet = connectionManager.getRequestString("https://sustentatemiddleware-generous-bonobo.mybluemix.net/ecotip?lastId=0");

                Gson gson = new Gson();
                contenedora = gson.fromJson(leerElJsonDeInternet, ContenedoraTip.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return contenedora != null ? contenedora.getListaTip() : null;
        }


        @Override
        protected void onPostExecute(List<Tip> productos) {
            listener.finish(productos);
        }


    }
}
