package com.example.tarea_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Novela extends AppCompatActivity {
    public static final String ID_CAPITULO = "com.com.example.tarea_1.ID_CAPITULO";
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";
    private TextView Titulo_novela;
    private TextView resena;
    private ImageView portada;
    private ListView Lista_caps;
    private ArrayList<ListaCapitulos> Lista = new ArrayList<>();
    private ArrayList<Integer> Capitulos_id = new ArrayList<>();
    private String id_capitulo;
    private String id_novela;

    RecyclerView recyclerCapitulos;

    private  String usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novela);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            SharedPreferences datos_usu = getSharedPreferences("usuario_login", Context.MODE_PRIVATE);
            usuario = datos_usu.getString("usuario", "");
            if (!usuario.equals("")) {
                setTitle("Hola " + usuario);
            }else{

            }
        }

        Intent i = getIntent();

        if(i.getStringExtra(MainActivity.ID_NOVELA) == ""){
            id_novela = i.getStringExtra(Capitulo.ID_NOVELA);
        }else{
            id_novela = i.getStringExtra(MainActivity.ID_NOVELA);
        }

        CargarNovela("https://tnowebservice.000webhostapp.com/Novela_seleccionada.php?id_novela=" + id_novela);
        CargarCapitulos("https://tnowebservice.000webhostapp.com/Lista_caps.php?id_novela=" + id_novela);

        Titulo_novela = findViewById(R.id.Titulo_dif);
        resena = findViewById(R.id.Resena_novela_selec);
        portada = findViewById(R.id.Portada_novela_selec);

    }

    //carga novela y capitulos

    private void mandar_id_capitulo() {
        Intent a = new Intent(this, Capitulo.class);

        a.putExtra(ID_CAPITULO, id_capitulo);
        a.putExtra(ID_NOVELA, id_novela);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)Capitulos_id);
        a.putExtra("BUNDLE",args);
        startActivity(a);
    }

    private void CargarNovela(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Titulo_novela.setText(new String(jsonObject.getString("titulo").getBytes("ISO-8859-1"), "UTF-8"));
                        resena.setText(new String(jsonObject.getString("resena").getBytes("ISO-8859-1"), "UTF-8"));
                        Picasso.get().load(jsonObject.getString("portada")).into(portada);
                    } catch (JSONException | UnsupportedEncodingException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CARGA DE NOVELA SELECCIONADA", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void CargarCapitulos(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        String capitulo = "Capitulo: " + jsonObject.getString("num_capitulo") + " - " + new String(jsonObject.getString("titulo").getBytes("ISO-8859-1"), "UTF-8");
                        String id_cap = jsonObject.getString("id_capitulo");
                        Integer id_cap_int = Integer.valueOf(jsonObject.getString("id_capitulo"));
                        Capitulos_id.add(id_cap_int);
                        Lista.add(new ListaCapitulos(capitulo, id_cap));
                    } catch (JSONException | UnsupportedEncodingException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                recyclerCapitulos = (RecyclerView) findViewById(R.id.RecyclerCapitulos);
                recyclerCapitulos.setLayoutManager(new LinearLayoutManager(Novela.this));

                AdaptadorCapitulos adapter = new AdaptadorCapitulos(Lista);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_capitulo = Lista.get(recyclerCapitulos.getChildAdapterPosition(v)).getId();

                        mandar_id_capitulo();
                    }
                });

                recyclerCapitulos.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CARGA DE NOVELA SELECCIONADA", Toast.LENGTH_SHORT).show();
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

        if (!usuario.equals("")) {
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