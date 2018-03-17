package com.example.rafa.tfg.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.NavPrincActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.UsuariosActivity;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.startActivity;

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

            void onModAppointment(usuAdapter ModAppointment);

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

            if(appointment != null) {
                // estado: se colorea indicador según el estado
                switch (appointment.getAdmin()) {
                    case "si":
                        statusIndicator.setBackgroundResource(R.color.deeppurple);
                        break;
                    case "no":
                        statusIndicator.setBackgroundResource(R.color.colorPrimary);
                        break;
                }
            }

            StringBuilder usuario = new StringBuilder();
            usuario.append(Constantes.USUARIO);
            usuario.append(Constantes.DOS_PUNTOS_ESPACIO);
            usuario.append(appointment.getUser());

            StringBuilder nomApell = new StringBuilder();
            nomApell.append(Constantes.NOMBRE);
            nomApell.append(Constantes.DOS_PUNTOS_ESPACIO);
            nomApell.append(appointment.getNombre());
            nomApell.append(Constantes.ESPACIO);
            nomApell.append(appointment.getApellidos());

            StringBuilder pass = new StringBuilder();
            pass.append(Constantes.PASSWORD);
            pass.append(Constantes.DOS_PUNTOS_ESPACIO);
            pass.append(appointment.getPass());

            StringBuilder admin = new StringBuilder();
            admin.append(Constantes.ADMIN);
            admin.append(Constantes.DOS_PUNTOS_ESPACIO);
            admin.append(appointment.getAdmin());

            StringBuilder email = new StringBuilder();
            email.append(Constantes.EMAIL);
            email.append(Constantes.DOS_PUNTOS_ESPACIO);
            email.append(appointment.getEmail());

            holder.usuario.setText(usuario);
            holder.nomApell.setText(nomApell);
            holder.password.setText(pass);
            holder.admin.setText(admin);
            holder.email.setText(email);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView usuario;
            public TextView nomApell;
            public TextView password;
            public TextView admin;
            public TextView email;
            public Button deleteButton;
            public Button modButton;
            public View statusIndicator;

            public ViewHolder(View itemView) {
                super(itemView);

                statusIndicator = itemView.findViewById(R.id.indicator_appointment_status);
                usuario = itemView.findViewById(R.id.tvUsuarioTodos);
                nomApell = (TextView) itemView.findViewById(R.id.tvNombreUsuariosTodos);
                password = (TextView) itemView.findViewById(R.id.tvPasswordUsuariosTodos);
                admin = (TextView) itemView.findViewById(R.id.tvAdminUsuariosTodos);
                email = (TextView) itemView.findViewById(R.id.tvEmailUsuariosTodos);
                deleteButton = (Button) itemView.findViewById(R.id.btDeleteUsuariosTodos);
                modButton = (Button) itemView.findViewById(R.id.btModUsuariosTodos);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnItemClickListener.onCancelAppointment(mItems.get(position));
                        }
                    }
                });

                // Este metodo a sido desarrollado en la activity
                modButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnItemClickListener.onModAppointment(mItems.get(position));
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