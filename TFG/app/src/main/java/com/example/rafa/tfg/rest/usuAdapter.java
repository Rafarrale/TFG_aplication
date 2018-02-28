package com.example.rafa.tfg.rest;

import com.google.gson.Gson;

/**
 * Created by Rafa on 23/01/2018.
 */

public class usuAdapter {
    private String _id;
    private String user;
    private String nombre;
    private String apellidos;
    private String pass;
    private String admin;
    private String email;

    public usuAdapter(String _id, String user, String nombre, String apellidos, String pass, String admin, String email){
        this._id = _id;
        this.user = user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.pass = pass;
        this.admin = admin;
        this.email = email;
    }

    public usuAdapter(String user, String nombre, String apellidos, String email, String pass){
        this.user = user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.pass = pass;
        this.email = email;
    }

    public static  usuAdapter buildFromJson(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString,usuAdapter.class);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getUser() {
        return user;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getPass() {
        return pass;
    }

    public String getAdmin() {
        return admin;
    }

    public String getEmail() {
        return email;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        usuAdapter that = (usuAdapter) o;

        if (!_id.equals(that._id)) return false;
        if (!user.equals(that.user)) return false;
        if (!nombre.equals(that.nombre)) return false;
        if (!apellidos.equals(that.apellidos)) return false;
        if (!pass.equals(that.pass)) return false;
        if (!admin.equals(that.admin)) return false;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = _id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + apellidos.hashCode();
        result = 31 * result + pass.hashCode();
        result = 31 * result + admin.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
