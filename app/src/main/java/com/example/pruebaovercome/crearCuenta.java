package com.example.pruebaovercome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class crearCuenta extends AppCompatActivity implements View.OnClickListener {

    MetodosGenerales metodosGenerales = new MetodosGenerales();

    Button btnCrear;
    TextInputEditText etCorreo, etNombre, etContrasena;
    TextView tvIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        btnCrear = findViewById(R.id.btnCrear);
        etCorreo = findViewById(R.id.etCorreo);
        etNombre = findViewById(R.id.etNombre);
        etContrasena = findViewById(R.id.etContrasena);
        tvIniciar = findViewById(R.id.tvIniciar);

        btnCrear.setOnClickListener(this);
        tvIniciar.setOnClickListener(this);

        metodosGenerales.comprobarConexionn(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCrear:
                if ((etCorreo.getText().toString()).isEmpty() || (etNombre.getText().toString()).isEmpty() || (etContrasena.getText().toString()).isEmpty()) {
                    Toast.makeText(this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    metodosGenerales.agregarUsuario(getApplicationContext(), etCorreo.getText().toString().trim(), etNombre.getText().toString().trim(), etContrasena.getText().toString().trim());
                }
                break;
            case R.id.tvIniciar:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}