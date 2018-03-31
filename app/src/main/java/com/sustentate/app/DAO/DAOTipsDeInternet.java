package com.sustentate.app.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.sustentate.app.api.ResultListener;
import com.sustentate.app.api.SustentateAPI;
import com.sustentate.app.models.ContenedoraTip;
import com.sustentate.app.models.Tip;
import com.sustentate.app.utils.HTTPConnectionManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            List<Tip> prueba;

            //TODO: Esto de aca hay que factorizarlo una vez sola para todos, yo ya lo uso en reconomimiento y la url la deberiamos parametrizar
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sustentatemiddleware-generous-bonobo.mybluemix.net/")
                //.baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            SustentateAPI api = retrofit.create(SustentateAPI.class);
            Call<List<Tip>> call = api.getTips(0);
            try {
                Response<List<Tip>> response = call.execute();
                List<Tip> tips = response.body();
                return tips;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

/*
            ContenedoraTip contenedora = null;
            try {
                HTTPConnectionManager connectionManager = new HTTPConnectionManager();
                String leerElJsonDeInternet = connectionManager.getRequestString("https://sustentatemiddleware-generous-bonobo.mybluemix.net/ecotip?lastId=0");

                Gson gson = new Gson();
                contenedora = gson.fromJson(leerElJsonDeInternet, ContenedoraTip.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return contenedora != null ? contenedora.getListaTip() : null;*/
        }


        @Override
        protected void onPostExecute(List<Tip> productos) {
            listener.finish(productos);
        }


    }
}
