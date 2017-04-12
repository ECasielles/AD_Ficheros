package com.mercacortex.ad_ficheros.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mercacortex.ad_ficheros.R;

public class PeriodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodo);
    }

    protected void addFecha(){
        //Guarda fecha en archivo
    }
    protected void predict() {
        //Lee archivo y muestra predicción
    }
}
