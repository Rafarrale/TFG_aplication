package com.example.rafa.tfg.clases;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rafa.tfg.adapters.DispositivosAdapter;

import java.util.Objects;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Casa implements Parcelable {

    private String _id;
    private String homeUsu;
    private Configuracion configuracion;
    private DispositivosAdapter dispositivosAdapter;
    private Camaras camaras;
    private LogSeguridad logSeguridad;
    private LogUsuarios logUsuarios;


    public Casa() {

    }

    public Casa(String _id, String homeUsu, Configuracion configuracion, DispositivosAdapter dispositivosAdapter, Camaras camaras, LogSeguridad logSeguridad, LogUsuarios logUsuarios) {
        this._id = _id;
        this.homeUsu = homeUsu;
        this.configuracion = configuracion;
        this.dispositivosAdapter = dispositivosAdapter;
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

    protected Casa(Parcel in) {
        _id = in.readString();
        homeUsu = in.readString();
        configuracion = in.readParcelable(Configuracion.class.getClassLoader());
        dispositivosAdapter = in.readParcelable(DispositivosAdapter.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(homeUsu);
        dest.writeParcelable(configuracion, flags);
        dest.writeParcelable(dispositivosAdapter, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Casa> CREATOR = new Creator<Casa>() {
        @Override
        public Casa createFromParcel(Parcel in) {
            return new Casa(in);
        }

        @Override
        public Casa[] newArray(int size) {
            return new Casa[size];
        }
    };

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

    public DispositivosAdapter getDispositivosAdapter() {
        return dispositivosAdapter;
    }

    public void setDispositivosAdapter(DispositivosAdapter dispositivosAdapter) {
        this.dispositivosAdapter = dispositivosAdapter;
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
        return Objects.equals(_id, casa._id) &&
                Objects.equals(homeUsu, casa.homeUsu) &&
                Objects.equals(configuracion, casa.configuracion) &&
                Objects.equals(dispositivosAdapter, casa.dispositivosAdapter) &&
                Objects.equals(camaras, casa.camaras) &&
                Objects.equals(logSeguridad, casa.logSeguridad) &&
                Objects.equals(logUsuarios, casa.logUsuarios);
    }

    @Override
    public int hashCode() {

        return Objects.hash(_id, homeUsu, configuracion, dispositivosAdapter, camaras, logSeguridad, logUsuarios);
    }
}
