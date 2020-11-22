package com.example.tarea_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Registro extends AppCompatActivity {

    private EditText NuevoUsuario;
    private EditText NuevoEmail;
    private EditText NuevaContrasena;
    private EditText NuevaContrasena2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        Button InicioSesionRegisatro = (Button) findViewById(R.id.InicioRegistro);
        InicioSesionRegisatro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InicioSesionReg();
            }
        });


        NuevoUsuario = findViewById(R.id.CrearUsuario);
        NuevoEmail = findViewById(R.id.CrearEmail);
        NuevaContrasena = findViewById(R.id.CrearContrasena1);
        NuevaContrasena2 = findViewById(R.id.ConbContrasena1);


        Button CrearCuenta = (Button) findViewById(R.id.bCrearCuenta);
        CrearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String Contrasena = NuevaContrasena.getText().toString();
                String Contrasena2 = NuevaContrasena2.getText().toString();
                if(Contrasena.equals(Contrasena2)) {
                    EnvioInfoCrearCuenta("https://tnowebservice.000webhostapp.com/NuevoUsuario.php");
                }else{
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Moverse a iniciar sesión

    public void InicioSesionReg(){
        Intent i = new Intent(this, Login.class);

        startActivity(i);
    }

    //Enviar datos para crear cuenta

    private void EnvioInfoCrearCuenta(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Cuenta creada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", NuevoUsuario.getText().toString());
                parametros.put("email", NuevoEmail.getText().toString());
                parametros.put("contrasena", NuevaContrasena.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_activity, mimenu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem opciones_menu){
        int id = opciones_menu.getItemId();

        if(id == R.id.inicio){

            Intent i = new Intent(this, MainActivity.class);

            startActivity(i);

            return true;
        }

        if(id == R.id.login){

            Intent i = new Intent(this, Login.class);

            startActivity(i);

            return true;
        }

        if(id == R.id.registro){

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }
}