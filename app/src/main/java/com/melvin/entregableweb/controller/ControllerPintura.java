package com.melvin.entregableweb.controller;

import android.content.Context;

import com.melvin.entregableweb.dao.DaoInternetPintura;
import com.melvin.entregableweb.dao.DatabaseApp;
import com.melvin.entregableweb.model.Pintura;
import com.melvin.entregableweb.util.ResultListener;
import com.melvin.entregableweb.util.Util;

import java.util.List;

public class ControllerPintura {

    private DatabaseApp database;

    public void obtenerPinturas(Context context, final ResultListener<List<Pintura>> listenerView){

        database = DatabaseApp.getInMemoryDatabase(context);

        if (Util.hayInternet(context)){

            new DaoInternetPintura().obtenerPinturas(new ResultListener<List<Pintura>>() {
                @Override
                public void finish(List<Pintura> resultado) {
                    listenerView.finish(resultado);

                    for (Pintura unaPintura : resultado){
                        database.modeloPintura().grabarPintura(unaPintura);
                    }
                }
            });
        } else {

            List<Pintura> datos = database.modeloPintura().obtenerPinturas();
            listenerView.finish(datos);
        }
    }
}
