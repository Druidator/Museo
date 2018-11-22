package com.melvin.entregableweb.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.melvin.entregableweb.R;
import com.melvin.entregableweb.model.Pintura;
import com.melvin.entregableweb.util.GlideApp;

import java.util.List;

public class AdapterObra extends RecyclerView.Adapter {

    private List<Pintura> datos;
    private InterfaceObra listener;

    public AdapterObra(List<Pintura> datos, InterfaceObra listener) {
        this.datos = datos;
        this.listener = listener;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer id = datos.get(getAdapterPosition()).getIdArtista();
                    String referencia = datos.get(getAdapterPosition()).getImage();

                    Bundle datos = new Bundle();

                    datos.putInt(DetalleActivity.ID_ARTISTA, id);
                    datos.putString(DetalleActivity.KEY_IMAGEN, referencia);

                    listener.pasarDetalle(datos);

                }
            });
        }


        public void cargar(Pintura unaPintura){

            StorageReference reference = FirebaseStorage.getInstance().getReference();

            reference = reference.child(unaPintura.getImage());

            nombre.setText(unaPintura.getNombre());
            GlideApp.with(itemView.getContext()).load(reference).into(imagen);
        }
    }

    public void setDatos(List<Pintura> datos) {
        this.datos = datos;

        notifyDataSetChanged();
    }

    public interface InterfaceObra{

        void pasarDetalle(Bundle datos);
    }
}
