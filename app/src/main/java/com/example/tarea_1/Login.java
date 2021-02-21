package com.example.tarea_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText contrasena;
    private Button Login;
    private FirebaseAuth mAuth;
    CheckBox humano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        humano = findViewById(R.id.humano);

        mAuth = FirebaseAuth.getInstance();

        Button Registrarse = findViewById(R.id.RegistroInicio);
        Registrarse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Registrarse();
            }
        });

        email = findViewById(R.id.Email_login);
        contrasena = findViewById(R.id.Contrasena_login);
        Login = findViewById(R.id.bLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(humano.isChecked()) {
                    String Email = email.getText().toString();
                    String Contrasena = contrasena.getText().toString();

                    mAuth.signInWithEmailAndPassword(Email, Contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                EnvioLogin("https://tnowebservice.000webhostapp.com/Login.php?email=" + Email);

                                Intent i = new Intent(Login.this, MainActivity.class);

                                startActivity(i);
                            } else {
                                Toast.makeText(Login.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Login.this, "Debe de ser humano", Toast.LENGTH_SHORT).show();
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
                        editor.putString("id", jsonObject.getString("id_usuario"));
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

    public void Registrarse(){
        Intent i = new Intent(this, Registro.class);

        startActivity(i);
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

            return true;
        }

        if(id == R.id.registro){

            Intent i = new Intent(this, Registro.class);

            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }
}