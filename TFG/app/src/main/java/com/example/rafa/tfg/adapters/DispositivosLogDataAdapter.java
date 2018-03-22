package com.example.rafa.tfg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.clases.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 21/03/2018.
 */

public class DispositivosLogDataAdapter extends RecyclerView.Adapter<DispositivosLogDataAdapter.ViewHolder>{
    private List<DispositivosAdapter> mItems;

    private Context mContext;

    private DispositivosLogDataAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(DispositivosAdapter clickedAppointment);

    }

    public DispositivosLogDataAdapter(Context context, List<DispositivosAdapter> items) {
        mItems = items;
        mContext = context;
    }

    public DispositivosLogDataAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(DispositivosLogDataAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void swapItems(List<DispositivosAdapter> appointments) {
        if (appointments == null) {
            mItems = new ArrayList<>(0);
        } else {
            mItems = appointments;
        }
        notifyDataSetChanged();
    }

    @Override
    public DispositivosLogDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.items_dispositivos, parent, false);
        return new DispositivosLogDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DispositivosLogDataAdapter.ViewHolder holder, int position) {
        DispositivosAdapter appointment = mItems.get(position);

        StringBuilder id = new StringBuilder();
        id.append(Constantes.ID);
        id.append(Constantes.DOS_PUNTOS_ESPACIO);
        id.append(appointment.get_id());

        StringBuilder habit = new StringBuilder();
        habit.append(Constantes.HABITACION);
        habit.append(Constantes.DOS_PUNTOS_ESPACIO);
        habit.append(appointment.getHabitacion());

        StringBuilder nom = new StringBuilder();
        nom.append(Constantes.NOMBRE);
        nom.append(Constantes.DOS_PUNTOS_ESPACIO);
        nom.append(appointment.getName());

        StringBuilder tipo = new StringBuilder();
        tipo.append(Constantes.TIPO);
        tipo.append(Constantes.DOS_PUNTOS_ESPACIO);
        tipo.append(appointment.getTipo());

        StringBuilder estado = new StringBuilder();
        estado.append(Constantes.ESTADO);
        estado.append(Constantes.DOS_PUNTOS_ESPACIO);
        estado.append(appointment.getEstado());

        StringBuilder bateria = new StringBuilder();
        bateria.append(Constantes.BATERIA);
        bateria.append(Constantes.DOS_PUNTOS_ESPACIO);
        bateria.append(appointment.getBateria());

        holder.id.setText(id);
        holder.nom.setText(nom);
        holder.habit.setText(habit);
        holder.tipo.setText(tipo);
        holder.estado.setText(estado);
        holder.bateria.setText(bateria);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView id;
        public TextView nom;
        public TextView habit;
        public TextView tipo;
        public TextView estado;
        public TextView bateria;
        public View statusIndicator;

        public ViewHolder(View itemView) {
            super(itemView);

            statusIndicator = itemView.findViewById(R.id.indicator_dispositivo_status);
            id = itemView.findViewById(R.id.tvIdDispositivoTodos);
            nom = (TextView) itemView.findViewById(R.id.tvNomDispositivoTodos);
            habit = (TextView) itemView.findViewById(R.id.tvDispHabitTodos);
            tipo = (TextView) itemView.findViewById(R.id.tvDispTipoTodos);
            estado = (TextView) itemView.findViewById(R.id.tvDispEstadoTodos);
            bateria = (TextView) itemView.findViewById(R.id.tvDispBatTodos);

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


