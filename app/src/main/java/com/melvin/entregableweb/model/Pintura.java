package com.melvin.entregableweb.model;

import com.google.gson.annotations.SerializedName;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Pintura {

    @PrimaryKey
    @NonNull
    private String image;
    @SerializedName("name")
    private String nombre;
    @SerializedName("artistId")
    private Integer idArtista;

    public Pintura(String image, String nombre, Integer idArtista) {
        this.image = image;
        this.nombre = nombre;
        this.idArtista = idArtista;
    }

    public String getImage() {
        return image;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getIdArtista() {
        return idArtista;
    }
}
