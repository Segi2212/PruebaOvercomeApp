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

public class AdaptadorTicketsAtendidos extends ArrayAdapter<TicketsAtendidos> {
    Context context;
    ArrayList<TicketsAtendidos> ticketsAtendidosArrayList;

    public AdaptadorTicketsAtendidos(@NonNull Context context, int i, ArrayList<TicketsAtendidos> ticketsAtendidosArrayList) {
        super(context, R.layout.cardview_tickets, ticketsAtendidosArrayList);

        this.context = context;
        this.ticketsAtendidosArrayList = ticketsAtendidosArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tickets, null, true);

        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvInsidencia = view.findViewById(R.id.tvInsidencia);
        TextView tvGravedad = view.findViewById(R.id.tvGravedad);

        tvId.setText(ticketsAtendidosArrayList.get(position).getID());
        tvTitulo.setText(ticketsAtendidosArrayList.get(position).getTitulo());
        tvInsidencia.setText(ticketsAtendidosArrayList.get(position).getIncidencia());
        tvGravedad.setText(ticketsAtendidosArrayList.get(position).getGravedad());
        return view;
    }
}
