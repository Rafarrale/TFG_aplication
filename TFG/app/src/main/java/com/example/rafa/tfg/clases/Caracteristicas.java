package com.example.rafa.tfg.clases;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Caracteristicas {

    private String activo;

    public Caracteristicas() {

    }

    public Caracteristicas(String activo) {
        this.activo = activo;
    }



    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Caracteristicas that = (Caracteristicas) o;

        return activo != null ? activo.equals(that.activo) : that.activo == null;
    }

    @Override
    public int hashCode() {
        return activo != null ? activo.hashCode() : 0;
    }
}
