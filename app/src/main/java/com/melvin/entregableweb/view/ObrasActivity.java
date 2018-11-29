package com.melvin.entregableweb.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.melvin.entregableweb.R;
import com.melvin.entregableweb.controller.ControllerPintura;
import com.melvin.entregableweb.model.Pintura;
import com.melvin.entregableweb.util.ResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;

public class ObrasActivity extends AppCompatActivity implements AdapterObra.InterfaceObra{

    private List<Pintura> datos = new ArrayList<>();
    private AdapterObra adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obras);

        String nombreUsuario = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        getSupportActionBar().setTitle(nombreUsuario);

        adapter = new AdapterObra(datos, this);

        new ControllerPintura().obtenerPinturas(this, new ResultListener<List<Pintura>>() {
            @Override
            public void finish(List<Pintura> resultado) {
                datos = resultado;

                adapter.setDatos(datos);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ControllerPintura().obtenerPinturas(ObrasActivity.this, new ResultListener<List<Pintura>>() {
                    @Override
                    public void finish(List<Pintura> resultado) {
                        datos = resultado;

                        adapter.setDatos(datos);
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView recycler = findViewById(R.id.recyclerObras);

        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        recycler.setAdapter(adapter);

        recycler.setLayoutManager(manager);

        FloatingActionButton fabCamera = findViewById(R.id.fabCamera);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openChooserWithGallery(ObrasActivity.this, "Selecciona...", 101);
            }
        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_obras, menu);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.botonLogout){
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Intent unIntent = new Intent(ObrasActivity.this, MainActivity.class);
            unIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            unIntent.setFlags(unIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(unIntent);
        }

        if (item.getItemId() == R.id.chat){
            startActivity(new Intent(ObrasActivity.this, ChatActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void pasarDetalle(Bundle datos) {
        startActivity(new Intent(ObrasActivity.this, DetalleActivity.class).putExtras(datos));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, ObrasActivity.this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource imageSource, int i) {

            }

            @Override
            public void onImagesPicked(@NonNull List<File> list, EasyImage.ImageSource imageSource, int i) {

                if (list.size() > 0){

                    switch (i){
                        case 101:

                            File file = list.get(0);

                            Uri uri = Uri.fromFile(file);

                            StorageReference reference = FirebaseStorage.getInstance().getReference().child("fotos").child(uri.getLastPathSegment());

                            UploadTask uploadTask = reference.putFile(uri);

                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(ObrasActivity.this, "Se subio exitosamente", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                }

            }

            @Override
            public void onCanceled(EasyImage.ImageSource imageSource, int i) {

            }
        });
    }
}
