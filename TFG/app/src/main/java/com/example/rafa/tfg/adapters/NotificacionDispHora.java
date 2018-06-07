package com.example.rafa.tfg.adapters;

public class NotificacionDispHora extends  DispositivosAdapter {
    private String hora;

    public NotificacionDispHora() {
        super();
    }

    public NotificacionDispHora(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
