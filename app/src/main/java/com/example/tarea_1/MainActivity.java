package com.example.tarea_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TITULO = "com.com.example.tarea_1.TITULO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView Card1 = (CardView) findViewById(R.id.card1);
        CardView Card2 = (CardView) findViewById(R.id.card2);
        CardView Card3 = (CardView) findViewById(R.id.card3);
        CardView Card4 = (CardView) findViewById(R.id.card4);

        Card1.setOnClickListener(this);
        Card2.setOnClickListener(this);
        Card3.setOnClickListener(this);
        Card4.setOnClickListener(this);
    }

    public void Novela1(){
        @SuppressLint("WrongViewCast") TextView titulo = (TextView) findViewById(R.id.Titulo);
        String Titulo = titulo.getText().toString();

        Intent i = new Intent(this, Novela.class);

        i.putExtra(TITULO, Titulo);

        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        TextView titulo;
        
        switch (v.getId()){
            case R.id.card1:
               titulo = (TextView) findViewById(R.id.Titulo);
                break;
            case R.id.card2:
                titulo = (TextView) findViewById(R.id.Titulo2);
                break;
            case R.id.card3:
                titulo = (TextView) findViewById(R.id.Titulo3);
                break;
            case R.id.card4:
                titulo = (TextView) findViewById(R.id.Titulo4);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        String Titulo = titulo.getText().toString();
        
        Intent i = new Intent(this, Novela.class);

        i.putExtra(TITULO, Titulo);

        startActivity(i);
    }

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
/*
        if(id == R.id.espanol){

            return true;
        }

        if(id == R.id.ingles){

            return true;
        }
*/
        return super.onOptionsItemSelected(opciones_menu);
    }
}