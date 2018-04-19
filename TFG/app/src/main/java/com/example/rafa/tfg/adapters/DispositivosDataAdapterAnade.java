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
 * Created by Rafael on 26/03/2018.
 */

public class DispositivosDataAdapterAnade extends RecyclerView.Adapter<DispositivosDataAdapterAnade.ViewHolder> {
    private List<DispositivosAdapter> mItems;

    private Context mContext;

    private DispositivosDataAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(DispositivosAdapter clickedAppointment);

    }

    public DispositivosDataAdapterAnade(Context context, List<DispositivosAdapter> items) {
        mItems = items;
        mContext = context;
    }

    public DispositivosDataAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(DispositivosDataAdapter.OnItemClickListener onItemClickListener) {
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
    public DispositivosDataAdapterAnade.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.items_dispositivos, parent, false);
        return new DispositivosDataAdapterAnade.ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        DispositivosAdapter appointment = mItems.get(position);
        View statusIndicator = holder.statusIndicator;
        try {
            int aux = Integer.parseInt(appointment.getBateria());
            // estado: se colorea indicador según el estado

            if (aux >= Constantes.VALUE_50) {
                statusIndicator.setBackgroundResource(R.color.deepVerde);
            }
            if (aux < Constantes.VALUE_50 && aux >= Constantes.VALUE_30){
                statusIndicator.setBackgroundResource(R.color.deepOrange);
            }
            if(aux < Constantes.VALUE_30 && aux >= Constantes.VALUE_10) {
                statusIndicator.setBackgroundResource(R.color.deepRedAlerta);
            }
            if(aux < Constantes.VALUE_10) {
                statusIndicator.setBackgroundResource(R.color.deepNegro);
            }

        }catch(NumberFormatException e){

        }

        StringBuilder id = new StringBuilder();
        StringBuilder habit = new StringBuilder();
        StringBuilder nom = new StringBuilder();
        StringBuilder tipo = new StringBuilder();
        StringBuilder estado = new StringBuilder();
        StringBuilder bateria = new StringBuilder();


        id.append(Constantes.ID);
        id.append(Constantes.DOS_PUNTOS_ESPACIO);
        id.append(appointment.get_id());

        if(appointment.getHabitacion() != null) {
            habit.append(Constantes.HABITACION);
            habit.append(Constantes.DOS_PUNTOS_ESPACIO);
            habit.append(appointment.getHabitacion());
        }else{
            habit.append(Constantes.HABITACION);
            habit.append(Constantes.DOS_PUNTOS_ESPACIO);
            habit.append(Constantes.POR_ASIGNAR);
        }

        if(appointment.getName() != null) {
            nom.append(Constantes.NOMBRE);
            nom.append(Constantes.DOS_PUNTOS_ESPACIO);
            nom.append(appointment.getName());
        }else{
            nom.append(Constantes.NOMBRE);
            nom.append(Constantes.DOS_PUNTOS_ESPACIO);
            nom.append(Constantes.POR_ASIGNAR);
        }

        if(appointment.getTipo() != null) {
            tipo.append(Constantes.TIPO);
            tipo.append(Constantes.DOS_PUNTOS_ESPACIO);
            tipo.append(appointment.getTipo());
        }else{
            tipo.append(Constantes.TIPO);
            tipo.append(Constantes.DOS_PUNTOS_ESPACIO);
            tipo.append(Constantes.POR_DETERMINAR);
        }
        if(appointment.getEstado() != null){
            estado.append(Constantes.ESTADO);
            estado.append(Constantes.DOS_PUNTOS_ESPACIO);
            estado.append(appointment.getEstado());
        }else{
            estado.append(Constantes.ESTADO);
            estado.append(Constantes.DOS_PUNTOS_ESPACIO);
            estado.append(Constantes.POR_DETERMINAR);
        }

        if(appointment.getBateria() != null){
            bateria.append(Constantes.BATERIA);
            bateria.append(Constantes.DOS_PUNTOS_ESPACIO);
            bateria.append(appointment.getBateria());
        }else{
            bateria.append(Constantes.BATERIA);
            bateria.append(Constantes.DOS_PUNTOS_ESPACIO);
            bateria.append(Constantes.POR_DETERMINAR);
        }

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
            id = (TextView)itemView.findViewById(R.id.tvIdDispositivoTodos);
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
