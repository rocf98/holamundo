package com.example.elro.nopapers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addgrupo extends Activity {

    private Map<String, String> params;


    EditText nombremateria;
    Spinner spinmateria;
    TextView inv1,inv2;
    private String url = "http://pruebaschoolactive.esy.es/consultaidmat.php";
    private String jsonResult;
    ArrayList<String> listitems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    SearchableSpinner sp;
    String namemateria, idGrupo, nombregrupo,claveacc;
    MainActivity activity= new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgrupo);
        this.setTitle("Añadir Grupo");

        final EditText namegrupo = (EditText) findViewById(R.id.namegroup);
        final TextView tvnive = (TextView) findViewById(R.id.nivelescuela);
        final Spinner spinner = (Spinner) findViewById(R.id.spinnernivel);
        final EditText nombreescuela = (EditText) findViewById(R.id.nameescuela);
        final Button bregistrogrupo = (Button) findViewById(R.id.botonnext);
        sp= (SearchableSpinner) findViewById(R.id.materiaspinner);
        inv1= (TextView)findViewById(R.id.matask);
        inv2= (TextView)findViewById(R.id.crearmateria);
        spinmateria = (Spinner)findViewById(R.id.materiaspinner);
        adapter=new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.txt, listitems);
        sp.setTitle("Seleccione Materia");
        sp.setPositiveButton("Cerrar");
        spinmateria.setAdapter(adapter);





        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                final String itemText = parent.getItemAtPosition(pos).toString();
                tvnive.setText(itemText);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bregistrogrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String textnamegrupo = namegrupo.getText().toString();
                String textnamemateria = spinmateria.getSelectedItem().toString();
                String textschool = nombreescuela.getText().toString();
                claveacc = activity.generarclave(6);



                if(textnamegrupo.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(addgrupo.this);
                    builder.setMessage("Introduzca el nombre del grupo")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                }else if(textnamemateria.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addgrupo.this);
                    builder.setMessage("Introduzca su materia")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                }else if(textschool.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addgrupo.this);
                    builder.setMessage("Introduzca su escuela")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                }else if(tvnive.getText().equals("Escoger Nivel")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addgrupo.this);
                    builder.setMessage("Introduzca el nivel escolar")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                }else {

                    Intent intent = getIntent();
                    nombregrupo = namegrupo.getText().toString();
                    final String nivel = tvnive.getText().toString();
                    namemateria = spinmateria.getSelectedItem().toString();
                    final String nameescuela = nombreescuela.getText().toString();
                    final String creador = intent.getStringExtra("nombre");



                    accessWebService();


                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override

                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(),
                                            "Grupo creado con exito", Toast.LENGTH_LONG).show();

                                   Intent opengrupo = new Intent(addgrupo.this, grupointerfaz.class);
                                    opengrupo.putExtra("nombregrupo", nombregrupo);
                                  opengrupo.putExtra("claveacc", claveacc);
                                  startActivity(opengrupo);
                                  finish();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(addgrupo.this);
                                    builder.setMessage("Error al crear el grupo")
                                            .setNegativeButton("Reintentar", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    Criterioseva gruposrequest = new Criterioseva(namemateria, creador, nameescuela, nivel, claveacc, nombregrupo, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(addgrupo.this);
                    queue.add(gruposrequest);




                }

            }

        });
    }
    protected void onStart(){
        super.onStart();
        BackTask bt = new BackTask();
        bt.execute();
    }

    private class BackTask extends AsyncTask<Void, Void, Void> {
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
                HttpPost httpPost = new HttpPost("http://pruebaschoolactive.esy.es/materiasconsult.php");
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
                    list.add(jsonObject.getString("NombreMat"));
                }
            }catch (JSONException e){e.printStackTrace();}
            return null;

        }
        protected void onPostExecute(Void result){
            listitems.addAll(list);
            adapter.notifyDataSetChanged();

        }

    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://pruebaschoolactive.esy.es/consultaidmat.php");


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("nombregrupo", nombregrupo ));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                HttpResponse response = httpclient.execute(httppost);

                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
        }
    }// end async task



    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { url });
    }


    // build hash set for list view
    public void ListDrwaer() {

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("grupos");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                 idGrupo = jsonChildNode.optString("idGrupos");







            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }


    public void crearmateria(View v){
        Intent opencm = new Intent(addgrupo.this, Crearmateria.class);
        addgrupo.this.startActivity(opencm);
        finish();
    }

}