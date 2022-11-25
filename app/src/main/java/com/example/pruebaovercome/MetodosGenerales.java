package com.example.pruebaovercome;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MetodosGenerales {

    void comprobarConexionn(Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getString(R.string.URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (response) {
                    case "100":
                        Toast.makeText(context, "Hubo un error al conectar con el servidor.\n Intente más tarde.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Hubo un error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion","0");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    void agregarUsuario(Context context, String correo, String nombre, String contrasena) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getString(R.string.URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (response) {
                    case "101":
                        Intent i = new Intent(context, MainActivity.class);
                        Toast.makeText(context, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                        context.startActivity(i);
                        break;
                    case "102":
                        Toast.makeText(context, "Hubo un problema al crear la cuenta. \n Intente más tarde.", Toast.LENGTH_SHORT).show();
                        break;
                    case "103":
                        Toast.makeText(context, "El correo que ha introducido ya tiene una cuenta registrada", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "Error desconocido.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Hubo un error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion","1");
                params.put("correo",correo.trim());
                params.put("nombre",nombre.trim());
                params.put("contrasena",contrasena.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    void logearUsuario(Context context, String correo, String contrasena) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getString(R.string.URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                switch (response) {
                    case "101":
                        Intent i = new Intent(context, MainActivity2.class);
                        SharedPreferences preferences = context.getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor= preferences.edit();
                        editor.putString("correo",correo);
                        editor.putString("contrasena",contrasena);
                        editor.putBoolean("sesion",true);
                        editor.commit();
                        context.startActivity(i);
                        break;
                    case "102":
                        Toast.makeText(context, "Los datos no corresponden a ninguna cuenta.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "Error desconocido.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Hubo un error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion","2");
                params.put("correo",correo.trim());
                params.put("contrasena",contrasena.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    void comprobarUsuario(Context context, String correo, String contrasena) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getString(R.string.URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                switch (response) {
                    case "101":
                        Intent i = new Intent(context, MainActivity2.class);
                        context.startActivity(i);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Hubo un error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion","2");
                params.put("correo",correo.trim());
                params.put("contrasena",contrasena.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

}