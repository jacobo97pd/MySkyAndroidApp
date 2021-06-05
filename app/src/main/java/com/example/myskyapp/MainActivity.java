package com.example.myskyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText etEmailUsuario,  etContrasenaUsuario;
    private static final String MIFICHERO = "FicheroUsuariosRegistrados.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pruebaConexionMongo();

        etEmailUsuario = (EditText)findViewById(R.id.etNombreUsuarioRegistrar);
        etContrasenaUsuario = (EditText)findViewById(R.id.etContrasenaUsuarioRegistrar);
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        // SharedPreferences preferencias = getPreferences(Context.MODE_PRIVATE);
        String nombreUsuario = preferencias.getString("nombre", "");
        etEmailUsuario.setText(nombreUsuario);
    }

    public void pruebaConexionMongo(){
        /*MongoClientURI mongoUri  = new MongoClientURI("mongodb://localhost:27017/mysky");
        //MongoClient mongoClient = new MongoClient(mongoUri);

        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient(mongoUri);
            DB db = mongoClient.getDB("mysky");
            Set<String> collectionNames = db.getCollectionNames();
        }catch (UnknownError e){
            e.printStackTrace();
        }

        if(mongoClient != null) {
            Toast.makeText(this, "Abriendo Enesimo numero primo", Toast.LENGTH_LONG).show();
        }*/

         System.out.println("Probando conexion");
         MongoClient mongo = null;
         try{
         mongo = new MongoClient("localhost", 27017);
         }catch (UnknownError e){
         Log.i("MyApp","I am here");
         e.printStackTrace();
         }

         if(mongo != null){
         DB db = mongo.getDB("basedatos");

             System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
         }



    }


    public void login(View view) {
        String valorEtNombreUsuarioLogIn = etEmailUsuario.getText().toString();
        String valorEtContrasenaLogIn = etContrasenaUsuario.getText().toString();
        if (valorEtContrasenaLogIn.isEmpty() || valorEtNombreUsuarioLogIn.isEmpty()) {
            Toast.makeText(this, "No puede haber campos vacíos.", Toast.LENGTH_LONG).show();
        } else {
            Usuario usuario = new Usuario(valorEtNombreUsuarioLogIn, valorEtContrasenaLogIn);

            if (comprobarUsuario(usuario)) {
                if (comprobarContrasena(usuario)) {
                    SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("nombre", etEmailUsuario.getText().toString());
                    // editor.putInt("edad", 3);
                    // editor.putBoolean("activo", true);
                    // editor.putFloat("altura", 2.3f);
                    editor.commit();
                    startActivity(new Intent(this, SubidaArchivosActivity.class));
                } else {
                    Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_LONG).show();
                    etContrasenaUsuario.setText("");
                }
            } else {
                startActivity(new Intent(this, Registro.class));
            }
        }
    }

    public boolean comprobarContrasena(Usuario s) {
        boolean contrasena = false;
        Usuario sc;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(MIFICHERO)));
            String linea = br.readLine();
            while (linea != null) {
                String[] s_parts = linea.split("#");
                sc = new Usuario(s_parts[0], s_parts[1]);
                if (sc.getContrasena().equals(s.getContrasena())) {
                    contrasena = true;
                    break;
                }
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error al comprobar contraseña", Toast.LENGTH_LONG).show();
        }
        return contrasena;
    }

    public void registrarse(View view) {
        startActivity(new Intent(this, Registro.class));
    }

    // SI DEVUELVE TRUE, EL USUARI O ESTA REGISTRADO
    public boolean comprobarUsuario(Usuario s) {
        boolean encontrado = false;
        Usuario sc;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(MIFICHERO)));
            String linea = br.readLine();
            while (linea != null) {
                String[] s_parts = linea.split("#");
                sc = new Usuario(s_parts[0], s_parts[1]);
                if (sc.getNombreUsuario().equalsIgnoreCase(s.getNombreUsuario())) {
                    encontrado = true;
                    break;
                }
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            Toast.makeText(this, "No existe el usuario", Toast.LENGTH_LONG).show();
        }
        return encontrado;
    }

}