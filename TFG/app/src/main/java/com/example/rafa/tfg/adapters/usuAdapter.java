package com.example.rafa.tfg.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Rafa on 23/01/2018.
 */

public class usuAdapter implements Parcelable {
    private String _id;
    private String user;
    private String nombre;
    private String apellidos;
    private String pass;
    private String admin;
    private String email;
    private Integer keyToUse;
    private List<CasaPass> passCasa;


    public  usuAdapter(){
    }

    public usuAdapter(String _id, String user, String nombre, String apellidos, String pass, String admin, String email, Integer keyToUse, List<CasaPass> passCasa) {
        this._id = _id;
        this.user = user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.pass = pass;
        this.admin = admin;
        this.email = email;
        this.keyToUse = keyToUse;
        this.passCasa = passCasa;
    }



    public usuAdapter(String user, String nombre, String apellidos, String pass, String admin, String email, Integer keyToUse, List<CasaPass> passCasa) {
        this.user = user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.pass = pass;
        this.admin = admin;
        this.email = email;
        this.keyToUse = keyToUse;
        this.passCasa = passCasa;
    }

    public usuAdapter(String user, String nombre, String apellidos, String pass, String email, Integer keyToUse, List<CasaPass> passCasa) {
        this.user = user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.pass = pass;
        this.email = email;
        this.keyToUse = keyToUse;
        this.passCasa = passCasa;
    }

    protected usuAdapter(Parcel in) {
        _id = in.readString();
        user = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        pass = in.readString();
        admin = in.readString();
        email = in.readString();
        if (in.readByte() == 0) {
            keyToUse = null;
        } else {
            keyToUse = in.readInt();
        }
        passCasa = in.createTypedArrayList(CasaPass.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(user);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(pass);
        dest.writeString(admin);
        dest.writeString(email);
        if (keyToUse == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(keyToUse);
        }
        dest.writeTypedList(passCasa);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<usuAdapter> CREATOR = new Creator<usuAdapter>() {
        @Override
        public usuAdapter createFromParcel(Parcel in) {
            return new usuAdapter(in);
        }

        @Override
        public usuAdapter[] newArray(int size) {
            return new usuAdapter[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getKeyToUse() {
        return keyToUse;
    }

    public void setKeyToUse(Integer keyToUse) {
        this.keyToUse = keyToUse;
    }

    public List<CasaPass> getPassCasa() {
        return passCasa;
    }

    public void setPassCasa(List<CasaPass> passCasa) {
        this.passCasa = passCasa;
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
