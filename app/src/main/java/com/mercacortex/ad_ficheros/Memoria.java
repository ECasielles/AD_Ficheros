package com.mercacortex.ad_ficheros;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Memoria {

    private Context contexto;

    public Memoria(Context contexto) {
        this.contexto = contexto;
    }

    public boolean escribirInterna(String nombreArchivo, String cadena, Boolean anadir, String encoding) {
        return escribir(new File(contexto.getFilesDir(), nombreArchivo), cadena, anadir, encoding);
    }

    private boolean escribir(File file, String cadena, Boolean anadir, String encoding) {
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        BufferedWriter out = null;
        boolean correcto = false;
        try {
            fileOutputStream = new FileOutputStream(file, anadir);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
            out = new BufferedWriter(outputStreamWriter);
            out.write(cadena);
        } catch (IOException e) {
            Log.e("Error de E/S", e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                    correcto = true;
                }
            } catch (IOException e) {
                Log.e("Error al cerrar", e.getMessage());
            }
        }
        return correcto;
    }

    public String mostrarPropiedadesInterna(String fichero) {
        File miFichero;
        miFichero = new File(fichero);
        return mostrarPropiedades(miFichero);
    }

    public String mostrarPropiedades(File fichero) {
        SimpleDateFormat simpleDateFormat;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (fichero.exists()) {
                stringBuffer.append("Nombre: " + fichero.getName() + '\n');
                stringBuffer.append("Ruta: " + fichero.getAbsolutePath() + '\n');
                stringBuffer.append("TamaÃ±o (bytes): " + Long.toString(fichero.length()) + '\n');
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
                stringBuffer.append("Fecha: " + simpleDateFormat.format(new Date(fichero.lastModified())) + '\n');
            } else
                stringBuffer.append("No existe el fichero " + fichero.getName() + '\n');
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            stringBuffer.append(e.getMessage());
        }
        return stringBuffer.toString();
    }

    public boolean disponibleEscritura() {

        boolean escritura = false;

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED))
            escritura = true;
        return escritura;
    }

    public boolean disponibleLectura() {
        boolean lectura = false;

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY) || estado.equals(Environment.MEDIA_MOUNTED))
            lectura = true;
        return lectura;
    }

    public boolean escribirExterna(String nombreArchivo, String cadena, Boolean anadir, String encoding) {
        return escribir(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), nombreArchivo), cadena, anadir, encoding);
    }

    public String mostrarPropiedadesExterna(String fichero) {
        File miFichero, tarjeta;
        tarjeta = Environment.getExternalStorageDirectory();
        miFichero = new File(tarjeta.getAbsolutePath(), fichero);
        return mostrarPropiedades(miFichero);
    }

    public Resultado leerInterna(String nombreArchivo, String encoding){
        File file;
        file = new File(contexto.getFilesDir(), nombreArchivo);
        return leer(file, encoding);
    }

    private Resultado leer(File fichero, String codigo){
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        Resultado resultado = new Resultado();
        resultado.setCodigo(true);
        try {
            fileInputStream = new FileInputStream(fichero);
            inputStreamReader = new InputStreamReader(fileInputStream, codigo);
            bufferedReader = new BufferedReader(inputStreamReader);
            int n;
            while ((n = bufferedReader.read()) != -1)
                stringBuilder.append((char) n);
        } catch (IOException e) {
            Log.e("Error E/S lectura", e.getMessage());
            resultado.setCodigo(false);
            resultado.setMensaje(e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    resultado.setContenido(stringBuilder.toString());
                }
            } catch (IOException e) {
                Log.e("Error cierre lectura", e.getMessage());
                resultado.setCodigo(false);
                resultado.setMensaje(e.getMessage());
            }
        }
        return resultado;
    }

    public Resultado leerExterna(String fichero, String codigo){
        File miFichero, tarjeta;
        //tarjeta = Environment.getExternalStoragePublicDirectory("datos/programas/");
        tarjeta = Environment.getExternalStorageDirectory();
        miFichero = new File(tarjeta.getAbsolutePath(), fichero);
        return leer(miFichero, codigo);
    }

    public Resultado leerRaw(String fichero){
        //fichero tendrÃ¡ el nombre del fichero raw sin la extensiÃ³n
        InputStream is = null;
        StringBuilder miCadena = new StringBuilder();
        int n;
        Resultado resultado = new Resultado();
        resultado.setCodigo(true);
        try {
            //is = contexto.getResources().openRawResource(R.raw.numero);
            is = contexto.getResources().openRawResource(
                    contexto.getResources().getIdentifier(fichero,"raw", contexto.getPackageName()));
            while ((n = is.read()) != -1) {
                miCadena.append((char) n);
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            resultado.setCodigo(false);
            resultado.setMensaje(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                    resultado.setContenido(miCadena.toString());
                }
            } catch (Exception e) {
                Log.e("Error al cerrar", e.getMessage());
                resultado.setCodigo(false);
                resultado.setMensaje(e.getMessage());
            }
        }
        return resultado;
    }

    public Resultado leerAsset(String fichero){
        AssetManager am = contexto.getAssets();
        InputStream is = null;
        StringBuilder miCadena = new StringBuilder();
        int n;
        Resultado resultado = new Resultado();
        resultado.setCodigo(true);
        try {
            is = am.open(fichero);
            while ((n = is.read()) != -1) {
                miCadena.append((char) n);
            }
        } catch (IOException e) {
            Log.e("Error", e.getMessage());
            resultado.setCodigo(false);
            resultado.setMensaje(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                    resultado.setContenido(miCadena.toString());
                }
            } catch (Exception e) {
                Log.e("Error al cerrar", e.getMessage());
                resultado.setCodigo(false);
                resultado.setMensaje(e.getMessage());
            }
        }
        return resultado;
    }

}