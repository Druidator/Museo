package com.melvin.entregableweb.dao;

import android.arch.persistence.room.Dao;

import com.melvin.entregableweb.model.Pintura;

import java.util.List;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoLocalPintura {

    @Query("SELECT * FROM Pintura")
    List<Pintura> obtenerPinturas();

    @Insert(onConflict = REPLACE)
    long grabarPintura (Pintura pintura);
}
