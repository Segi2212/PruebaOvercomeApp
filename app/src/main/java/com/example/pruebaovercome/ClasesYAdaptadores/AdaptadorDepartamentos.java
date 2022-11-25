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

public class AdaptadorDepartamentos  extends ArrayAdapter<Departamentos> {
    Context context;
    ArrayList<Departamentos> departamentosArrayList;

    public AdaptadorDepartamentos(@NonNull Context context, int i, ArrayList<Departamentos> departamentosArrayList) {
        super(context, R.layout.cardview_departamento, departamentosArrayList);

        this.context = context;
        this.departamentosArrayList = departamentosArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_departamento, null, true);

        TextView tvDepartamento = view.findViewById(R.id.tvDepartamento);

        tvDepartamento.setText(departamentosArrayList.get(position).getDepartamento());

        return view;
    }

}
