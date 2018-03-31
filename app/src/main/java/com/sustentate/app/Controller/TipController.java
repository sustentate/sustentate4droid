package com.sustentate.app.Controller;

import com.sustentate.app.DAO.DAOTipsDeInternet;
import com.sustentate.app.api.ResultListener;
import com.sustentate.app.models.Tip;

import java.util.List;

/**
 * Created by emzas on 28/3/2018.
 */

public class TipController {

    public void obtenerTips(final ResultListener<List<Tip>> listenerFromView) {

        DAOTipsDeInternet daoTipsDeInternet = new DAOTipsDeInternet();
        daoTipsDeInternet.obtenerProductosDeInternet(new ResultListener<List<Tip>>() {
            @Override
            public void loading() {

            }

            @Override
            public void finish(List<Tip> result) {
                if (result != null){
                    listenerFromView.finish(result);
                }
            }

            @Override
            public void error(Throwable error) {

            }
        });

    }
}
