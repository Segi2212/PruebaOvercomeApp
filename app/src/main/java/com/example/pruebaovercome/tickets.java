package com.example.pruebaovercome;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebaovercome.ClasesYAdaptadores.AdaptadorTicketsAtendidos;
import com.example.pruebaovercome.ClasesYAdaptadores.AdaptadorTicketsNuevos;
import com.example.pruebaovercome.ClasesYAdaptadores.AdaptadorTicketsProceso;
import com.example.pruebaovercome.ClasesYAdaptadores.TicketsAtendidos;
import com.example.pruebaovercome.ClasesYAdaptadores.TicketsNuevos;
import com.example.pruebaovercome.ClasesYAdaptadores.TicketsProceso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tickets extends Fragment {

    SwipeRefreshLayout srNuevos, srProceso, srAtendidos;
    ListView lvNuevos, lvProceso, lvAtendidos;

    public static ArrayList<TicketsNuevos> ticketsNuevosArrayList = new ArrayList<>();
    public static ArrayList<TicketsProceso> ticketsProcesoArrayList = new ArrayList<>();
    public static ArrayList<TicketsAtendidos> ticketsAtendidosArrayList = new ArrayList<>();
    AdaptadorTicketsNuevos adaptadorTicketsNuevos;
    AdaptadorTicketsProceso adaptadorTicketsProceso;
    AdaptadorTicketsAtendidos adaptadorTicketsAtendidos;
    TicketsNuevos ticketsNuevos;
    TicketsProceso ticketsProceso;
    TicketsAtendidos ticketsAtendidos;

    public tickets() {
        // Required empty public constructor
    }

    public static tickets newInstance(String param1, String param2) {
        tickets fragment = new tickets();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        srNuevos = view.findViewById(R.id.srNuevos);
        srProceso = view.findViewById(R.id.srProceso);
        srAtendidos = view.findViewById(R.id.srAtendidos);
        lvNuevos = view.findViewById(R.id.lvNuevos);
        lvProceso = view.findViewById(R.id.lvProceso);
        lvAtendidos = view.findViewById(R.id.lvAtendidos);

        srNuevos.setEnabled(false);
        srProceso.setEnabled(false);
        srAtendidos.setEnabled(false);

        adaptadorTicketsNuevos = new AdaptadorTicketsNuevos(getContext(), 0, ticketsNuevosArrayList);
        adaptadorTicketsProceso = new AdaptadorTicketsProceso(getContext(), 0, ticketsProcesoArrayList);
        adaptadorTicketsAtendidos = new AdaptadorTicketsAtendidos(getContext(), 0, ticketsAtendidosArrayList);

        lvNuevos.setAdapter(adaptadorTicketsNuevos);
        lvProceso.setAdapter(adaptadorTicketsProceso);
        lvAtendidos.setAdapter(adaptadorTicketsAtendidos);

        SharedPreferences preferences = getActivity().getSharedPreferences("datosUser", MODE_PRIVATE);
        String Departamento = preferences.getString("Departamento", "");

        lvNuevos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                Bundle bundle = new Bundle();
                TicketsNuevos selectd = (TicketsNuevos) lvNuevos.getItemAtPosition(position);

                String dep = selectd.getDepartamento();
                if (Departamento.equals(dep)){
                    bundle.putString("btn", "depIgual");
                    bundle.putString("id", selectd.getID());

                    getParentFragmentManager().setFragmentResult("ticket", bundle);
                    getFragmentManager().beginTransaction().add(R.id.fragmentContainerView, new ticket_menu()).commit();
                }else{
                    Toast.makeText(getContext(), "Solo el departamento a cargo puede editar este ticket", Toast.LENGTH_SHORT).show();
                }

                lvDisable();
            }
        });

        lvProceso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                Bundle bundle = new Bundle();
                TicketsProceso selectd = (TicketsProceso) lvProceso.getItemAtPosition(position);

                bundle.putString("btn", "depIgualProc");
                bundle.putString("id", selectd.getID());

                getParentFragmentManager().setFragmentResult("ticket", bundle);
                getFragmentManager().beginTransaction().add(R.id.fragmentContainerView, new ticket_menu()).commit();

                lvDisable();
            }
        });

        lvAtendidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                Bundle bundle = new Bundle();

                lvNuevos.setEnabled(false);
                lvProceso.setEnabled(false);
                lvAtendidos.setEnabled(false);

                TicketsAtendidos selectd = (TicketsAtendidos) lvAtendidos.getItemAtPosition(position);
                bundle.putString("id", selectd.getID());
                bundle.putString("opcion", "3");
                getParentFragmentManager().setFragmentResult("ticket", bundle);
                FragmentTransaction fragmentTransactionn = getFragmentManager().beginTransaction();
                fragmentTransactionn.add(R.id.fragmentContainerView, new ticket_menu()).commit();

                lvNuevos.setEnabled(false);
                lvProceso.setEnabled(false);
                lvAtendidos.setEnabled(false);
            }
        });

        listarTicket();
    }

    void listarTicket() {
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ticketsNuevosArrayList.clear();
                        ticketsProcesoArrayList.clear();
                        ticketsAtendidosArrayList.clear();
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                String ID = object.getString("ID");
                                String Titulo = object.getString("Titulo");
                                String Fecha = object.getString("Fecha");
                                String Usuario = object.getString("Usuario");
                                String Departamento = object.getString("Departamento");
                                String Incidencia = object.getString("Incidencia");
                                String Gravedad = object.getString("Gravedad");
                                String Version = object.getString("Version");
                                String Descripcion = object.getString("Descripcion");
                                String Archivo = object.getString("Archivo");
                                String Etapa = object.getString("Etapa");
                                String Archivado = object.getString("Archivado");

                                if (Archivado.equals("0")) {

                                    switch (Etapa) {
                                        case "Nuevo":
                                            ticketsNuevos = new TicketsNuevos(ID, Titulo, Fecha, Usuario, Departamento, Incidencia, Gravedad, Version, Descripcion, Archivo, Etapa, Archivado);
                                            ticketsNuevosArrayList.add(ticketsNuevos);
                                            adaptadorTicketsNuevos.notifyDataSetChanged();
                                            break;
                                        case "Proceso":
                                            ticketsProceso = new TicketsProceso(ID, Titulo, Fecha, Usuario, Departamento, Incidencia, Gravedad, Version, Descripcion, Archivo, Etapa, Archivado);
                                            ticketsProcesoArrayList.add(ticketsProceso);
                                            adaptadorTicketsProceso.notifyDataSetChanged();
                                            break;
                                        case "Atendido":
                                            ticketsAtendidos = new TicketsAtendidos(ID, Titulo, Fecha, Usuario, Departamento, Incidencia, Gravedad, Version, Descripcion, Archivo, Etapa, Archivado);
                                            ticketsAtendidosArrayList.add(ticketsAtendidos);
                                            adaptadorTicketsAtendidos.notifyDataSetChanged();
                                            break;
                                    }
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
                params.put("opcion", "6");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    void lvDisable(){
        lvNuevos.setEnabled(false);
        lvProceso.setEnabled(false);
        lvAtendidos.setEnabled(false);
    }
}