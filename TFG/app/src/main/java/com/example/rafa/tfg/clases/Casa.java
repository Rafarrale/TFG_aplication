package com.example.rafa.tfg.clases;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rafa.tfg.adapters.DispositivosAdapter;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Casa implements Parcelable {

    private String _id;
    private String homeUsu;
    private String wifi;
    private String ssid;
    private Configuracion configuracion;
    private DispositivosAdapter dispositivosAdapter;
    private Camaras camaras;
    private LogSeguridad logSeguridad;
    private LogUsuarios logUsuarios;

    public Casa(String _id, String homeUsu, String wifi, String ssid, Configuracion configuracion, DispositivosAdapter dispositivosAdapter, Camaras camaras, LogSeguridad logSeguridad, LogUsuarios logUsuarios) {
        this._id = _id;
        this.homeUsu = homeUsu;
        this.wifi = wifi;
        this.ssid = ssid;
        this.configuracion = configuracion;
        this.dispositivosAdapter = dispositivosAdapter;
        this.camaras = camaras;
        this.logSeguridad = logSeguridad;
        this.logUsuarios = logUsuarios;
    }

    public Casa(String _id, String homeUsu, String wifi, String ssid, Configuracion configuracion){
        super();
        this._id = _id;
        this.homeUsu = homeUsu;
        this.wifi = wifi;
        this.ssid = ssid;
        this.configuracion = configuracion;
    }

    public Casa(String homeUsu, String wifi, String ssid){
        super();
        this.homeUsu = homeUsu;
        this.wifi = wifi;
        this.ssid = ssid;
    }


    protected Casa(Parcel in) {
        _id = in.readString();
        homeUsu = in.readString();
        wifi = in.readString();
        ssid = in.readString();
        configuracion = in.readParcelable(Configuracion.class.getClassLoader());
        dispositivosAdapter = in.readParcelable(DispositivosAdapter.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(homeUsu);
        dest.writeString(wifi);
        dest.writeString(ssid);
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

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
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

        if (_id != null ? !_id.equals(casa._id) : casa._id != null) return false;
        if (homeUsu != null ? !homeUsu.equals(casa.homeUsu) : casa.homeUsu != null) return false;
        if (wifi != null ? !wifi.equals(casa.wifi) : casa.wifi != null) return false;
        if (ssid != null ? !ssid.equals(casa.ssid) : casa.ssid != null) return false;
        if (configuracion != null ? !configuracion.equals(casa.configuracion) : casa.configuracion != null)
            return false;
        if (dispositivosAdapter != null ? !dispositivosAdapter.equals(casa.dispositivosAdapter) : casa.dispositivosAdapter != null)
            return false;
        if (camaras != null ? !camaras.equals(casa.camaras) : casa.camaras != null) return false;
        if (logSeguridad != null ? !logSeguridad.equals(casa.logSeguridad) : casa.logSeguridad != null)
            return false;
        return logUsuarios != null ? logUsuarios.equals(casa.logUsuarios) : casa.logUsuarios == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (homeUsu != null ? homeUsu.hashCode() : 0);
        result = 31 * result + (wifi != null ? wifi.hashCode() : 0);
        result = 31 * result + (ssid != null ? ssid.hashCode() : 0);
        result = 31 * result + (configuracion != null ? configuracion.hashCode() : 0);
        result = 31 * result + (dispositivosAdapter != null ? dispositivosAdapter.hashCode() : 0);
        result = 31 * result + (camaras != null ? camaras.hashCode() : 0);
        result = 31 * result + (logSeguridad != null ? logSeguridad.hashCode() : 0);
        result = 31 * result + (logUsuarios != null ? logUsuarios.hashCode() : 0);
        return result;
    }
}
