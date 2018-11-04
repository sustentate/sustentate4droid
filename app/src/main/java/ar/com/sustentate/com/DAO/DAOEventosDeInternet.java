package ar.com.sustentate.com.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import ar.com.sustentate.com.adapter.DateDeserializer;
import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.api.SustentateAPI;
import ar.com.sustentate.com.models.Evento;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emzas on 12/5/2018.
 */

public class DAOEventosDeInternet {

    public void obtenerEventosDeInternet(ResultListener<List<Evento>> listenerFromController){
        ObtenerListaEventosDeInternet tarea = new ObtenerListaEventosDeInternet(listenerFromController);
        tarea.execute();
    }


    private class ObtenerListaEventosDeInternet extends AsyncTask<String, Void, List<Evento>> {

        ResultListener<List<Evento>> listener;

        public ObtenerListaEventosDeInternet(ResultListener<List<Evento>> listener) {
            this.listener = listener;
        }

        @Override
        protected List<Evento> doInBackground(String... strings) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://sustentatemiddleware-generous-bonobo.mybluemix.net/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


            SustentateAPI api = retrofit.create(SustentateAPI.class);

            Call<List<Evento>> call = api.getEventos(0);
            try {
                Response<List<Evento>> response = call.execute();
                List<Evento> eventos = response.body();
                return eventos;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Evento> eventos) {
            listener.finish(eventos);
        }
    }
}
