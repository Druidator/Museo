package com.melvin.entregableweb.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.melvin.entregableweb.R;
import com.melvin.entregableweb.controller.ControllerPintura;
import com.melvin.entregableweb.model.Pintura;
import com.melvin.entregableweb.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class ObrasActivity extends AppCompatActivity {

    private List<Pintura> datos = new ArrayList<>();
    private AdapterObra adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obras);


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

        adapter = new AdapterObra(datos);

        recycler.setAdapter(adapter);

        recycler.setLayoutManager(manager);


    }
}
