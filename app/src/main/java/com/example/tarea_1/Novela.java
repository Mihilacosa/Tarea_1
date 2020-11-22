package com.example.tarea_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Novela extends AppCompatActivity {
    public static final String ID_CAPITULO = "com.com.example.tarea_1.ID_CAPITULO";
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";
    private TextView Titulo_novela;
    private TextView resena;
    private ImageView portada;
    private ListView Lista_caps;
    private List<String> Lista = new ArrayList<>();
    private ArrayAdapter<String> Adaptador;
    private ArrayList<Integer> Capitulos_id = new ArrayList<>();
    private String id_capitulo;
    private String id_novela;

    ArrayList<String> lista_capitulos;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novela);

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


        Lista_caps = findViewById(R.id.Lista_capitulos);

        Lista_caps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < Capitulos_id.size(); i++){
                    if(i == position){
                        int id_cap = Capitulos_id.get(i);
                        id_capitulo = String.valueOf(id_cap);
                        mandar_id_capitulo();
                    }
                }
            }
        });
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
                        Titulo_novela.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                        resena.setText(jsonObject.getString("resena"));
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
                        Integer id_cap = Integer.valueOf(jsonObject.getString("id_capitulo"));
                        Capitulos_id.add(id_cap);
                        Lista.add("Capitulo: " + jsonObject.getString("num_capitulo") + " - " + URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                        Adaptador = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,Lista);
                        Lista_caps.setAdapter(Adaptador);
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


    //Combio de activity menu

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

            Intent i = new Intent(this, Registro.class);

            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }
}