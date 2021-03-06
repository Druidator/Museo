package com.melvin.entregableweb.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.melvin.entregableweb.R;
import com.melvin.entregableweb.dao.DatabaseApp;
import com.melvin.entregableweb.model.Artista;
import com.melvin.entregableweb.util.GlideApp;
import com.melvin.entregableweb.util.Util;

public class DetalleActivity extends AppCompatActivity {

    public static final String ID_ARTISTA = "idArtista";
    public static final String KEY_IMAGEN = "imagen";
    private DatabaseApp databaseApp;
    private Artista artista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        String nombreUsuario = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        getSupportActionBar().setTitle(nombreUsuario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Integer idArtista = getIntent().getExtras().getInt(ID_ARTISTA);
        final String referenciaImagen = getIntent().getExtras().getString(KEY_IMAGEN);

        final ImageView imagen = findViewById(R.id.imagenDetalle);
        final TextView nombre = findViewById(R.id.nombreArtista);
        final TextView nacionalidad = findViewById(R.id.nacionalidadArtista);
        final TextView influenciado = findViewById(R.id.influenciadoArtista);




        databaseApp = DatabaseApp.getInMemoryDatabase(this);

        if (Util.hayInternet(this)) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("artists");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Artista unArtista = child.getValue(Artista.class);

                        if (unArtista.getArtistId().equals(idArtista.toString())) {

                            artista = unArtista;

                            nombre.setText(artista.getName());
                            nacionalidad.setText(artista.getNationality());
                            influenciado.setText(artista.getInfluenced_by());

                            StorageReference referenceImage = FirebaseStorage.getInstance().getReference();

                            referenceImage = referenceImage.child(referenciaImagen);

                            GlideApp.with(DetalleActivity.this).load(referenceImage).into(imagen);
                        }

                        databaseApp.modeloArtista().grabarArtista(unArtista);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {

            artista = databaseApp.modeloArtista().obtenerArtistaPorId(idArtista.toString());

            nombre.setText(artista.getName());
            nacionalidad.setText(artista.getNationality());
            influenciado.setText(artista.getInfluenced_by());

            StorageReference referenceImage = FirebaseStorage.getInstance().getReference();

            referenceImage = referenceImage.child(referenciaImagen);

            GlideApp.with(DetalleActivity.this).load(referenceImage).into(imagen);
        }




    }
}
