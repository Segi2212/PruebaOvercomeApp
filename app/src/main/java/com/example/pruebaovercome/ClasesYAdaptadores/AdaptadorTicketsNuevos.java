package com.example.pruebaovercome.ClasesYAdaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pruebaovercome.R;

import java.util.ArrayList;

public class AdaptadorTicketsNuevos extends ArrayAdapter<TicketsNuevos> {
    Context context;
    ArrayList<TicketsNuevos> ticketsNuevosArrayList;

    public AdaptadorTicketsNuevos(@NonNull Context context, int i, ArrayList<TicketsNuevos> ticketsNuevosArrayList) {
        super(context, R.layout.cardview_tickets, ticketsNuevosArrayList);

        this.context = context;
        this.ticketsNuevosArrayList = ticketsNuevosArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tickets, null, true);

        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvInsidencia = view.findViewById(R.id.tvInsidencia);
        TextView tvGravedad = view.findViewById(R.id.tvGravedad);

        tvId.setText(ticketsNuevosArrayList.get(position).getID());
        tvTitulo.setText(ticketsNuevosArrayList.get(position).getTitulo());
        tvInsidencia.setText(ticketsNuevosArrayList.get(position).getIncidencia());
        tvGravedad.setText(ticketsNuevosArrayList.get(position).getGravedad());
        return view;
    }
}
