package com.example.rafa.tfg.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rafa.tfg.clases.Caracteristicas;
import com.example.rafa.tfg.clases.LogDispositivos;

import java.util.List;

/**
 * Created by Rafael on 04/03/2018.
 */

/**
 * Clase para los dispositivos
 * @author Rafael
 * */
public class DispositivosAdapter implements Parcelable{
    private String _id;
    private String casa;
    private String habitacion;
    private String name;
    private String estado;
    private String tipo;
    private String bateria;
    private Caracteristicas caracteristicas;
    private List<LogDispositivos> log;


    public DispositivosAdapter() {
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

    public DispositivosAdapter(String _id, String casa, String habitacion, String name, String estado, String tipo, String bateria, List<LogDispositivos> log) {
        this._id = _id;
        this.casa = casa;
        this.habitacion = habitacion;
        this.name = name;
        this.estado = estado;
        this.tipo = tipo;
        this.bateria = bateria;
        this.log = log;
    }

    public DispositivosAdapter(String _id, String casa, String habitacion, String name, String estado, String tipo, String bateria, Caracteristicas caracteristicas, List<LogDispositivos> log) {
        this._id = _id;
        this.casa = casa;
        this.habitacion = habitacion;
        this.name = name;
        this.estado = estado;
        this.tipo = tipo;
        this.bateria = bateria;
        this.caracteristicas = caracteristicas;
        this.log = log;
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
        log = in.createTypedArrayList(LogDispositivos.CREATOR);
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
        dest.writeTypedList(log);
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

    public List<LogDispositivos> getLog() {
        return log;
    }

    public void setLog(List<LogDispositivos> log) {
        this.log = log;
    }
}
