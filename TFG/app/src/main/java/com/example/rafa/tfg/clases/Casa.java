package com.example.rafa.tfg.clases;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Casa{

    private String _id;
    private String homeUsu;
    private Configuracion configuracion;
    private Dispositivos dispositivos;
    private Camaras camaras;
    private LogSeguridad logSeguridad;
    private LogUsuarios logUsuarios;

    public Casa(String _id, String homeUsu, Configuracion configuracion,
                Dispositivos dispositivos, Camaras camaras, LogSeguridad logSeguridad, LogUsuarios logUsuarios) {
        super();
        this._id = _id;
        this.homeUsu = homeUsu;
        this.configuracion = configuracion;
        this.dispositivos = dispositivos;
        this.camaras = camaras;
        this.logSeguridad = logSeguridad;
        this.logUsuarios = logUsuarios;
    }

    public Casa(String _id, String homeUsu, Configuracion configuracion){
        super();
        this._id = _id;
        this.homeUsu = homeUsu;
        this.configuracion = configuracion;
    }

    public Casa(String homeUsu){
        super();
        this.homeUsu = homeUsu;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHomeUsu() {
        return homeUsu;
    }

    public void setHomeUsu(String homeUsu) {
        this.homeUsu = homeUsu;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public Dispositivos getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(Dispositivos dispositivos) {
        this.dispositivos = dispositivos;
    }

    public Camaras getCamaras() {
        return camaras;
    }

    public void setCamaras(Camaras camaras) {
        this.camaras = camaras;
    }

    public LogSeguridad getLogSeguridad() {
        return logSeguridad;
    }

    public void setLogSeguridad(LogSeguridad logSeguridad) {
        this.logSeguridad = logSeguridad;
    }

    public LogUsuarios getLogUsuarios() {
        return logUsuarios;
    }

    public void setLogUsuarios(LogUsuarios logUsuarios) {
        this.logUsuarios = logUsuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Casa casa = (Casa) o;

        if (!_id.equals(casa._id)) return false;
        if (homeUsu != null ? !homeUsu.equals(casa.homeUsu) : casa.homeUsu != null) return false;
        if (configuracion != null ? !configuracion.equals(casa.configuracion) : casa.configuracion != null)
            return false;
        if (dispositivos != null ? !dispositivos.equals(casa.dispositivos) : casa.dispositivos != null)
            return false;
        if (camaras != null ? !camaras.equals(casa.camaras) : casa.camaras != null) return false;
        if (logSeguridad != null ? !logSeguridad.equals(casa.logSeguridad) : casa.logSeguridad != null)
            return false;
        return logUsuarios != null ? logUsuarios.equals(casa.logUsuarios) : casa.logUsuarios == null;
    }

    @Override
    public int hashCode() {
        int result = _id.hashCode();
        result = 31 * result + (homeUsu != null ? homeUsu.hashCode() : 0);
        result = 31 * result + (configuracion != null ? configuracion.hashCode() : 0);
        result = 31 * result + (dispositivos != null ? dispositivos.hashCode() : 0);
        result = 31 * result + (camaras != null ? camaras.hashCode() : 0);
        result = 31 * result + (logSeguridad != null ? logSeguridad.hashCode() : 0);
        result = 31 * result + (logUsuarios != null ? logUsuarios.hashCode() : 0);
        return result;
    }
}
