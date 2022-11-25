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

public class AdaptadorTicketsProceso extends ArrayAdapter<TicketsProceso> {
    Context context;
    ArrayList<TicketsProceso> ticketsProcesoArrayList;

    public AdaptadorTicketsProceso(@NonNull Context context, int i, ArrayList<TicketsProceso> ticketsProcesoArrayList) {
        super(context, R.layout.cardview_tickets, ticketsProcesoArrayList);

        this.context = context;
        this.ticketsProcesoArrayList = ticketsProcesoArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tickets, null, true);

        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvInsidencia = view.findViewById(R.id.tvInsidencia);
        TextView tvGravedad = view.findViewById(R.id.tvGravedad);

        tvId.setText(ticketsProcesoArrayList.get(position).getID());
        tvTitulo.setText(ticketsProcesoArrayList.get(position).getTitulo());
        tvInsidencia.setText(ticketsProcesoArrayList.get(position).getIncidencia());
        tvGravedad.setText(ticketsProcesoArrayList.get(position).getGravedad());
        return view;
    }
}
