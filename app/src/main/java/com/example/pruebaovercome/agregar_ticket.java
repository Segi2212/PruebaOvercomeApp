package com.example.pruebaovercome;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class agregar_ticket extends Fragment {

    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;

    TextInputEditText etTitulo, etVersion, etDescripcion;
    Button btnAgregar;
    String Titulo, Fecha, Usuario, Departamento, Incidencia, Gravedad, Version, Descripcion;

    public agregar_ticket() {
        // Required empty public constructor
    }

    public static agregar_ticket newInstance(String param1, String param2) {
        agregar_ticket fragment = new agregar_ticket();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agregar_ticket, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTitulo = view.findViewById(R.id.etTitulo);
        etVersion = view.findViewById(R.id.etVersion);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = view.findViewById(R.id.autoCompleteTextView2);
        autoCompleteTextView3 = view.findViewById(R.id.autoCompleteTextView3);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        String[] Subjects = new String[]{"Soporte", "Desarrollo", "Atención a clientes"};
        ArrayAdapter<String> lista = new ArrayAdapter<String>(getActivity(), R.layout.list_item, Subjects);
        autoCompleteTextView.setAdapter(lista);

        String[] Subjects1 = new String[]{"Bug", "Feature"};
        ArrayAdapter<String> lista1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, Subjects1);
        autoCompleteTextView2.setAdapter(lista1);

        String[] Subjects2 = new String[]{"Alta", "Media", "Baja"};
        ArrayAdapter<String> lista2 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, Subjects2);
        autoCompleteTextView3.setAdapter(lista2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        SharedPreferences preferences = getContext().getSharedPreferences("datosUser", MODE_PRIVATE);


        Usuario = preferences.getString("Nombre", "");
        Departamento = preferences.getString("Departamento", "");
        Fecha = dateFormat.format(date);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Titulo = etTitulo.getText().toString();
                Departamento = autoCompleteTextView.getText().toString();
                Incidencia = autoCompleteTextView2.getText().toString();
                Gravedad = autoCompleteTextView3.getText().toString();
                Version = etVersion.getText().toString();
                Descripcion = etDescripcion.getText().toString();

                if (Titulo.isEmpty() || Fecha.isEmpty() || Usuario.isEmpty() || Departamento.equals("Departamento encargado") || Incidencia.equals("Tipo de incidencia") || Gravedad.equals("Gravedad")
                        || Version.isEmpty() || Descripcion.isEmpty()) {
                    Toast.makeText(getContext(), "Debe llenar todos los campos y seleccionar las opciones solicitadas", Toast.LENGTH_SHORT).show();
                } else {
                    agregarTicket();
                }
            }
        });
    }

    void agregarTicket() {
        StringRequest request = new StringRequest(Request.Method.POST, this.getString(R.string.URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        switch (response) {
                            case "101":
                                Intent refresh = new Intent(getContext(), MainActivity2.class);
                                startActivity(refresh);
                                getActivity().finish();
                                break;
                            default:
                                Toast.makeText(getContext(), "Error desconocido.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Hubo un error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion", "7");
                params.put("titulo", Titulo);
                params.put("fecha", Fecha);
                params.put("usuario", Usuario);
                params.put("departamento", Departamento);
                params.put("incidencia", Incidencia);
                params.put("gravedad", Gravedad);
                params.put("version", Version);
                params.put("descripcion", Descripcion);
                params.put("archivo", "");
                params.put("etapa", "Nuevo");
                params.put("archivado", "0");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

}