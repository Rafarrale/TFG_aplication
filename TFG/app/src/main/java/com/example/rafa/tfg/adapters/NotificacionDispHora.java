package com.example.rafa.tfg.adapters;


public class NotificacionDispHora extends DispositivosAdapter {
    private String hora;
    private boolean visto;

    public NotificacionDispHora() {
        super();
    }

    public NotificacionDispHora(String hora, boolean visto) {
        this.hora = hora;
        this.visto = visto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
