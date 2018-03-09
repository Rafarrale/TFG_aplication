package com.example.rafa.tfg.adapters;

import com.example.rafa.tfg.clases.Configuracion;

/**
 * Created by Rafael on 07/03/2018.
 */

public class estadoAlarmaAdapter {
    private Configuracion configuracion;

    public estadoAlarmaAdapter(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        estadoAlarmaAdapter that = (estadoAlarmaAdapter) o;

        return configuracion != null ? configuracion.equals(that.configuracion) : that.configuracion == null;
    }

    @Override
    public int hashCode() {
        return configuracion != null ? configuracion.hashCode() : 0;
    }
}
