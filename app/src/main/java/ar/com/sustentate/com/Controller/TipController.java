package ar.com.sustentate.com.Controller;

import android.content.Context;

import java.text.ParseException;
import java.util.List;

import ar.com.sustentate.com.DAO.DAOTablaTips;
import ar.com.sustentate.com.DAO.DAOTipsDeInternet;
import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.models.Tip;
import ar.com.sustentate.com.utils.HTTPConnectionManager;

/**
 * Created by emzas on 28/3/2018.
 */

public class TipController {

    public void obtenerTips( Context context, final ResultListener<List<Tip>> listenerFromView) throws ParseException {

        final DAOTablaTips daoTablaTips = new DAOTablaTips(context);

        if (hayInternet(context)){

            DAOTipsDeInternet daoTipsDeInternet = new DAOTipsDeInternet();
            daoTipsDeInternet.ObtenerTipsDeInternet(new ResultListener<List<Tip>>() {
                @Override
                public void loading() {

                }

                @Override
                public void finish(List<Tip> result) throws ParseException {
                    if (result != null){
                        daoTablaTips.insertarLosTips(result);
                        listenerFromView.finish(result);
                    }
                }

                @Override
                public void error(Throwable error) {


                }
            });

        } else{
           List<Tip> tips = daoTablaTips.consultaDeTips();
           listenerFromView.finish(tips);
        }

    }

    private Boolean hayInternet(Context context){
        return HTTPConnectionManager.isNetworkingOnline(context);
    }


}
