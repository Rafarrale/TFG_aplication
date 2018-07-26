package com.example.rafa.tfg.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.fragments.CamaraFragment.OnListFragmentInteractionListener;

import java.util.List;


public class MyCamaraRecyclerViewAdapter extends RecyclerView.Adapter<MyCamaraRecyclerViewAdapter.ViewHolder> {

    private final List<DispositivosAdapter> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyCamaraRecyclerViewAdapter(List<DispositivosAdapter> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_camara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        DispositivosAdapter appointment = holder.mItem = mValues.get(position);

        View statusIndicator = holder.statusIndicator;

        if(appointment != null) {
            statusIndicator.setBackgroundResource(R.color.colorPrimary);
        }

        StringBuilder id = new StringBuilder();
        id.append(Constantes.ID);
        id.append(Constantes.DOS_PUNTOS_ESPACIO);
        id.append(appointment.get_id());

        StringBuilder nom = new StringBuilder();
        nom.append(Constantes.NOMBRE);
        nom.append(Constantes.DOS_PUNTOS_ESPACIO);
        nom.append(appointment.getName());


        StringBuilder hab = new StringBuilder();
        hab.append(Constantes.HABITACION);
        hab.append(Constantes.DOS_PUNTOS_ESPACIO);
        hab.append(appointment.getHabitacion());

        StringBuilder tipo = new StringBuilder();
        tipo.append(Constantes.TIPO);
        tipo.append(Constantes.DOS_PUNTOS_ESPACIO);
        tipo.append(appointment.getTipo());

        StringBuilder estado = new StringBuilder();
        estado.append(Constantes.ESTADO);
        estado.append(Constantes.DOS_PUNTOS_ESPACIO);
        estado.append(appointment.getEstado());

        StringBuilder bat = new StringBuilder();
        bat.append(Constantes.BATERIA);
        bat.append(Constantes.DOS_PUNTOS_ESPACIO);
        bat.append(appointment.getBateria());

        holder.mIdView.setText(id);
        holder.mNomView.setText(nom);
        holder.mHabView.setText(hab);
        holder.mTipoView.setText(tipo);
        holder.mEstadoView.setText(estado);
        holder.mBatView.setText(bat);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final View statusIndicator;
        public final TextView mIdView;
        public final TextView mNomView;
        public final TextView mHabView;
        public final TextView mTipoView;
        public final TextView mEstadoView;
        public final TextView mBatView;
        public DispositivosAdapter mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            statusIndicator = view.findViewById(R.id.indicator_dispositivo_status);
            mIdView = (TextView) view.findViewById(R.id.tvIdCamara);
            mNomView = (TextView) view.findViewById(R.id.tvNomCamara);
            mHabView = (TextView) view.findViewById(R.id.tvDispHabitCamara);
            mTipoView = (TextView) view.findViewById(R.id.tvDispTipoCamara);
            mEstadoView = (TextView) view.findViewById(R.id.tvDispEstadoCamara);
            mBatView= (TextView) view.findViewById(R.id.tvDispBatCamara);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNomView.getText() + "'";
        }
    }
}
