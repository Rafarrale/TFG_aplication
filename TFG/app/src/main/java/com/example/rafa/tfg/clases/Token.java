package com.example.rafa.tfg.clases;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rafael on 06/04/2018.
 */

public class Token {
    @SerializedName("_id")
    private String _id;
    @SerializedName("homeUsu")
    private String homeUsu;
    @SerializedName("token")
    private String token;


    public Token() {
        super();
    }

    public Token(String _id, String homeUsu, String token) {
        this._id = _id;
        this.homeUsu = homeUsu;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        if (_id != null ? !_id.equals(token1._id) : token1._id != null) return false;
        if (homeUsu != null ? !homeUsu.equals(token1.homeUsu) : token1.homeUsu != null)
            return false;
        return token != null ? token.equals(token1.token) : token1.token == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (homeUsu != null ? homeUsu.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
