package com.example.rafa.tfg.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rafa.tfg.clases.Caracteristicas;

/**
 * Created by Rafael on 04/03/2018.
 */

public class DispositivosAdapter implements Parcelable{
    private String _id;
    private String casa;
    private String habitacion;
    private String name;
    private String estado;
    private String tipo;
    private String bateria;
    private Caracteristicas caracteristicas;

    public DispositivosAdapter() {
    }

    public DispositivosAdapter(String _id, String casa, String habitacion, String name, String estado, String tipo, String bateria, Caracteristicas caracteristicas) {
        this._id = _id;
        this.casa = casa;
        this.habitacion = habitacion;
        this.name = name;
        this.estado = estado;
        this.tipo = tipo;
        this.bateria = bateria;
        this.caracteristicas = caracteristicas;
    }

    public DispositivosAdapter(String _id, String casa, String habitacion, String name, String estado, String tipo, String bateria) {
        this._id = _id;
        this.casa = casa;
        this.habitacion = habitacion;
        this.name = name;
        this.estado = estado;
        this.tipo = tipo;
        this.bateria = bateria;
    }

    protected DispositivosAdapter(Parcel in) {
        _id = in.readString();
        casa = in.readString();
        habitacion = in.readString();
        name = in.readString();
        estado = in.readString();
        tipo = in.readString();
        bateria = in.readString();
        caracteristicas = in.readParcelable(Caracteristicas.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(casa);
        dest.writeString(habitacion);
        dest.writeString(name);
        dest.writeString(estado);
        dest.writeString(tipo);
        dest.writeString(bateria);
        dest.writeParcelable(caracteristicas, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DispositivosAdapter> CREATOR = new Creator<DispositivosAdapter>() {
        @Override
        public DispositivosAdapter createFromParcel(Parcel in) {
            return new DispositivosAdapter(in);
        }

        @Override
        public DispositivosAdapter[] newArray(int size) {
            return new DispositivosAdapter[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public Caracteristicas getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Caracteristicas caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DispositivosAdapter that = (DispositivosAdapter) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (casa != null ? !casa.equals(that.casa) : that.casa != null) return false;
        if (habitacion != null ? !habitacion.equals(that.habitacion) : that.habitacion != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;
        if (bateria != null ? !bateria.equals(that.bateria) : that.bateria != null) return false;
        return caracteristicas != null ? caracteristicas.equals(that.caracteristicas) : that.caracteristicas == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (casa != null ? casa.hashCode() : 0);
        result = 31 * result + (habitacion != null ? habitacion.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (bateria != null ? bateria.hashCode() : 0);
        result = 31 * result + (caracteristicas != null ? caracteristicas.hashCode() : 0);
        return result;
    }
}
