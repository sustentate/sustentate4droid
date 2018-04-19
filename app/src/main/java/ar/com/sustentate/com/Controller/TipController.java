package com.sustentate.app.Controller;

import android.content.Context;

import com.sustentate.app.DAO.DAOTablaTips;
import com.sustentate.app.DAO.DAOTipsDeInternet;
import com.sustentate.app.api.ResultListener;
import com.sustentate.app.models.Tip;
import com.sustentate.app.utils.HTTPConnectionManager;

import java.util.List;

/**
 * Created by emzas on 28/3/2018.
 */

public class TipController {

    public void obtenerTips( Context context, final ResultListener<List<Tip>> listenerFromView) {

        final DAOTablaTips daoTablaTips = new DAOTablaTips(context);

        if (hayInternet(context)){

            DAOTipsDeInternet daoTipsDeInternet = new DAOTipsDeInternet();
            daoTipsDeInternet.obtenerProductosDeInternet(new ResultListener<List<Tip>>() {
                @Override
                public void loading() {

                }

                @Override
                public void finish(List<Tip> result) {
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
