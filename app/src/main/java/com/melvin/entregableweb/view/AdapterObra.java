package com.melvin.entregableweb.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melvin.entregableweb.R;
import com.melvin.entregableweb.model.Pintura;

import java.util.List;

public class AdapterObra extends RecyclerView.Adapter {

    private List<Pintura> datos;

    public AdapterObra(List<Pintura> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_item_obra, viewGroup, false);

        ObraViewHolder holder = new ObraViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ObraViewHolder holder = (ObraViewHolder) viewHolder;

        holder.cargar(datos.get(i));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    private class ObraViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private ImageView imagen;

        public ObraViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombrePintura);
            imagen = itemView.findViewById(R.id.imagenPintura);
        }


        public void cargar(Pintura unaPintura){

            nombre.setText(unaPintura.getNombre());
            imagen.setImageResource(R.drawable.grito);
        }
    }

    public void setDatos(List<Pintura> datos) {
        this.datos = datos;

        notifyDataSetChanged();
    }
}
