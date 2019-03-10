package ar.com.sustentate.com.Controller;

import android.content.Context;

import ar.com.sustentate.com.DAO.DAOMessage;
import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.models.AssistanceRequest;
import ar.com.sustentate.com.models.AssistanceResponse;
import ar.com.sustentate.com.utils.HTTPConnectionManager;

/**
 * Created by emzas on 31/10/2018.
 */

public class MessageController {

    public void obtenerResponse(Context context, final AssistanceRequest request, final ResultListener<AssistanceResponse> listenerFromView){

        if(hayInternet(context)){

            DAOMessage daoMessage = new DAOMessage();
            daoMessage.ObtenerRespuestaDeWatson(request, new ResultListener<AssistanceResponse>() {
                @Override
                public void loading() {

                }

                @Override
                public void finish(AssistanceResponse result) {
                    if(request != null){
                        listenerFromView.finish(result);
                    }
                }

                @Override
                public void error(Throwable error) {

                }
            });
        }else {
            AssistanceResponse assistanceResponse = new AssistanceResponse("No hay conexion","www.google.com", 3, "0");
            listenerFromView.finish(assistanceResponse);
        }

    }
    private Boolean hayInternet(Context context){
        return HTTPConnectionManager.isNetworkingOnline(context);
    }

}
