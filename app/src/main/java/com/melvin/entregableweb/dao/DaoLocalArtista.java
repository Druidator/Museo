package com.melvin.entregableweb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.melvin.entregableweb.model.Artista;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoLocalArtista {

    @Query("SELECT * FROM Artista where artistId = :artistId")
    Artista obtenerArtistaPorId(String artistId);

    @Insert(onConflict = REPLACE)
    void grabarArtista(Artista artista);
}

