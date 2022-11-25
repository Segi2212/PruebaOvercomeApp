package com.example.pruebaovercome;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class departamento_confirmacion extends Fragment implements View.OnClickListener {

    Button btnCancelar, btnAceptar;
    TextView tvPregunta;

    String departamento, correo;

    public departamento_confirmacion() {
        // Required empty public constructor
    }

    public static departamento_confirmacion newInstance(String param1, String param2) {
        departamento_confirmacion fragment = new departamento_confirmacion();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction fragmentTransactionn = getFragmentManager().beginTransaction();
                fragmentTransactionn.replace(R.id.fragmentContainerView, new departamento()).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        getParentFragmentManager().setFragmentResultListener("opcion", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                departamento = result.getString("dep");
                tvPregunta.setText("¿Tu departamento es " + departamento + "?");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_departamento_confirmacion, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPregunta = view.findViewById(R.id.tvPregunta);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnAceptar = view.findViewById(R.id.btnAceptar);

        btnCancelar.setOnClickListener(this);
        btnAceptar.setOnClickListener(this);

        SharedPreferences preferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        correo = preferences.getString("correo", "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancelar:
                FragmentTransaction fragmentTransactionn = getFragmentManager().beginTransaction();
                fragmentTransactionn.replace(R.id.fragmentContainerView, new departamento()).commit();
                break;

            case R.id.btnAceptar:
                agregarDepartamento();
                break;
        }
    }

    void agregarDepartamento() {
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
                params.put("opcion", "5");
                params.put("correo", correo);
                params.put("departamento", departamento);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

}