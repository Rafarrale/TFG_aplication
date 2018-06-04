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

public class eliminaKeysAdapter extends RecyclerView.Adapter<eliminaKeysAdapter.ViewHolder> {
    private eliminaKeysAdapter.OnItemClickListener mOnItemClickListener;
    private List<CasaPass> mItems;
    private Context mContext;

    public eliminaKeysAdapter() {
    }

    public eliminaKeysAdapter(List<CasaPass> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    public interface OnItemClickListener{
        void onItemClick(CasaPass clickedAppointment);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.items_key_elimina, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(eliminaKeysAdapter.ViewHolder holder, int position) {
        CasaPass appointment = mItems.get(position);

        StringBuilder id = new StringBuilder();
        id.append(Constantes.KEY);
        id.append(Constantes.DOS_PUNTOS_ESPACIO);
        id.append(appointment.getKey());

        holder.key.setText(id);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView key;

        public ViewHolder(View itemView) {
            super(itemView);

            key = itemView.findViewById(R.id.tvKeyElimina);

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
