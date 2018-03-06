package com.example.rafa.tfg.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by Rafael on 04/03/2018.
 */

public class CasaAdapterIni implements Parcelable {
    private String _id;
    private String homeUsu;

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

    public static  usuAdapter buildFromJson(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString,usuAdapter.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CasaAdapterIni that = (CasaAdapterIni) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        return homeUsu != null ? homeUsu.equals(that.homeUsu) : that.homeUsu == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (homeUsu != null ? homeUsu.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(homeUsu);
    }
}
