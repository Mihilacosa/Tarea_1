package com.example.tarea_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";

    private  String usuario = "";
    String titulo, id, imagen;

    MenuItem itemlt;
    MenuItem itemln;
    MenuItem itemr;

    ArrayList<ListaNovelas> listaNovelas = new ArrayList<>();
    RecyclerView recyclerNovelas;

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

        registerForContextMenu(findViewById(R.id.main));
    }

    private void Cargar(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        titulo = URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8");
                        id = jsonObject.getString("id_novela");
                        imagen = jsonObject.getString("portada");
                        listaNovelas.add(new ListaNovelas(titulo, id, imagen));

                    } catch (JSONException | UnsupportedEncodingException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                recyclerNovelas = (RecyclerView) findViewById(R.id.ReyclerId);
                recyclerNovelas.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                AdaptadorNovelas adapter = new AdaptadorNovelas(listaNovelas);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Seleccionado: " + listaNovelas.get(recyclerNovelas.getChildAdapterPosition(v)).getTitulo(), Toast.LENGTH_SHORT).show();

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

    //Menu context
/*
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        switch (v.getId()){
            case R.id.card0:
                menu.setHeaderTitle(this.titulo_novela0.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena0);
                break;
            case R.id.card1:
                menu.setHeaderTitle(this.titulo_novela1.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena1);
                break;
            case R.id.card2:
                menu.setHeaderTitle(this.titulo_novela2.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena2);
                break;
            case R.id.card3:
                menu.setHeaderTitle(this.titulo_novela3.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena3);
                break;
            case R.id.card4:
                menu.setHeaderTitle(this.titulo_novela4.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena4);
                break;
            case R.id.card5:
                menu.setHeaderTitle(this.titulo_novela5.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena5);
                break;
            case R.id.card6:
                menu.setHeaderTitle(this.titulo_novela6.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena6);
                break;
            case R.id.card7:
                menu.setHeaderTitle(this.titulo_novela7.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena7);
                break;
            case R.id.card8:
                menu.setHeaderTitle(this.titulo_novela8.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena8);
                break;
            case R.id.card9:
                menu.setHeaderTitle(this.titulo_novela9.getText());
                menu.findItem(R.id.context_resena).setTitle((CharSequence) resena9);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

        inflater.inflate(R.menu.menu_contextual, menu);
    }
*/
    //Menu normal

    @Override public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_activity, mimenu);

        itemlt = mimenu.findItem(R.id.logout);
        itemln = mimenu.findItem(R.id.login);
        itemr = mimenu.findItem(R.id.registro);

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
            setTitle("Tarea_1");
            opciones_menu.setVisible(false);
            itemln.setVisible(true);
            itemr.setVisible(true);

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }
}