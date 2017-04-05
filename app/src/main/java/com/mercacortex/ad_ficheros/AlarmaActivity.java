package com.mercacortex.ad_ficheros;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mercacortex.ad_ficheros.R;

public class AlarmaActivity extends AppCompatActivity {

    TextView txvContador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        txvContador = (TextView) findViewById(R.id.txvContador);

        //Lee desde archivo y carga los datos para arrancar la alarma
    }

    class MyCountDownTimer extends CountDownTimer {
        int minutos = 0;
        int segundos = 0;
        TextView texto;

        public MyCountDownTimer(long startTime, long interval, TextView texto) {
            super(startTime, interval);
            this.texto = texto;
            segundos = (int) (startTime/ 1000);
            minutos = segundos / 60;
            segundos %= 60;
            texto.setText(String.format("%1$M:%2$S", minutos, segundos));
        }
        @Override
        public void onTick(long millisUntilFinished) {
            segundos = (int) (millisUntilFinished/ 1000);
            minutos = segundos / 60;
            segundos %= 60;
            texto.setText(minutos + ":" + segundos);
        }
        @Override public void onFinish() {
            texto.setText("Pausa terminada!!");
        }
    }

    protected void countDown(){
        new MyCountDownTimer(300000, 1000, txvContador).start();
    }


}
