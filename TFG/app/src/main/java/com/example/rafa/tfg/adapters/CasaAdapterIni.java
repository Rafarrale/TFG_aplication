package com.example.rafa.tfg.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rafa.tfg.clases.Configuracion;
import com.google.gson.Gson;

/**
 * Created by Rafael on 04/03/2018.
 */

public class CasaAdapterIni implements Parcelable {
    private String _id;
    private String homeUsu;
    private Configuracion configuracion;

    public CasaAdapterIni(String _id, String homeUsu, Configuracion configuracion) {
        this._id = _id;
        this.homeUsu = homeUsu;
        this.configuracion = configuracion;
    }

    public CasaAdapterIni(String _id, String homeUsu) {
        this._id = _id;
        this.homeUsu = homeUsu;
    }

    public CasaAdapterIni(String homeUsu){
        this.homeUsu = homeUsu;
    }

    protected CasaAdapterIni(Parcel in) {
        _id = in.readString();
        homeUsu = in.readString();
        configuracion = in.readParcelable(Configuracion.class.getClassLoader());
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(homeUsu);
        dest.writeParcelable(configuracion,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CasaAdapterIni> CREATOR = new Creator<CasaAdapterIni>() {
        @Override
        public CasaAdapterIni createFromParcel(Parcel in) {
            return new CasaAdapterIni(in);
        }

        @Override
        public CasaAdapterIni[] newArray(int size) {
            return new CasaAdapterIni[size];
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CasaAdapterIni that = (CasaAdapterIni) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (homeUsu != null ? !homeUsu.equals(that.homeUsu) : that.homeUsu != null) return false;
        return configuracion != null ? configuracion.equals(that.configuracion) : that.configuracion == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (homeUsu != null ? homeUsu.hashCode() : 0);
        result = 31 * result + (configuracion != null ? configuracion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CasaAdapterIni{" +
                "_id='" + _id + '\'' +
                ", homeUsu='" + homeUsu + '\'' +
                ", configuracion=" + configuracion +
                '}';
    }

    public static  usuAdapter buildFromJson(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString,usuAdapter.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
