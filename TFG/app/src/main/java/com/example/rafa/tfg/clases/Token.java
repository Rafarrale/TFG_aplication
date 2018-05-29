package com.example.rafa.tfg.clases;

import com.example.rafa.tfg.adapters.CasaPass;

import java.util.List;

public class Token {
    private String token;
    private List<CasaPass> passCasa;
    private String casa;
    private String _id;
    private Integer keyToUse;

    public Token() {
    }

    public Token(String token, List<CasaPass> passCasa, String casa, String _id, Integer keyToUse) {
        this.token = token;
        this.passCasa = passCasa;
        this.casa = casa;
        this._id = _id;
        this.keyToUse = keyToUse;
    }

    public Token(String token, List<CasaPass> passCasa, String _id) {
        this.token = token;
        this.passCasa = passCasa;
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CasaPass> getPassCasa() {
        return passCasa;
    }

    public void setPassCasa(List<CasaPass> passCasa) {
        this.passCasa = passCasa;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getKeyToUse() {
        return keyToUse;
    }

    public void setKeyToUse(Integer keyToUse) {
        this.keyToUse = keyToUse;
    }
}
