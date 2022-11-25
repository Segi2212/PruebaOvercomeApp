package com.example.pruebaovercome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebaovercome.ClasesYAdaptadores.Departamentos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    MetodosGenerales metodosGenerales = new MetodosGenerales();
    ImageView imageView2;
    ConstraintLayout linearLayout;
    Button btnTickets,btnAgregar,btnArchivados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView2 = findViewById(R.id.imageView2);
        linearLayout = findViewById(R.id.linearLayout);
        btnTickets = findViewById(R.id.btnTickets);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnArchivados = findViewById(R.id.btnArchivados);

        btnTickets.setOnClickListener(this);
        btnAgregar.setOnClickListener(this);
        btnArchivados.setOnClickListener(this);

        SharedPreferences preferences = this.getSharedPreferences("login", MODE_PRIVATE);
        String correo = preferences.getString("correo", "");
        comprobarDepartamento(correo);


    }

    void comprobarDepartamento(String correo) {
        StringRequest request = new StringRequest(Request.Method.POST, this.getString(R.string.URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                switch (response) {
                    case "101":
                        descragarDatos(correo);
                        break;
                    case "102":
                        imageView2.setVisibility(View.INVISIBLE);
                        linearLayout.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new departamento()).addToBackStack(null).commit();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this, "Hubo un error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion", "3");
                params.put("correo", correo.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    void descragarDatos(String correo){
        StringRequest request = new StringRequest(Request.Method.POST, this.getString(R.string.URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        String ID = object.getString("ID");
                        String Correo = object.getString("Correo");
                        String Nombre = object.getString("Nombre");
                        String Departamento = object.getString("Departamento");

                        SharedPreferences preferences = getSharedPreferences("datosUser",MODE_PRIVATE);
                        SharedPreferences.Editor editor= preferences.edit();
                        editor.putString("ID",ID);
                        editor.putString("Correo",Correo);
                        editor.putString("Nombre",Nombre);
                        editor.putString("Departamento",Departamento);
                        editor.commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this, "Hubo un error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion", "101");
                params.put("correo", correo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTickets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new tickets()).addToBackStack(null).commit();
                break;
            case R.id.btnAgregar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new agregar_ticket()).addToBackStack(null).commit();
                break;
        }
    }
}