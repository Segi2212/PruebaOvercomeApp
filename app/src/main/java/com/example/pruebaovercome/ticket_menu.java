package com.example.pruebaovercome;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebaovercome.ClasesYAdaptadores.TicketsNuevos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ticket_menu extends Fragment implements View.OnClickListener {

    Button btnEditar, btnCancelar, btnAceptar, btnArchivar, btnAceptarInci, btnAbandonarInci;

    public ticket_menu() {
        // Required empty public constructor
    }

    public static ticket_menu newInstance(String param1, String param2) {
        ticket_menu fragment = new ticket_menu();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEditar = view.findViewById(R.id.btnEditar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        btnArchivar = view.findViewById(R.id.btnArchivar);
        btnAceptarInci = view.findViewById(R.id.btnAceptarInci);
        btnAbandonarInci = view.findViewById(R.id.btnAbandonarInci);

        getParentFragmentManager().setFragmentResultListener("ticket", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String ID = result.getString("id", "");
                String btn = result.getString("btn", "");

                switch (btn){
                    case "depIgual":
                        btnAceptarInci.setVisibility(View.VISIBLE);
                        break;
                    case "depIgualProc":
                        btnAbandonarInci.setVisibility(View.VISIBLE);
                        break;
                }

                btnEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("idt", ID);
                        getParentFragmentManager().setFragmentResult("ticket1", bundle);
                        FragmentTransaction fragmentTransactionn = getFragmentManager().beginTransaction();
                        fragmentTransactionn.replace(R.id.fragmentContainerView, new editar_ticket()).commit();
                    }
                });

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        archivarTicket(ID);
                    }
                });

                btnAceptarInci.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aceptarTicket(ID);
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnEditar.setVisibility(View.VISIBLE);

                        if(btn.equals("depIgual")){btnAceptarInci.setVisibility(View.VISIBLE);}
                        btnCancelar.setVisibility(View.GONE);
                        btnAceptar.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnArchivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEditar.setVisibility(View.GONE);
                btnAceptarInci.setVisibility(View.GONE);

                btnCancelar.setVisibility(View.VISIBLE);
                btnAceptar.setVisibility(View.VISIBLE);
            }
        });
    }

    /*void buscarTicket1(String id) {
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                String depa = object.getString("Departamento");

                                if(Departamento.equals(depa)){

                                    btnAceptarInci.setVisibility(View.VISIBLE);
                                }else{
                                    btnAceptarInci.setVisibility(View.GONE);
                                }
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
    }*/

    void archivarTicket(String ID){
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
                params.put("opcion", "10");
                params.put("id", ID);
                params.put("archivado", "1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    void aceptarTicket(String ID){
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
                params.put("opcion", "11");
                params.put("id", ID);
                params.put("etapa", "Proceso");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    @Override
    public void onClick(View view) {

    }
}