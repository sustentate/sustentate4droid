package ar.com.sustentate.com.DAO;

import android.os.AsyncTask;

import java.io.IOException;
import java.text.ParseException;

import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.api.SustentateAPI;
import ar.com.sustentate.com.models.AssistanceRequest;
import ar.com.sustentate.com.models.AssistanceResponse;
import ar.com.sustentate.com.models.Message;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emzas on 31/10/2018.
 */

public class DAOMessage {

    public void ObtenerRespuestaDeWatson(AssistanceRequest request, ResultListener<AssistanceResponse> listenerFromController){

        ObtenerRespuestasDeWatson tarea = new ObtenerRespuestasDeWatson(request, listenerFromController);
        tarea.execute();

    }

    private class ObtenerRespuestasDeWatson extends AsyncTask<String, Void, Message>{

        AssistanceRequest assistanceRequest;
        ResultListener<AssistanceResponse> listener;

        public ObtenerRespuestasDeWatson(AssistanceRequest assistanceRequest, ResultListener<AssistanceResponse> listener) {
            this.assistanceRequest = assistanceRequest;
            this.listener = listener;
        }


        @Override
        protected Message doInBackground(String... strings) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://sustentatemiddleware-generous-bonobo.mybluemix.net/")
                    //.baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SustentateAPI api = retrofit.create(SustentateAPI.class);

            Call<AssistanceResponse> call = api.chat(assistanceRequest);
            try {
                Response<AssistanceResponse> responseC = call.execute();
                AssistanceResponse response = responseC.body();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Message message) {
            AssistanceResponse response = (AssistanceResponse) message;
            try {
                listener.finish(response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
