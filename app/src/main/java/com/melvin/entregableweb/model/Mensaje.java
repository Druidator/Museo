package com.melvin.entregableweb.model;

public class Mensaje {

    private String nombre;
    private String mensaje;
    private String idUsuario;

    public Mensaje(){}

    public Mensaje(String nombre, String mensaje, String idUsuario) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}
