package com.melvin.entregableweb.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.melvin.entregableweb.R;
import com.melvin.entregableweb.model.Mensaje;

import java.util.List;

public class AdapterMensaje extends RecyclerView.Adapter {

    private List<Mensaje> datos;
    private Context context;

    public AdapterMensaje(List<Mensaje> datos, Context context) {
        this.datos = datos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_mensaje, viewGroup, false);
        MensajeViewHolder holder = new MensajeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MensajeViewHolder holder = (MensajeViewHolder) viewHolder;

        holder.cargar(datos.get(i));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    private class MensajeViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private TextView mensaje;
        private LinearLayout layout;

        public MensajeViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.escritorMensaje);
            mensaje = itemView.findViewById(R.id.mensaje);
            layout = itemView.findViewById(R.id.layoutMensaje);
        }

        public void cargar(Mensaje unMensaje){

            nombre.setText(unMensaje.getNombre());
            mensaje.setText(unMensaje.getMensaje());
            String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (idUsuario.equals(unMensaje.getIdUsuario())){
                nombre.setTextColor(ContextCompat.getColor(context, R.color.nombreLogeado));
                layout.setGravity(Gravity.END);
            }
            else {
                nombre.setTextColor(ContextCompat.getColor(context, R.color.nombreOtros));
                layout.setGravity(Gravity.START);
            }

        }
    }

    public void setDatos(List<Mensaje> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }
}
