package com.example.tarea_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        Button InicioSesionRegisatro = (Button) findViewById(R.id.InicioRegistro);
        InicioSesionRegisatro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                InicioSesionReg();
            }
        });

    }

    public void InicioSesionReg(){
        Intent i = new Intent(this, Login.class);

        startActivity(i);
    }

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

            return true;
        }

        return super.onOptionsItemSelected(opciones_menu);
    }
}