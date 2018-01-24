package com.example.rafa.tfg.rest;

import com.google.gson.Gson;

/**
 * Created by Rafa on 23/01/2018.
 */

public class usuAdapter {
    private int oidUsu;
    private String user;
    private String nombre;
    private String apellidos;
    private String pass;
    private String admin;
    private String email;

    public usuAdapter(int oidUsu, String user, String nombre, String apellidos, String pass, String admin, String email){
        this.oidUsu = oidUsu;
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

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getOidUsu() {
        return oidUsu;
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

    public void setOidUsu(int oidUsu) {
        this.oidUsu = oidUsu;
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

        if (oidUsu != that.oidUsu) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (apellidos != null ? !apellidos.equals(that.apellidos) : that.apellidos != null)
            return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;
        if (admin != null ? !admin.equals(that.admin) : that.admin != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = oidUsu;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (admin != null ? admin.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "usuAdapter{" +
                "oidUsu=" + oidUsu +
                ", user='" + user + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", pass='" + pass + '\'' +
                ", admin='" + admin + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
