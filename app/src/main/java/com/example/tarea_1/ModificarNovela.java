package com.example.tarea_1;

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
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ModificarNovela extends AppCompatActivity {
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";

    private String usuario = "";
    String id_usuario = "";
    String id_novela = "";

    MenuItem itemlt;
    MenuItem itemln;
    MenuItem itemr;
    MenuItem subir_novelas;

    private static int SELECT_PICTURE = 2;

    String titulo_modi, resena_modi, nombre_alt_modi, autor_modi, artista_modi, traductor_modi, genero_modi;

    TextView titulo;
    TextView resena;
    ImageView portada_img;
    Button portada;
    Uri selectedImageURI, returnUri;
    Bitmap bitmap = null;
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
    Button enviar;
    TextView modi_generos;
    String extension;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_novela);

        titulo = findViewById(R.id.modi_nuevo_titulo);
        resena = findViewById(R.id.modi_nueva_resena);
        portada_img = findViewById(R.id.modi_nueva_portada);
        portada = findViewById(R.id.modi_btn_portada);
        nombre_alt = findViewById(R.id.modi_nuevo_nombre_alt);
        autor = findViewById(R.id.modi_nuevo_autor);
        artista = findViewById(R.id.modi_nuevo_artista);
        traductor = findViewById(R.id.modi_nuevo_traductor);
        generos_inicio();
        enviar = findViewById(R.id.modi_btnEnviar_novela);
        modi_generos = findViewById(R.id.modi_generos);

        Intent i = getIntent();

        id_novela = i.getStringExtra(ModificarNovelas.ID_NOVELA);

        imagename = "id_" + id_novela;

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

        CargarNovela("https://tnowebservice.000webhostapp.com/Novela_seleccionada_modi.php?id_novela=" + id_novela);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generos();
                Subir("https://tnowebservice.000webhostapp.com/Subir_novela_modi.php");

                if(!(bitmap == null)){
                    SubirImagen(bitmap);
                    UpdateURL("https://tnowebservice.000webhostapp.com/UpdateURL.php");
                }


                long start = System.currentTimeMillis();
                long end = start + 2*1000; // 60 seconds * 1000 ms/sec
                while (System.currentTimeMillis() < end) {

                    Intent i = new Intent(ModificarNovela.this, ModificarNovelas.class);
                    startActivity(i);
                }
            }
        });


    }

    private void CargarNovela(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Picasso.get().load(jsonObject.getString("portada")).into(portada_img);
                        titulo_modi = new String(jsonObject.getString("titulo").getBytes("ISO-8859-1"), "UTF-8");
                        resena_modi = new String(jsonObject.getString("resena").getBytes("ISO-8859-1"), "UTF-8");
                        nombre_alt_modi = new String(jsonObject.getString("nombre_alternativo").getBytes("ISO-8859-1"), "UTF-8");
                        autor_modi = new String(jsonObject.getString("autor").getBytes("ISO-8859-1"), "UTF-8");
                        artista_modi = new String(jsonObject.getString("artista").getBytes("ISO-8859-1"), "UTF-8");
                        traductor_modi = new String(jsonObject.getString("traductor").getBytes("ISO-8859-1"), "UTF-8");
                        genero_modi = new String(jsonObject.getString("genero").getBytes("ISO-8859-1"), "UTF-8");
                    } catch (JSONException | UnsupportedEncodingException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                titulo.setText(titulo_modi);
                resena.setText(resena_modi);
                nombre_alt.setText(nombre_alt_modi);
                autor.setText(autor_modi);
                artista.setText(artista_modi);
                traductor.setText(traductor_modi);
                modi_generos.setText("Genero: " + genero_modi);
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

    public void generos_inicio() {
        checkBox1 = findViewById(R.id.modi_checkBox1);
        checkBox2 = findViewById(R.id.modi_checkBox2);
        checkBox3 = findViewById(R.id.modi_checkBox3);
        checkBox4 = findViewById(R.id.modi_checkBox4);
        checkBox5 = findViewById(R.id.modi_checkBox5);
        checkBox6 = findViewById(R.id.modi_checkBox6);
        checkBox7 = findViewById(R.id.modi_checkBox7);
        checkBox8 = findViewById(R.id.modi_checkBox8);
        checkBox9 = findViewById(R.id.modi_checkBox9);
        checkBox10 = findViewById(R.id.modi_checkBox10);

        checkBox11 = findViewById(R.id.modi_checkBox11);
        checkBox12 = findViewById(R.id.modi_checkBox12);
        checkBox13 = findViewById(R.id.modi_checkBox13);
        checkBox14 = findViewById(R.id.modi_checkBox14);
        checkBox15 = findViewById(R.id.modi_checkBox15);
        checkBox16 = findViewById(R.id.modi_checkBox16);
        checkBox18 = findViewById(R.id.modi_checkBox18);
        checkBox19 = findViewById(R.id.modi_checkBox19);
        checkBox20 = findViewById(R.id.modi_checkBox20);

        checkBox21 = findViewById(R.id.modi_checkBox21);
        checkBox22 = findViewById(R.id.modi_checkBox22);
        checkBox23 = findViewById(R.id.modi_checkBox23);
        checkBox24 = findViewById(R.id.modi_checkBox24);
        checkBox25 = findViewById(R.id.modi_checkBox25);
        checkBox26 = findViewById(R.id.modi_checkBox26);
        checkBox27 = findViewById(R.id.modi_checkBox27);
        checkBox28 = findViewById(R.id.modi_checkBox28);
        checkBox29 = findViewById(R.id.modi_checkBox29);
        checkBox30 = findViewById(R.id.modi_checkBox30);

        checkBox31 = findViewById(R.id.modi_checkBox31);
        checkBox32 = findViewById(R.id.modi_checkBox32);
        checkBox33 = findViewById(R.id.modi_checkBox33);
        checkBox34 = findViewById(R.id.modi_checkBox34);
        checkBox35 = findViewById(R.id.modi_checkBox35);
        checkBox36 = findViewById(R.id.modi_checkBox36);
        checkBox37 = findViewById(R.id.modi_checkBox37);
        checkBox38 = findViewById(R.id.modi_checkBox38);
        checkBox39 = findViewById(R.id.modi_checkBox39);
        checkBox40 = findViewById(R.id.modi_checkBox40);
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

        if(!genero.equals("")) {
            genero = genero.substring(0, genero.length() - 2);
        }
    }

    private void fetchImage(){
        final CharSequence[] options = { "Sacar foto", "Elegir de galeria","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ModificarNovela.this);
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

        if (resultCode == RESULT_OK) {
            portada_img.setImageBitmap(null);
            bitmap = null;
            if (requestCode == 1) {
                bitmap = (Bitmap) data.getExtras().get("data");
                extension = ".png";
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

                returnUri = data.getData();
                Cursor returnCursor =
                        getContentResolver().query(returnUri, null, null, null, null);

                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();

                extension = returnCursor.getString(nameIndex);

                extension = extension.substring(extension.lastIndexOf("."));

            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void Subir(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ModificarNovela.this, "Modificado", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModificarNovela.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_novela", id_novela);
                parametros.put("titulo", titulo.getText().toString());
                parametros.put("resena", resena.getText().toString());
                parametros.put("nombre_alternativo", nombre_alt.getText().toString());
                parametros.put("autor", autor.getText().toString());
                parametros.put("artista", artista.getText().toString());
                parametros.put("traductor", traductor.getText().toString());
                if(genero == ""){
                    genero = genero_modi;
                }
                parametros.put("genero", genero);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void SubirImagen(final Bitmap bitmap) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "https://tnowebservice.000webhostapp.com/Subir_portada.php", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                //Toast.makeText(SubirNovela.this, "Imagen subida", Toast.LENGTH_LONG).show();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModificarNovela.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("GotError","" + error.getMessage());
            }
        }) {
        @Override
        protected Map<String, DataPart> getByteData() {
            Map<String, DataPart> params = new HashMap<>();
            params.put("imagen", new DataPart(imagename + extension, getFileDataFromDrawable(bitmap)));
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
                //Toast.makeText(SubirNovela.this, "Subido", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModificarNovela.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", id_novela);
                parametros.put("url", "https://tnowebservice.000webhostapp.com/img/id_" + id_novela + extension);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Menu normal

    @Override public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_activity, mimenu);

        itemlt = mimenu.findItem(R.id.logout);
        itemln = mimenu.findItem(R.id.login);
        itemr = mimenu.findItem(R.id.registro);
        subir_novelas = mimenu.findItem(R.id.subir_novela);

        MenuItem play = mimenu.findItem(R.id.play);
        MenuItem pause = mimenu.findItem(R.id.pause);
        play.setVisible(false);
        pause.setVisible(false);

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

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }

}