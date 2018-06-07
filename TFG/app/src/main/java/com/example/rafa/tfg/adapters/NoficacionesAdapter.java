package com.example.rafa.tfg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.clases.Constantes;

import java.util.List;

public class NoficacionesAdapter extends RecyclerView.Adapter<NoficacionesAdapter.ViewHolder>{

    private NoficacionesAdapter.OnItemClickListener mOnItemClickListener;
    private List<NotificacionDispHora> mItems;
    private Context mContext;

    public NoficacionesAdapter() {
    }

    public NoficacionesAdapter(List<NotificacionDispHora> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    public interface OnItemClickListener{
        void onItemClick(NotificacionDispHora clickedAppointment);
    }

    public void setOnItemClickListener(NoficacionesAdapter.OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public NoficacionesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.items_notificaciones, parent, false);
        return new NoficacionesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoficacionesAdapter.ViewHolder holder, int position) {
        NotificacionDispHora appointment = mItems.get(position);


        StringBuilder nombre = new StringBuilder();
        nombre.append(Constantes.NOMBRE);
        nombre.append(Constantes.DOS_PUNTOS_ESPACIO);
        nombre.append(appointment.getName());

        holder.nombre.setText(nombre);

        StringBuilder hora = new StringBuilder();
        hora.append(Constantes.HORA);
        hora.append(Constantes.DOS_PUNTOS_ESPACIO);
        hora.append(appointment.getHora());

        holder.hora.setText(hora);

        StringBuilder estado = new StringBuilder();
        estado.append(Constantes.ESTADO);
        estado.append(Constantes.DOS_PUNTOS_ESPACIO);
        estado.append(appointment.getEstado());

        holder.estado.setText(estado);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombre;
        public TextView hora;
        public TextView estado;

        public ViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tvNomDispositivoNotificacion);
            hora = itemView.findViewById(R.id.tvIdHoraNotificacionDisp);
            estado = itemView.findViewById(R.id.tvDispEstadoNotificacion);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(mItems.get(position));
            }
        }
    }
}

