package com.example.elro.nopapers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BufferedHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Registeralumno extends Activity {


    Spinner spincuenta;

    ArrayList<String> listitems=new ArrayList<>();
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registeralumno);
        final EditText edad = (EditText) findViewById(R.id.age2);
        final EditText name = (EditText) findViewById(R.id.nombrecompleto2);
        final EditText user = (EditText) findViewById(R.id.nombre2);
        final EditText correo = (EditText) findViewById(R.id.emailreg2);
        final EditText contraseña = (EditText) findViewById(R.id.password2);
        final Button bregistro= (Button)findViewById(R.id.bregister2);
        spincuenta = (Spinner)findViewById(R.id.spinnercuenta);
        adapter=new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.txt, listitems);
        spincuenta.setAdapter(adapter);


        bregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String agestring= edad.getText().toString();
                if(agestring.equals("")){

                    edad.setText("0");
                }else
                {}
                final String textcuenta= spincuenta.getSelectedItem().toString();
                final String nombre = name.getText().toString();
                final String usuario = user.getText().toString();

                final int age = Integer.parseInt(edad.getText().toString());
                final String password = contraseña.getText().toString();
                final String email = correo.getText().toString();

                int cuenta = 0;


                if(textcuenta.equals("Estudiante")){
                    cuenta=1;
                }else if(textcuenta.equals("Docente")){
                    cuenta=2;
                }else if(textcuenta.equals("Tutor")){
                    cuenta=3;
                }



                Response.Listener<String> responseListener= new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success= jsonResponse.getBoolean("success");
                            if(success){

                                Toast.makeText(getApplicationContext(),
                                        "Registro exitoso", Toast.LENGTH_LONG).show();


                                Intent openlogin = new Intent(Registeralumno.this, Login_Activity.class);
                                Registeralumno.this.startActivity(openlogin);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Registeralumno.this);
                                builder.setMessage("Registro Fallido")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                if(usuario.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeralumno.this);
                    builder.setMessage("Introduzca su usuario")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeralumno.this);
                    builder.setMessage("Introduzca su contraseña")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(email.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeralumno.this);
                    builder.setMessage("Introduzca su email")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(textcuenta.equals("Tipo de cuenta")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Registeralumno.this);
                builder.setMessage("Seleccione un tipo de cuenta")
                        .setNegativeButton("Aceptar", null)
                        .create()
                        .show();
                edad.setText("");
                }else if(nombre.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeralumno.this);
                    builder.setMessage("Introduzca su nombre")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(agestring.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeralumno.this);
                    builder.setMessage("Introduzca su edad")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");

                }else {

                    RegisterRequest registerRequest = new RegisterRequest(nombre, usuario, email, age, password, cuenta, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Registeralumno.this);
                    queue.add(registerRequest);

                }


            }
        });
    }

    protected void onStart(){
        super.onStart();
        BackTask bt = new BackTask();
        bt.execute();
    }

    private class BackTask extends AsyncTask<Void, Void, Void>{
        ArrayList<String> list;
        protected void onPreExecute(){
            super.onPreExecute();
            list= new ArrayList<>();
        }

        protected Void doInBackground(Void...params){
            InputStream is = null;
            String result="";
            try{
                HttpClient httpClient= new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://pruebaschoolactive.esy.es/categorias.php");
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity=response.getEntity();
                is=entity.getContent();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line="";
                while((line= bufferedReader.readLine())!=null){
                    result+=line;
                }
                is.close();

            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                JSONArray jsonArray=new JSONArray(result);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    list.add(jsonObject.getString("colCategoria"));
                }
            }catch (JSONException e){e.printStackTrace();}
            return null;

        }
        protected void onPostExecute(Void result){
            listitems.addAll(list);
            adapter.notifyDataSetChanged();

        }

    }


}
