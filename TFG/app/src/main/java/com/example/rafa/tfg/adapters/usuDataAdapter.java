package com.example.rafa.tfg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.rafa.tfg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 13/03/2018.
 */

    public class usuDataAdapter extends RecyclerView.Adapter<usuDataAdapter.ViewHolder> {

        private List<usuAdapter> mItems;

        private Context mContext;

        private OnItemClickListener mOnItemClickListener;

        public interface OnItemClickListener {

            void onItemClick(usuAdapter clickedAppointment);

            void onCancelAppointment(usuAdapter canceledAppointment);

        }

        public usuDataAdapter(Context context, List<usuAdapter> items) {
            mItems = items;
            mContext = context;
        }

        public OnItemClickListener getOnItemClickListener() {
            return mOnItemClickListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public void swapItems(List<usuAdapter> appointments) {
            if (appointments == null) {
                mItems = new ArrayList<>(0);
            } else {
                mItems = appointments;
            }
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.items_usuarios, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            usuAdapter appointment = mItems.get(position);

            View statusIndicator = holder.statusIndicator;
/*
            // estado: se colorea indicador según el estado
            switch (appointment.getStatus()) {
                case "Activa":
                    // mostrar botón
                    holder.cancelButton.setVisibility(View.VISIBLE);
                    statusIndicator.setBackgroundResource(R.color.activeStatus);
                    break;
                case "Cumplida":
                    // ocultar botón
                    holder.cancelButton.setVisibility(View.GONE);
                    statusIndicator.setBackgroundResource(R.color.completedStatus);
                    break;
                case "Cancelada":
                    // ocultar botón
                    holder.cancelButton.setVisibility(View.GONE);
                    statusIndicator.setBackgroundResource(R.color.cancelledStatus);
                    break;
            }
*/
            holder.date.setText(appointment.getNombre());
            holder.service.setText(appointment.getApellidos());
            //holder.doctor.setText(appointment.getDoctor());
            //holder.medicalCenter.setText(appointment.getMedicalCenter());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView date;
            public TextView service;
            public TextView doctor;
            public TextView medicalCenter;
            public Button cancelButton;
            public View statusIndicator;

            public ViewHolder(View itemView) {
                super(itemView);

                statusIndicator = itemView.findViewById(R.id.indicator_appointment_status);
                date = (TextView) itemView.findViewById(R.id.text_appointment_date);
                service = (TextView) itemView.findViewById(R.id.text_medical_service);
                doctor = (TextView) itemView.findViewById(R.id.text_doctor_name);
                medicalCenter = (TextView) itemView.findViewById(R.id.text_medical_center);
                cancelButton = (Button) itemView.findViewById(R.id.button_cancel_appointment);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnItemClickListener.onCancelAppointment(mItems.get(position));
                        }
                    }
                });
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