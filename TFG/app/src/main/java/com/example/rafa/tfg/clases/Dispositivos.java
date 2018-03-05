package com.example.rafa.tfg.clases;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Dispositivos extends Caracteristicas{
    private String code;
    private String name;
    private Caracteristicas caracteristicas;

    public Dispositivos(String code, String name, Caracteristicas caracteristicas) {
        this.code = code;
        this.name = name;
        this.caracteristicas = caracteristicas;
    }

    public Dispositivos(String activo, String code, String name, Caracteristicas caracteristicas) {
        super();
        this.code = code;
        this.name = name;
        this.caracteristicas = caracteristicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Dispositivos that = (Dispositivos) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return caracteristicas != null ? caracteristicas.equals(that.caracteristicas) : that.caracteristicas == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (caracteristicas != null ? caracteristicas.hashCode() : 0);
        return result;
    }
}
