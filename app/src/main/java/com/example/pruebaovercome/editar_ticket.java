package com.example.pruebaovercome;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pruebaovercome.ClasesYAdaptadores.Departamentos;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class editar_ticket extends Fragment implements View.OnClickListener {

    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;

    TextInputEditText etTitulo, etVersion, etDescripcion;
    Button btnActualizar;

    public editar_ticket() {
        // Required empty public constructor
    }

    public static editar_ticket newInstance(String param1, String param2) {
        editar_ticket fragment = new editar_ticket();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction fragmentTransactionn = getFragmentManager().beginTransaction();
                fragmentTransactionn.replace(R.id.fragmentContainerView, new tickets()).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        getParentFragmentManager().setFragmentResultListener("ticket1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String ID = result.getString("idt");
                buscarTicket(ID);

                btnActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        actualizarTicket(ID);
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTitulo = view.findViewById(R.id.etTitulo);
        etVersion = view.findViewById(R.id.etVersion);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = view.findViewById(R.id.autoCompleteTextView2);
        autoCompleteTextView3 = view.findViewById(R.id.autoCompleteTextView3);
        btnActualizar = view.findViewById(R.id.btnActualizar);

        btnActualizar.setOnClickListener(this);

        String[] Subjects = new String[]{"Soporte", "Desarrollo", "Atención a clientes"};
        ArrayAdapter<String> lista = new ArrayAdapter<String>(getActivity(), R.layout.list_item, Subjects);
        autoCompleteTextView.setAdapter(lista);

        String[] Subjects1 = new String[]{"Bug", "Feature"};
        ArrayAdapter<String> lista1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, Subjects1);
        autoCompleteTextView2.setAdapter(lista1);

        String[] Subjects2 = new String[]{"Alta", "Media", "Baja"};
        ArrayAdapter<String> lista2 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, Subjects2);
        autoCompleteTextView3.setAdapter(lista2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnActualizar:
                break;
        }
    }

    void buscarTicket(String id) {
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                String Titulo = object.getString("Titulo");
                                String Departamento = object.getString("Departamento");
                                String Incidencia = object.getString("Incidencia");
                                String Gravedad = object.getString("Gravedad");
                                String Version = object.getString("Version");
                                String Descripcion = object.getString("Descripcion");
                                String Etapa = object.getString("Etapa");


                                etTitulo.setText(Titulo);
                                switch (Departamento) {
                                    case "Soporte":
                                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(1).toString(), false);
                                        break;
                                    case "Desarrollo":
                                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(2).toString(), false);
                                        break;
                                    case "Atención a clientes":
                                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(3).toString(), false);
                                        break;
                                }
                                switch (Incidencia) {
                                    case "Bug":
                                        autoCompleteTextView2.setText(autoCompleteTextView2.getAdapter().getItem(1).toString(), false);
                                        break;
                                    case "Feature":
                                        autoCompleteTextView2.setText(autoCompleteTextView2.getAdapter().getItem(2).toString(), false);
                                        break;
                                }
                                switch (Gravedad) {
                                    case "Alta":
                                        autoCompleteTextView3.setText(autoCompleteTextView3.getAdapter().getItem(1).toString(), false);
                                        break;
                                    case "Media":
                                        autoCompleteTextView3.setText(autoCompleteTextView3.getAdapter().getItem(2).toString(), false);
                                        break;
                                    case "Baja":
                                        autoCompleteTextView3.setText(autoCompleteTextView3.getAdapter().getItem(3).toString(), false);
                                        break;
                                }
                                etVersion.setText(Version);
                                etDescripcion.setText(Descripcion);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion", "8");
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    void actualizarTicket(String id) {
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
                params.put("opcion", "9");
                params.put("id", id);
                params.put("titulo", etTitulo.getText().toString());
                params.put("departamento", autoCompleteTextView.getText().toString());
                params.put("incidencia", autoCompleteTextView2.getText().toString());
                params.put("gravedad", autoCompleteTextView3.getText().toString());
                params.put("version", etVersion.getText().toString());
                params.put("descripcion", etDescripcion.getText().toString());
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