package com.example.pruebaovercome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MetodosGenerales metodosGenerales = new MetodosGenerales();

    Button btnIniciar;
    TextInputEditText etCorreo, etContrasena;
    TextView tvCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnIniciar = findViewById(R.id.btnIniciar);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        tvCrear = findViewById(R.id.tvCrear);

        btnIniciar.setOnClickListener(this);
        tvCrear.setOnClickListener(this);

        metodosGenerales.comprobarConexionn(this);

        SharedPreferences preferences = this.getSharedPreferences("login", MODE_PRIVATE);
        String correo = preferences.getString("correo", "");
        String contrasena = preferences.getString("contrasena", "");
        Boolean sesion = preferences.getBoolean("sesion", false);

        if(sesion){
            metodosGenerales.comprobarUsuario(this, correo, contrasena);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnIniciar:
                if ((etCorreo.getText().toString()).isEmpty() || (etContrasena.getText().toString()).isEmpty()) {
                    Toast.makeText(this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    metodosGenerales.logearUsuario(this, etCorreo.getText().toString().trim(), etContrasena.getText().toString().trim());
                }
                break;
            case R.id.tvCrear:
                Intent i = new Intent(this, crearCuenta.class);
                startActivity(i);
                finish();
                break;
        }
    }
}