package com.example.tarea_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class Capitulo extends AppCompatActivity {
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";
    private TextView Titulo;
    private TextView Contenido;
    private String id_novela;
    private String id_capitulo;
    private ArrayList<Integer> Capitulos_id = new ArrayList<>();
    private int posicion = 0;
    private Integer id_cap;
    private ScrollView scroll;

    private  String usuario = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitulo);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            SharedPreferences datos_usu = getSharedPreferences("usuario_login", Context.MODE_PRIVATE);
            usuario = datos_usu.getString("usuario", "");
            if (usuario != "") {
                setTitle("Hola " + usuario);
            }else{

            }
        }

        Button Anterior = findViewById(R.id.Anterior);
        Button Indice = findViewById(R.id.Indice);
        Button Siguiente = findViewById(R.id.Siguiente);

        Button Anterior2 = findViewById(R.id.Anterior2);
        Button Indice2 = findViewById(R.id.Indice2);
        Button Siguiente2 = findViewById(R.id.Siguiente2);

        scroll = findViewById(R.id.Cap_scroll);


        Intent a = getIntent();
        id_novela = a.getStringExtra(Novela.ID_NOVELA);
        id_capitulo = a.getStringExtra(Novela.ID_CAPITULO);
        id_cap = Integer.valueOf(id_capitulo);

        Bundle args = a.getBundleExtra("BUNDLE");
        Capitulos_id = (ArrayList<Integer>) args.getSerializable("ARRAYLIST");

        for (int i = 0; i < Capitulos_id.size(); i++){
            if(Capitulos_id.get(i) == id_cap){
                posicion = i;
            }
        }

        int cap_max = Capitulos_id.size() - 1;

        if(posicion == 0){
            Anterior.setEnabled(false);
            Anterior2.setEnabled(false);
        }

        if(posicion == cap_max){
            Siguiente.setEnabled(false);
            Siguiente2.setEnabled(false);
        }

        Titulo = findViewById(R.id.Titulo_cap);
        Contenido = findViewById(R.id.Contenido_cap);

        CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

        Anterior.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(posicion != cap_max){
                    Siguiente.setEnabled(true);
                }

                posicion = posicion - 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == 0){
                    Anterior.setEnabled(false);
                }else{
                    Anterior.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Anterior2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(posicion != cap_max){
                    Siguiente2.setEnabled(true);
                }

                posicion = posicion - 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == 0){
                    Anterior2.setEnabled(false);
                }else{
                    Anterior2.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Siguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                posicion = posicion + 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == cap_max){
                    Siguiente.setEnabled(false);
                }else{
                    Siguiente.setEnabled(true);
                }

                if(posicion != 0){
                    Anterior.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Siguiente2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                posicion = posicion + 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == cap_max){
                    Siguiente2.setEnabled(false);
                }else{
                    Siguiente2.setEnabled(true);
                }

                if(posicion != 0){
                    Anterior2.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Indice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                VolverNovela();
            }
        });

        Indice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                VolverNovela();
            }
        });
    }

    //Volver a novela
    private void VolverNovela() {
        Intent i = new Intent(this, Novela.class);
        i.putExtra(ID_NOVELA, id_novela);
        startActivity(i);
    }


    private void CargarCapitulo(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Titulo.setText("Capitulo: " + jsonObject.getString("num_capitulo") + " - " + URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                        Contenido.setText(URLDecoder.decode(jsonObject.getString("contenido"), "UTF-8"));
                    } catch (JSONException | UnsupportedEncodingException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CARGA DEL CAPITULO SELECCIONADA", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //Combio de activity menu

    @Override public boolean onCreateOptionsMenu(Menu mimenu){

        getMenuInflater().inflate(R.menu.menu_activity, mimenu);

        MenuItem itemlt = mimenu.findItem(R.id.logout);
        MenuItem itemln = mimenu.findItem(R.id.login);
        MenuItem itemr = mimenu.findItem(R.id.registro);

        if (usuario != "") {
            itemlt.setVisible(true);
            itemln.setVisible(false);
            itemr.setVisible(false);
        }else{
            itemln.setVisible(true);
            itemr.setVisible(true);
        }

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

            Intent i = new Intent(this, Registro.class);

            startActivity(i);

            return true;
        }

        if(id == R.id.logout){

            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, MainActivity.class);

            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }
}