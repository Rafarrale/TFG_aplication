package com.example.rafa.tfg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rafa.tfg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 13/03/2018.
 */

public class usuDataAdapter extends RecyclerView.Adapter<usuDataAdapter.usuDataAdapterViewHolder> {

    private ArrayList<usuAdapter> datos;

    public usuDataAdapter(ArrayList<usuAdapter> datos) {
        this.datos = datos;
    }

    public static class usuDataAdapterViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private TextView txtSubtitulo;

        public usuDataAdapterViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.text_medical_service);
            txtSubtitulo = (TextView)itemView.findViewById(R.id.text_doctor_name);
        }

        public void bindTitular(usuAdapter t) {
            txtTitulo.setText(t.getNombre());
            txtSubtitulo.setText(t.getApellidos());
        }
    }

    @Override
    public usuDataAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_usuarios, viewGroup, false);

        usuDataAdapterViewHolder tvh = new usuDataAdapterViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(usuDataAdapterViewHolder viewHolder, int pos) {
        usuAdapter item = datos.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
}