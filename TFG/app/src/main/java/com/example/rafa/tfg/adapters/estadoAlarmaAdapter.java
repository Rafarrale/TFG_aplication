package com.example.rafa.tfg.adapters;

/**
 * Created by Rafael on 07/03/2018.
 */

public class estadoAlarmaAdapter {
    private String estadoAlarma;

    public estadoAlarmaAdapter(String estadoAlarma) {
        this.estadoAlarma = estadoAlarma;
    }

    public String getEstadoAlarma() {
        return estadoAlarma;
    }

    public void setEstadoAlarma(String estadoAlarma) {
        this.estadoAlarma = estadoAlarma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        estadoAlarmaAdapter that = (estadoAlarmaAdapter) o;

        return estadoAlarma != null ? estadoAlarma.equals(that.estadoAlarma) : that.estadoAlarma == null;
    }

    @Override
    public int hashCode() {
        return estadoAlarma != null ? estadoAlarma.hashCode() : 0;
    }
}
