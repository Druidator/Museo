package com.melvin.entregableweb.dao;

import android.content.Context;

import com.melvin.entregableweb.model.Pintura;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Database;



@Database(entities = {Pintura.class}, version = 1, exportSchema = false)
public abstract class DatabaseApp extends RoomDatabase {

    public static DatabaseApp INSTANCE;

    public abstract DaoLocalPintura modeloPintura();

    public static DatabaseApp getInMemoryDatabase(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseApp.class, "dbMuseo").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
