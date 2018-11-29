package com.melvin.entregableweb.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.melvin.entregableweb.dao.DatabaseApp;
import com.melvin.entregableweb.model.Artista;
import com.melvin.entregableweb.util.Util;

public class ControllerArtista {

    private Artista unArtista;
    private DatabaseApp databaseApp;

    public Artista obtenerArtistaPorId(Context context, final Integer idArtista) {

        databaseApp = DatabaseApp.getInMemoryDatabase(context);

        if (Util.hayInternet(context)) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("artists");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Artista artista = child.getValue(Artista.class);

                        if (artista.getArtistId().equals(idArtista.toString())) {

                            unArtista = artista;
                        }

                        databaseApp.modeloArtista().grabarArtista(artista);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {

            unArtista = databaseApp.modeloArtista().obtenerArtistaPorId(idArtista.toString());
        }

        return unArtista;
    }

}

