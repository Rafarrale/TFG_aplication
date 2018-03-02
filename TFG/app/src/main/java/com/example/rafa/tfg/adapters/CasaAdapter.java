package com.example.rafa.tfg.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rafa.tfg.R;

import java.util.ArrayList;

/**
 * Created by Rafael on 02/03/2018.
 */

public class CasaAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<String> items;

    public CasaAdapter(Activity activity, ArrayList<String> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.lista_casas,null);
        }

        String dir = items.get(position);

        TextView title = v.findViewById(R.id.tvCasas);
        title.setText(dir);

        return v;


    }
}
