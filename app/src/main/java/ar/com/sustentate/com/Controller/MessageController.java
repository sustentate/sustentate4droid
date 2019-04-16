package ar.com.sustentate.com.Controller;

import android.content.Context;

import java.text.ParseException;

import ar.com.sustentate.com.DAO.DAOMessage;
import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.models.AssistanceRequest;
import ar.com.sustentate.com.models.AssistanceResponse;

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
