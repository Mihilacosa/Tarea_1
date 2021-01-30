package com.example.tarea_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SubirNovela extends AppCompatActivity {

    private  String usuario = "";
    String id_usuario = "";
    String id_novela = "";

    MenuItem itemlt;
    MenuItem itemln;
    MenuItem itemr;
    MenuItem subir_novelas;

    private static int SELECT_PICTURE = 2;

    TextView titulo;
    TextView resena;
    ImageView portada_img;
    Button portada;
    Uri selectedImageURI;
    String rutaImagen;
    Bitmap bitmap;
    String imagename;
    TextView nombre_alt;
    TextView autor;
    TextView artista;
    TextView traductor;
    String genero = "";
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10,
            checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16, checkBox18, checkBox19, checkBox20,
            checkBox21, checkBox22, checkBox23, checkBox24, checkBox25, checkBox26, checkBox27, checkBox28, checkBox29, checkBox30,
            checkBox31, checkBox32, checkBox33, checkBox34, checkBox35, checkBox36, checkBox37, checkBox38, checkBox39, checkBox40;
    TextView titulo_cap;
    RadioButton tipo0, tipo1;
    String tipo_cap = "";
    TextView contenido_cap;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_novela);

        titulo = findViewById(R.id.nuevo_titulo);
        resena = findViewById(R.id.nueva_resena);
        portada_img = findViewById(R.id.nueva_portada);
        portada = findViewById(R.id.btn_portada);
        nombre_alt = findViewById(R.id.nuevo_nombre_alt);
        autor = findViewById(R.id.nuevo_autor);
        artista = findViewById(R.id.nuevo_artista);
        traductor = findViewById(R.id.nuevo_traductor);
        generos_inicio();
        titulo_cap = findViewById(R.id.nuevo_titulo_cap);
        tipo0 = findViewById(R.id.nuevo_prologo);
        tipo1 = findViewById(R.id.nuevo_cap_1);
        contenido_cap = findViewById(R.id.nuevo_contenido_cap);
        enviar = findViewById(R.id.btnEnviar_novela);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            SharedPreferences datos_usu = getSharedPreferences("usuario_login", Context.MODE_PRIVATE);
            usuario = datos_usu.getString("usuario", "");
            id_usuario = datos_usu.getString("id", "");
            if (!usuario.equals("")) {
                setTitle("Hola " + usuario);
            }
        }

        //registerForContextMenu(findViewById(R.id.main));
        portada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImage();
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo_cap_seleccionado();
                generos();
                Subir("https://tnowebservice.000webhostapp.com/Subir_novela.php");
                Id_novela("https://tnowebservice.000webhostapp.com/Id_novela.php?titulo=" + titulo.getText().toString() + "&autor=" + autor.getText().toString());
                SubirImagen(bitmap);
                UpdateURL("https://tnowebservice.000webhostapp.com/UpdateURL.php");
            }
        });
    }

    public void generos_inicio() {
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);
        checkBox9 = findViewById(R.id.checkBox9);
        checkBox10 = findViewById(R.id.checkBox10);

        checkBox11 = findViewById(R.id.checkBox11);
        checkBox12 = findViewById(R.id.checkBox12);
        checkBox13 = findViewById(R.id.checkBox13);
        checkBox14 = findViewById(R.id.checkBox14);
        checkBox15 = findViewById(R.id.checkBox15);
        checkBox16 = findViewById(R.id.checkBox16);
        checkBox18 = findViewById(R.id.checkBox18);
        checkBox19 = findViewById(R.id.checkBox19);
        checkBox20 = findViewById(R.id.checkBox20);

        checkBox21 = findViewById(R.id.checkBox21);
        checkBox22 = findViewById(R.id.checkBox22);
        checkBox23 = findViewById(R.id.checkBox23);
        checkBox24 = findViewById(R.id.checkBox24);
        checkBox25 = findViewById(R.id.checkBox25);
        checkBox26 = findViewById(R.id.checkBox26);
        checkBox27 = findViewById(R.id.checkBox27);
        checkBox28 = findViewById(R.id.checkBox28);
        checkBox29 = findViewById(R.id.checkBox29);
        checkBox30 = findViewById(R.id.checkBox30);

        checkBox31 = findViewById(R.id.checkBox31);
        checkBox32 = findViewById(R.id.checkBox32);
        checkBox33 = findViewById(R.id.checkBox33);
        checkBox34 = findViewById(R.id.checkBox34);
        checkBox35 = findViewById(R.id.checkBox35);
        checkBox36 = findViewById(R.id.checkBox36);
        checkBox37 = findViewById(R.id.checkBox37);
        checkBox38 = findViewById(R.id.checkBox38);
        checkBox39 = findViewById(R.id.checkBox39);
        checkBox40 = findViewById(R.id.checkBox40);
    }

    public void generos() {
        if (checkBox1.isChecked()){
            genero += checkBox1.getText() + ", ";
        }
        if (checkBox2.isChecked()){
            genero += checkBox2.getText() + ", ";
        }
        if (checkBox3.isChecked()){
            genero += checkBox3.getText() + ", ";
        }
        if (checkBox4.isChecked()){
            genero += checkBox4.getText() + ", ";
        }
        if (checkBox5.isChecked()){
            genero += checkBox5.getText() + ", ";
        }
        if (checkBox6.isChecked()){
            genero += checkBox6.getText() + ", ";
        }
        if (checkBox7.isChecked()){
            genero += checkBox7.getText() + ", ";
        }
        if (checkBox8.isChecked()){
            genero += checkBox8.getText() + ", ";
        }
        if (checkBox9.isChecked()){
            genero += checkBox9.getText() + ", ";
        }
        if (checkBox10.isChecked()){
            genero += checkBox10.getText() + ", ";
        }

        if (checkBox11.isChecked()){
            genero += checkBox11.getText() + ", ";
        }
        if (checkBox12.isChecked()){
            genero += checkBox12.getText() + ", ";
        }
        if (checkBox13.isChecked()){
            genero += checkBox13.getText() + ", ";
        }
        if (checkBox14.isChecked()){
            genero += checkBox14.getText() + ", ";
        }
        if (checkBox15.isChecked()){
            genero += checkBox15.getText() + ", ";
        }
        if (checkBox16.isChecked()){
            genero += checkBox16.getText() + ", ";
        }
        if (checkBox18.isChecked()){
            genero += checkBox18.getText() + ", ";
        }
        if (checkBox19.isChecked()){
            genero += checkBox19.getText() + ", ";
        }
        if (checkBox20.isChecked()){
            genero += checkBox20.getText() + ", ";
        }

        if (checkBox21.isChecked()){
            genero += checkBox21.getText() + ", ";
        }
        if (checkBox22.isChecked()){
            genero += checkBox22.getText() + ", ";
        }
        if (checkBox23.isChecked()){
            genero += checkBox23.getText() + ", ";
        }
        if (checkBox24.isChecked()){
            genero += checkBox24.getText() + ", ";
        }
        if (checkBox25.isChecked()){
            genero += checkBox25.getText() + ", ";
        }
        if (checkBox26.isChecked()){
            genero += checkBox26.getText() + ", ";
        }
        if (checkBox27.isChecked()){
            genero += checkBox27.getText() + ", ";
        }
        if (checkBox28.isChecked()){
            genero += checkBox28.getText() + ", ";
        }
        if (checkBox29.isChecked()){
            genero += checkBox29.getText() + ", ";
        }
        if (checkBox30.isChecked()){
            genero += checkBox30.getText() + ", ";
        }

        if (checkBox31.isChecked()){
            genero += checkBox31.getText() + ", ";
        }
        if (checkBox32.isChecked()){
            genero += checkBox32.getText() + ", ";
        }
        if (checkBox33.isChecked()){
            genero += checkBox33.getText() + ", ";
        }
        if (checkBox34.isChecked()){
            genero += checkBox34.getText() + ", ";
        }
        if (checkBox35.isChecked()){
            genero += checkBox35.getText() + ", ";
        }
        if (checkBox36.isChecked()){
            genero += checkBox36.getText() + ", ";
        }
        if (checkBox37.isChecked()){
            genero += checkBox37.getText() + ", ";
        }
        if (checkBox38.isChecked()){
            genero += checkBox38.getText() + ", ";
        }
        if (checkBox39.isChecked()){
            genero += checkBox39.getText() + ", ";
        }
        if (checkBox40.isChecked()){
            genero += checkBox40.getText() + ", ";
        }

        genero = genero.substring(0, genero.length() - 2);
    }

    public void tipo_cap_seleccionado() {
        if (tipo0.isChecked()){
            tipo_cap = "0";
        }

        if (tipo1.isChecked()){
            tipo_cap = "1";
        }
    }

    private void fetchImage(){
        final CharSequence[] options = { "Sacar foto", "Elegir de galeria","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SubirNovela.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Sacar foto"))
                {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        startActivityForResult(takePictureIntent, 1);
                    } catch (ActivityNotFoundException e) {
                        // display error state to the user
                    }
                }
                else if (options[item].equals("Elegir de galeria"))
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                }
                else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        portada_img.setImageBitmap(null);
        bitmap = null;

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                bitmap = (Bitmap) data.getExtras().get("data");

                portada_img.setImageBitmap(bitmap);
            } else {

                selectedImageURI = data.getData();
                //rutaImagen = getPath(selectedImageURI);
                //rutaImagen = selectedImageURI.getPath();

                if (requestCode == SELECT_PICTURE && data != null && data.getData() != null) {
                    try {
                        Picasso.get().load(selectedImageURI).noPlaceholder().centerCrop().fit().into(portada_img);
                        //Log.d("filePath", String.valueOf(rutaImagen));
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageURI);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void Subir(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Subido", Toast.LENGTH_SHORT).show();
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
                parametros.put("id_usuario", id_usuario);
                parametros.put("titulo", titulo.getText().toString());
                parametros.put("resena", resena.getText().toString());
                parametros.put("nombre_alternativo", nombre_alt.getText().toString());
                parametros.put("autor", autor.getText().toString());
                parametros.put("artista", artista.getText().toString());
                parametros.put("traductor", traductor.getText().toString());
                parametros.put("genero", genero);
                parametros.put("titulo_cap", titulo_cap.getText().toString());
                parametros.put("capitulo", tipo_cap);
                parametros.put("cont_capitulo", contenido_cap.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Id_novela(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = response.getJSONObject(0);
                    id_novela = jsonObject.getString("id_novela");
                    contenido_cap.setText(id_novela);
                    imagename = "id_" + id_novela;
                } catch (JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR al recibir id de la novela", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void SubirImagen(final Bitmap bitmap) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "https://tnowebservice.000webhostapp.com/Subir_portada.php", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(getApplicationContext(), "Imagen subida", Toast.LENGTH_LONG).show();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("GotError","" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("imagen", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void UpdateURL(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Subido", Toast.LENGTH_SHORT).show();
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
                parametros.put("id", id_novela);
                parametros.put("url", "https://tnowebservice.000webhostapp.com/img/id_" + id_novela + ".jpg");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }
}