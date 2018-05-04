package com.example.rafa.tfg.clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by Rafael on 06/04/2018.
 */

public class SettingPreferences {
    private final Context context;

    public SettingPreferences(Context context) {
        this.context = context;
    }


    public void save(Token token, String PrefKey) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String json = new Gson().toJson(token);
        edit.putString(PrefKey,json);
        edit.commit();
    }

    public void remove(String PrefKey) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.remove(PrefKey);
        edit.commit();
    }

    public Token getToken(String PrefKey) {
        String json = getSharedPreferences().getString(PrefKey,null);
        if(json == null){
            return new Token();
        }
        Token person = new Gson().fromJson(json, Token.class);
        return person;
    }


    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }

}
