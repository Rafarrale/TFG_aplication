package com.example.rafa.tfg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.LogDispositivos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 21/03/2018.
 */

public class DispositivosLogDataAdapter extends RecyclerView.Adapter<DispositivosLogDataAdapter.ViewHolder>{
    private List<LogDispositivos> mItems;

    private Context mContext;

    private DispositivosLogDataAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(LogDispositivos clickedAppointment);

    }

    public DispositivosLogDataAdapter(Context context, List<LogDispositivos> items) {
        mItems = items;
        mContext = context;
    }

    public DispositivosLogDataAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(DispositivosLogDataAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void swapItems(List<LogDispositivos> appointments) {
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
        View view = layoutInflater.inflate(R.layout.items_dispositivos_log, parent, false);
        return new DispositivosLogDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DispositivosLogDataAdapter.ViewHolder holder, int position) {
        LogDispositivos appointment = mItems.get(position);

        StringBuilder fecha = new StringBuilder();
        fecha.append(Constantes.FECHA);
        fecha.append(Constantes.DOS_PUNTOS_ESPACIO);
        fecha.append(appointment.getFecha());

        StringBuilder hora = new StringBuilder();
        hora.append(Constantes.HORA);
        hora.append(Constantes.DOS_PUNTOS_ESPACIO);
        hora.append(appointment.getHora());

        StringBuilder suceso = new StringBuilder();
        suceso.append(appointment.getSuceso());


        holder.fecha.setText(fecha);
        holder.hora.setText(hora);
        holder.suceso.setText(suceso);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView fecha;
        public TextView hora;
        public TextView suceso;

        public ViewHolder(View itemView) {
            super(itemView);

            fecha = itemView.findViewById(R.id.tvNomDispositivoFechaTodos);
            hora = itemView.findViewById(R.id.tvIdDispositivoHoraTodos);
            suceso = itemView.findViewById(R.id.tvIdDispositivoSucesoTodos);

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


