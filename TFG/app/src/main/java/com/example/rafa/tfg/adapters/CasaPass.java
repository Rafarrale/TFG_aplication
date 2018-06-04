package com.example.rafa.tfg.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class CasaPass implements Parcelable{
    private String key;

    public CasaPass() {
    }

    public CasaPass(String key) {
        this.key = key;
    }

    protected CasaPass(Parcel in) {
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CasaPass> CREATOR = new Creator<CasaPass>() {
        @Override
        public CasaPass createFromParcel(Parcel in) {
            return new CasaPass(in);
        }

        @Override
        public CasaPass[] newArray(int size) {
            return new CasaPass[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasaPass casaPass = (CasaPass) o;
        return Objects.equals(key, casaPass.key);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key);
    }
}
