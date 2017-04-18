package com.mercacortex.ad_ficheros.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mercacortex.ad_ficheros.Memoria;
import com.mercacortex.ad_ficheros.R;
import com.mercacortex.ad_ficheros.Resultado;

import java.util.concurrent.TimeUnit;

public class AlarmaActivity extends AppCompatActivity {

    TextView txvContador;
    Button btnComenzar;

    Memoria memoria;
    String ruta = "alarmas.txt";
    Resultado lectura;
    String[] alarmas;
    CountDownTimer myCountDownTimer;

    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        txvContador = (TextView) findViewById(R.id.txvContador);
        btnComenzar = (Button) findViewById(R.id.btnComenzar);
        initialize();

        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myCountDownTimer != null)
                    myCountDownTimer.cancel();
                if(cont < alarmas.length) {
                    btnComenzar.setText("Siguiente");
                    contador();
                } else {
                    cont = 0;
                    btnComenzar.setText("Comenzar");
                    txvContador.setText("Pausa terminada!!");
                }
            }
        });
    }

    //Arranca un contador tras otro
    private void contador() {
        myCountDownTimer = new CountDownTimer(Long.parseLong(alarmas[cont++]), 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                txvContador.setText(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(1)));
            }
            @Override
            public void onFinish() {
                Toast.makeText(AlarmaActivity.this, "¡Alarma!", Toast.LENGTH_SHORT).show();
                //Arranca la siguiente alarma o termina.
                if(cont < alarmas.length) {
                    contador();
                }
            }
        }.start();
    }

    //Crea un archivo con valores por defecto para pruebas
    private void initialize() {
        try {
            memoria = new Memoria(this);
            lectura = memoria.leerInterna(ruta, "UTF-8");
            if (!lectura.getCodigo()) {
                Toast.makeText(this, "Creando alarmas nuevas", Toast.LENGTH_SHORT).show();
                memoria.escribirInterna(ruta, String.valueOf(1 * 60 * 1000) + "\n", true, "UTF-8");
                memoria.escribirInterna(ruta, String.valueOf(2 * 60 * 1000) + "\n", true, "UTF-8");
                memoria.escribirInterna(ruta, String.valueOf(3 * 60 * 1000) + "\n", true, "UTF-8");
                memoria.escribirInterna(ruta, String.valueOf(4 * 60 * 1000) + "\n", true, "UTF-8");
                lectura = memoria.leerInterna(ruta, "UTF-8");
            }
            alarmas = lectura.getContenido().split("\n");
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
