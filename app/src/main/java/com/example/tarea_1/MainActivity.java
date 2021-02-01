package com.example.tarea_1;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";

    private  String usuario = "";
    String titulo, id, imagen, resena;

    MenuItem itemlt;
    MenuItem itemln;
    MenuItem itemr;
    MenuItem subir_novelas;

    ArrayList<ListaNovelas> listaNovelas = new ArrayList<>();
    RecyclerView recyclerNovelas;
    AdaptadorNovelas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cargar("https://tnowebservice.000webhostapp.com/UltimasActualizaciones.php");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            SharedPreferences datos_usu = getSharedPreferences("usuario_login", Context.MODE_PRIVATE);
            usuario = datos_usu.getString("usuario", "");
            if (!usuario.equals("")) {
                setTitle("Hola " + usuario);
            }
        }
        //registerForContextMenu(findViewById(R.id.main));
    }

    private void Cargar(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        titulo = new String(jsonObject.getString("titulo").getBytes("ISO-8859-1"), "UTF-8");
                        id = jsonObject.getString("id_novela");
                        imagen = jsonObject.getString("portada");
                        resena = new String(jsonObject.getString("resena").getBytes("ISO-8859-1"), "UTF-8");
                        listaNovelas.add(new ListaNovelas(titulo, id, imagen, resena));

                    } catch (JSONException | UnsupportedEncodingException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                recyclerNovelas = findViewById(R.id.ReyclerId);
                recyclerNovelas.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                adapter = new AdaptadorNovelas(listaNovelas);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_N = listaNovelas.get(recyclerNovelas.getChildAdapterPosition(v)).getId();

                        Intent i = new Intent(MainActivity.this, Novela.class);

                        i.putExtra(ID_NOVELA, id_N);

                        startActivity(i);
                    }
                });


                recyclerNovelas.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CARGA DE NOVELA", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //Menu normal

    @Override public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_activity, mimenu);

        itemlt = mimenu.findItem(R.id.logout);
        itemln = mimenu.findItem(R.id.login);
        itemr = mimenu.findItem(R.id.registro);
        subir_novelas = mimenu.findItem(R.id.subir_novela);

        if (!usuario.equals("")) {
            itemlt.setVisible(true);
            itemln.setVisible(false);
            itemr.setVisible(false);
            subir_novelas.setVisible(true);
        }else{
            itemln.setVisible(true);
            itemr.setVisible(true);
            subir_novelas.setVisible(false);
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

        if(id == R.id.subir_novela){

            Intent i = new Intent(this, SubirNovela.class);

            startActivity(i);

            return true;
        }

        if(id == R.id.logout){

            FirebaseAuth.getInstance().signOut();
            setTitle("Tarea_1");
            opciones_menu.setVisible(false);
            itemln.setVisible(true);
            itemr.setVisible(true);
            subir_novelas.setVisible(false);

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 120:
                String resena2 = adapter.mostrarResena(item.getGroupId());
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.resenya_activity_main);
                TextView txt = (TextView)dialog.findViewById(R.id.resenya);
                txt.setText(resena2);
                dialog.show();
                return true;
            case 121:
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}