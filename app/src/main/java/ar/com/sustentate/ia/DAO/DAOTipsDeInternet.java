package ar.com.sustentate.ia.DAO;

import android.os.AsyncTask;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import ar.com.sustentate.ia.api.ResultListener;
import ar.com.sustentate.ia.api.SustentateAPI;
import ar.com.sustentate.ia.models.Tip;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emzas on 28/3/2018.
 */

public class DAOTipsDeInternet {


    public void ObtenerTipsDeInternet(ResultListener<List<Tip>> listenerFromController){

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
        }


        @Override
        protected void onPostExecute(List<Tip> tips) {
            try {
                listener.finish(tips);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }
}