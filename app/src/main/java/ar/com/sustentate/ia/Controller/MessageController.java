package ar.com.sustentate.ia.Controller;

import android.content.Context;

import java.text.ParseException;

import ar.com.sustentate.ia.DAO.DAOMessage;
import ar.com.sustentate.ia.api.ResultListener;
import ar.com.sustentate.ia.models.AssistanceRequest;
import ar.com.sustentate.ia.models.AssistanceResponse;

/**
 * Created by emzas on 31/10/2018.
 */

public class MessageController {

    public void obtenerResponse(Context context, final AssistanceRequest request, final ResultListener<AssistanceResponse> listenerFromView) throws ParseException {

        DAOMessage daoMessage = new DAOMessage();
        daoMessage.ObtenerRespuestaDeWatson(request, new ResultListener<AssistanceResponse>() {
            @Override
            public void loading() {

            }

            @Override
            public void finish(AssistanceResponse result) throws ParseException {
                if(request != null){
                    listenerFromView.finish(result);
                }
            }

            @Override
            public void error(Throwable error) {

            }
        });
    }
}
