package com.melvin.entregableweb.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.melvin.entregableweb.R;
import com.melvin.entregableweb.controller.ControllerPintura;
import com.melvin.entregableweb.model.Pintura;
import com.melvin.entregableweb.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class ObrasActivity extends AppCompatActivity implements AdapterObra.InterfaceObra{

    private List<Pintura> datos = new ArrayList<>();
    private AdapterObra adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obras);

        String nombreUsuario = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        getSupportActionBar().setTitle(nombreUsuario);

        new ControllerPintura().obtenerPinturas(this, new ResultListener<List<Pintura>>() {
            @Override
            public void finish(List<Pintura> resultado) {
                datos = resultado;

                adapter.setDatos(datos);
            }
        });

        RecyclerView recycler = findViewById(R.id.recyclerObras);

        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        adapter = new AdapterObra(datos, this);

        recycler.setAdapter(adapter);

        recycler.setLayoutManager(manager);

        FloatingActionButton fabCamera = findViewById(R.id.fabCamera);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void pasarDetalle(Bundle datos) {
        startActivity(new Intent(ObrasActivity.this, DetalleActivity.class).putExtras(datos));
    }
}
