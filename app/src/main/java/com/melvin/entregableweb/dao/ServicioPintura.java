package com.melvin.entregableweb.dao;

import com.melvin.entregableweb.model.PinturaContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicioPintura {

    @GET("https://api.myjson.com/bins/x858r")
    Call<PinturaContainer> obtenerPinturas();
}
