package com.example.rafa.tfg.clases;

public class Token {
    private String token;
    private String passCasa;
    private String casa;
    private String _id;

    public Token() {
    }


    public Token(String token, String passCasa) {
        this.token = token;
        this.passCasa = passCasa;
    }

    public Token(String token, String passCasa, String _id) {
        this.token = token;
        this.passCasa = passCasa;
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassCasa() {
        return passCasa;
    }

    public void setPassCasa(String passCasa) {
        this.passCasa = passCasa;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }
}
