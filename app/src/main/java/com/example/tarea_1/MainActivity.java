package com.example.tarea_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";
    private TextView titulo_novela0;
    private TextView titulo_novela1;
    private TextView titulo_novela2;
    private TextView titulo_novela3;
    private TextView titulo_novela4;
    private TextView titulo_novela5;
    private TextView titulo_novela6;
    private TextView titulo_novela7;
    private TextView titulo_novela8;
    private TextView titulo_novela9;

    private TextView id_novela0;
    private TextView id_novela1;
    private TextView id_novela2;
    private TextView id_novela3;
    private TextView id_novela4;
    private TextView id_novela5;
    private TextView id_novela6;
    private TextView id_novela7;
    private TextView id_novela8;
    private TextView id_novela9;

    private String resena0;
    private String resena1;
    private String resena2;
    private String resena3;
    private String resena4;
    private String resena5;
    private String resena6;
    private String resena7;
    private String resena8;
    private String resena9;

    private ImageView portada0;
    private ImageView portada1;
    private ImageView portada2;
    private ImageView portada3;
    private ImageView portada4;
    private ImageView portada5;
    private ImageView portada6;
    private ImageView portada7;
    private ImageView portada8;
    private ImageView portada9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cargar("https://tnowebservice.000webhostapp.com/UltimasActualizaciones.php");

        CardView Card0 = findViewById(R.id.card0);
        CardView Card1 = findViewById(R.id.card1);
        CardView Card2 = findViewById(R.id.card2);
        CardView Card3 = findViewById(R.id.card3);
        CardView Card4 = findViewById(R.id.card4);
        CardView Card5 = findViewById(R.id.card5);
        CardView Card6 = findViewById(R.id.card6);
        CardView Card7 = findViewById(R.id.card7);
        CardView Card8 = findViewById(R.id.card8);
        CardView Card9 = findViewById(R.id.card9);

        Card0.setOnClickListener(this);
        Card1.setOnClickListener(this);
        Card2.setOnClickListener(this);
        Card3.setOnClickListener(this);
        Card4.setOnClickListener(this);
        Card5.setOnClickListener(this);
        Card6.setOnClickListener(this);
        Card7.setOnClickListener(this);
        Card8.setOnClickListener(this);
        Card9.setOnClickListener(this);

        titulo_novela0 = (TextView) findViewById(R.id.titulo0);
        titulo_novela1 = (TextView) findViewById(R.id.titulo1);
        titulo_novela2 = (TextView) findViewById(R.id.titulo2);
        titulo_novela3 = (TextView) findViewById(R.id.titulo3);
        titulo_novela4 = (TextView) findViewById(R.id.titulo4);
        titulo_novela5 = (TextView) findViewById(R.id.titulo5);
        titulo_novela6 = (TextView) findViewById(R.id.titulo6);
        titulo_novela7 = (TextView) findViewById(R.id.titulo7);
        titulo_novela8 = (TextView) findViewById(R.id.titulo8);
        titulo_novela9 = (TextView) findViewById(R.id.titulo9);

        id_novela0 = (TextView) findViewById(R.id.id_novela0);
        id_novela1 = (TextView) findViewById(R.id.id_novela1);
        id_novela2 = (TextView) findViewById(R.id.id_novela2);
        id_novela3 = (TextView) findViewById(R.id.id_novela3);
        id_novela4 = (TextView) findViewById(R.id.id_novela4);
        id_novela5 = (TextView) findViewById(R.id.id_novela5);
        id_novela6 = (TextView) findViewById(R.id.id_novela6);
        id_novela7 = (TextView) findViewById(R.id.id_novela7);
        id_novela8 = (TextView) findViewById(R.id.id_novela8);
        id_novela9 = (TextView) findViewById(R.id.id_novela9);

        portada0 = (ImageView) findViewById(R.id.portada0);
        portada1 = (ImageView) findViewById(R.id.portada1);
        portada2 = (ImageView) findViewById(R.id.portada2);
        portada3 = (ImageView) findViewById(R.id.portada3);
        portada4 = (ImageView) findViewById(R.id.portada4);
        portada5 = (ImageView) findViewById(R.id.portada5);
        portada6 = (ImageView) findViewById(R.id.portada6);
        portada7 = (ImageView) findViewById(R.id.portada7);
        portada8 = (ImageView) findViewById(R.id.portada8);
        portada9 = (ImageView) findViewById(R.id.portada9);
        registerForContextMenu(findViewById(R.id.main));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        TextView id_novela;
        
        switch (v.getId()){
            case R.id.card0:
                id_novela = findViewById(R.id.id_novela0);
                break;
            case R.id.card1:
                id_novela = findViewById(R.id.id_novela1);
                break;
            case R.id.card2:
                id_novela = findViewById(R.id.id_novela2);
                break;
            case R.id.card3:
                id_novela = findViewById(R.id.id_novela3);
                break;
            case R.id.card4:
                id_novela = findViewById(R.id.id_novela4);
                break;
            case R.id.card5:
                id_novela = findViewById(R.id.id_novela5);
                break;
            case R.id.card6:
                id_novela = findViewById(R.id.id_novela6);
                break;
            case R.id.card7:
                id_novela = findViewById(R.id.id_novela7);
                break;
            case R.id.card8:
                id_novela = findViewById(R.id.id_novela8);
                break;
            case R.id.card9:
                id_novela = findViewById(R.id.id_novela9);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

        String id_N = id_novela.getText().toString();
        
        Intent i = new Intent(this, Novela.class);

        i.putExtra(ID_NOVELA, id_N);

        startActivity(i);
    }

    private void Cargar(String URL) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);

                        switch (i) {
                            case 0:
                                titulo_novela0.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela0.setText(jsonObject.getString("id_novela"));
                                resena0 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada0);
                                break;
                            case 1:
                                titulo_novela1.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela1.setText(jsonObject.getString("id_novela"));
                                resena1 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada1);
                                break;
                            case 2:
                                titulo_novela2.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela2.setText(jsonObject.getString("id_novela"));
                                resena2 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada2);
                                break;
                            case 3:
                                titulo_novela3.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela3.setText(jsonObject.getString("id_novela"));
                                resena3 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada3);
                                break;
                            case 4:
                                titulo_novela4.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela4.setText(jsonObject.getString("id_novela"));
                                resena4 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada4);
                                break;
                            case 5:
                                titulo_novela5.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela5.setText(jsonObject.getString("id_novela"));
                                resena5 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada5);
                                break;
                            case 6:
                                titulo_novela6.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela6.setText(jsonObject.getString("id_novela"));
                                resena6 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada6);
                                break;
                            case 7:
                                titulo_novela7.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela7.setText(jsonObject.getString("id_novela"));
                                resena7 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada7);
                                break;
                            case 8:
                                titulo_novela8.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela8.setText(jsonObject.getString("id_novela"));
                                resena8 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada8);
                                break;
                            case 9:
                                titulo_novela9.setText(URLDecoder.decode(jsonObject.getString("titulo"), "UTF-8"));
                                id_novela9.setText(jsonObject.getString("id_novela"));
                                resena9 = (URLDecoder.decode(jsonObject.getString("resena"), "UTF-8"));
                                Picasso.get().load(jsonObject.getString("portada")).into(portada9);
                                break;
                        }
                    } catch (JSONException | IOException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CARGA DE NOVELAS", Toast.LENGTH_SHORT).show();
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
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem opciones_menu){

        int id = opciones_menu.getItemId();

        if(id == R.id.inicio){

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