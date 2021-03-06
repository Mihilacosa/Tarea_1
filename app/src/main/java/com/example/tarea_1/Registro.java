package com.example.tarea_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText NuevoUsuario;
    private EditText NuevoEmail;
    private EditText NuevaContrasena;
    private EditText NuevaContrasena2;
    private FirebaseAuth mAuth;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        Button InicioSesionRegisatro = findViewById(R.id.InicioRegistro);
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

        Button CrearCuenta = findViewById(R.id.bCrearCuenta);
        CrearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String usuario =  NuevoUsuario.getText().toString();
                email = NuevoEmail.getText().toString();
                String Contrasena = NuevaContrasena.getText().toString();
                String Contrasena2 = NuevaContrasena2.getText().toString();

                if(TextUtils.isEmpty(usuario)){
                    NuevoUsuario.setError("El usuario debe contener minimo un carácter");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    NuevoEmail.setError("El email es obligatorio");
                    return;
                }

                if(TextUtils.isEmpty(Contrasena)){
                    NuevaContrasena.setError("la contraseña no puede estar vacia");
                    return;
                }

                if(Contrasena.length() < 6){
                    NuevaContrasena.setError("la contraseña debe tener como minimo 6 caracteres");
                    return;
                }

                if(!Contrasena.equals(Contrasena2)) {
                    NuevaContrasena2.setError("No coincide con la contraseña");
                    return;
                }

                if(Contrasena.equals(Contrasena2)) {

                    mAuth.createUserWithEmailAndPassword(email, Contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                EnvioInfoCrearCuenta("https://tnowebservice.000webhostapp.com/NuevoUsuario.php");


                                Intent i = new Intent(Registro.this, MainActivity.class);

                                startActivity(i);
                            } else {
                                Toast.makeText(Registro.this, "El email esta siendo usado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void EnvioLogin(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                SharedPreferences datos_usu = getSharedPreferences("usuario_login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = datos_usu.edit();

                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        editor.putString("usuario", jsonObject.getString("usuario"));
                        editor.apply();
                    } catch (JSONException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR AL COMPROBAR DATOS", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
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
                EnvioLogin("https://tnowebservice.000webhostapp.com/Login.php?email=" + email);
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

        MenuItem play = mimenu.findItem(R.id.play);
        MenuItem pause = mimenu.findItem(R.id.pause);
        play.setVisible(false);
        pause.setVisible(false);

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