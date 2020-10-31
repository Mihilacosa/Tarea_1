package com.example.tarea_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TITULO = "com.com.example.tarea_1.TITULO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView Card1 = findViewById(R.id.card1);
        CardView Card2 = findViewById(R.id.card2);
        CardView Card3 = findViewById(R.id.card3);
        CardView Card4 = findViewById(R.id.card4);

        Card1.setOnClickListener(this);
        Card2.setOnClickListener(this);
        Card3.setOnClickListener(this);
        Card4.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        TextView titulo;
        
        switch (v.getId()){
            case R.id.card1:
                titulo = findViewById(R.id.Titulo);
                break;
            case R.id.card2:
                titulo = findViewById(R.id.Titulo2);
                break;
            case R.id.card3:
                titulo = findViewById(R.id.Titulo3);
                break;
            case R.id.card4:
                titulo = findViewById(R.id.Titulo4);
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