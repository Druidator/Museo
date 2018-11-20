package com.melvin.entregableweb.dao;

import com.melvin.entregableweb.model.Pintura;
import com.melvin.entregableweb.model.PinturaContainer;
import com.melvin.entregableweb.util.DaoHelper;
import com.melvin.entregableweb.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetPintura extends DaoHelper {

    public static final String BASE_URL = "https://api.myjson.com/";
    private ServicioPintura servicioPintura;

    public DaoInternetPintura() {
        super(BASE_URL);

        servicioPintura = retrofit.create(ServicioPintura.class);
    }

    public void obtenerPinturas(final ResultListener<List<Pintura>> listenerController){

        Call<PinturaContainer> call = servicioPintura.obtenerPinturas();

        call.enqueue(new Callback<PinturaContainer>() {
            @Override
            public void onResponse(Call<PinturaContainer> call, Response<PinturaContainer> response) {

                PinturaContainer container = response.body();

                List<Pintura> datos = container.getPaints();

                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<PinturaContainer> call, Throwable t) {

            }
        });
    }
}
