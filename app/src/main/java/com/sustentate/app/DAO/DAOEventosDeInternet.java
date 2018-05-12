package com.sustentate.app.DAO;

import android.os.AsyncTask;

import com.sustentate.app.api.ResultListener;
import com.sustentate.app.api.SustentateAPI;
import com.sustentate.app.models.Evento;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emzas on 12/5/2018.
 */

public class DAOEventosDeInternet {

    private class ObtenerListaEventosDeInternet extends AsyncTask<String, Void, List<Evento>> {

        ResultListener<List<Evento>> listener;

        public ObtenerListaEventosDeInternet(ResultListener<List<Evento>> listener) {
            this.listener = listener;
        }

        @Override
        protected List<Evento> doInBackground(String... strings) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SustentateAPI api = retrofit.create(SustentateAPI.class);
            Call<List<Evento>> call = api.getEvetos(0);
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
