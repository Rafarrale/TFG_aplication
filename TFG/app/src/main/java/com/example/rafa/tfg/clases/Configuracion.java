package com.example.rafa.tfg.clases;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Configuracion {

    private String estadoAlarma;
    private String cuentaAtrasSonido;
    private String volumenSonido;
    private String sonidoPuerta;

    public Configuracion(String estadoAlarma, String cuentaAtrasSonido, String volumenSonido, String sonidoPuerta) {
        this.estadoAlarma = estadoAlarma;
        this.cuentaAtrasSonido = cuentaAtrasSonido;
        this.volumenSonido = volumenSonido;
        this.sonidoPuerta = sonidoPuerta;
    }

    public Configuracion(){

    }

    public String getEstadoAlarma() {
        return estadoAlarma;
    }

    public void setEstadoAlarma(String estadoAlarma) {
        this.estadoAlarma = estadoAlarma;
    }

    public String getCuentaAtrasSonido() {
        return cuentaAtrasSonido;
    }

    public void setCuentaAtrasSonido(String cuentaAtrasSonido) {
        this.cuentaAtrasSonido = cuentaAtrasSonido;
    }

    public String getVolumenSonido() {
        return volumenSonido;
    }

    public void setVolumenSonido(String volumenSonido) {
        this.volumenSonido = volumenSonido;
    }

    public String getSonidoPuerta() {
        return sonidoPuerta;
    }

    public void setSonidoPuerta(String sonidoPuerta) {
        this.sonidoPuerta = sonidoPuerta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuracion that = (Configuracion) o;

        if (estadoAlarma != null ? !estadoAlarma.equals(that.estadoAlarma) : that.estadoAlarma != null)
            return false;
        if (cuentaAtrasSonido != null ? !cuentaAtrasSonido.equals(that.cuentaAtrasSonido) : that.cuentaAtrasSonido != null)
            return false;
        if (volumenSonido != null ? !volumenSonido.equals(that.volumenSonido) : that.volumenSonido != null)
            return false;
        return sonidoPuerta != null ? sonidoPuerta.equals(that.sonidoPuerta) : that.sonidoPuerta == null;
    }

    @Override
    public int hashCode() {
        int result = estadoAlarma != null ? estadoAlarma.hashCode() : 0;
        result = 31 * result + (cuentaAtrasSonido != null ? cuentaAtrasSonido.hashCode() : 0);
        result = 31 * result + (volumenSonido != null ? volumenSonido.hashCode() : 0);
        result = 31 * result + (sonidoPuerta != null ? sonidoPuerta.hashCode() : 0);
        return result;
    }
}
