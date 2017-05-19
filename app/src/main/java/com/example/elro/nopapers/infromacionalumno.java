package com.example.elro.nopapers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.id.list;
import static com.example.elro.nopapers.R.id.asismanual;
import static com.example.elro.nopapers.R.id.buttoninfo;

public class infromacionalumno extends AppCompatActivity {
    public String nombregrupo,email,idGrupo,nombreusuario,asistencia,fechalistview,nombre,fecha,fechaspin;
    public TextView etnombre,etemail;
    private String jsonResult;
    private String url = "http://schoolactive.xyz/VerAsistenicaAlumno.php";
    private ListView listView;
    private Map<String, String> params;
    ArrayList<String> listitems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    SearchableSpinner sp;
     Button btnasistencia;
    Spinner spinfecha;
    SimpleAdapter simpleAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infromacionalumno);

        etnombre= (TextView) findViewById(R.id.nombreet);
        etemail= (TextView) findViewById(R.id.emailet);
        listView = (ListView) findViewById(R.id.listas);
        btnasistencia = (Button) findViewById(asismanual);
        sp= (SearchableSpinner) findViewById(R.id.fechaspinner);
        spinfecha = (Spinner)findViewById(R.id.fechaspinner);
        adapter=new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.txt, listitems);
        sp.setTitle("Seleccione Fecha");
        sp.setPositiveButton("Cerrar");
        spinfecha.setAdapter(adapter);





        Intent abririnfoalum = getIntent();
        nombregrupo = abririnfoalum.getStringExtra("nombregrupo");

        idGrupo = abririnfoalum.getStringExtra("idGrupo");
        nombre = abririnfoalum.getStringExtra("nombre");
        fecha = abririnfoalum.getStringExtra("fecha");




        etnombre.setText(nombre);
        etemail.setText(email);

        this.setTitle(nombregrupo);


        accessWebService();

        btnasistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaspin = spinfecha.getSelectedItem().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                accessWebService();


                                Toast.makeText(getApplicationContext(), "Asistencia correcta  " , Toast.LENGTH_LONG).show();
                                //Intent intent= getIntent();
                                //finish();

                                //startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(infromacionalumno.this);
                                builder.setMessage("Error favor checa con el admin")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                pasarlistamanualrequest pasarlistamanualrequest = new pasarlistamanualrequest(idGrupo,fechaspin,nombreusuario, responseListener);
                RequestQueue queue = Volley.newRequestQueue(infromacionalumno.this);
                queue.add(pasarlistamanualrequest);
            }

        });


    }

    protected void onStart(){
        super.onStart();
        infromacionalumno.BackTask bt = new infromacionalumno.BackTask();
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
                HttpPost httpPost = new HttpPost("http://schoolactive.xyz/VerAsistenicaAlumno.php");
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



                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");
                for(int i=0;i<jsonMainNode.length();i++){
                    JSONObject jsonObject=jsonMainNode.getJSONObject(i);
                    list.add(jsonObject.getString("fecha"));
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
            HttpPost httppost = new HttpPost("http://schoolactive.xyz/VerAsistenicaAlumno.php");


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("nombre", nombre ));
                nameValuePairs.add(new BasicNameValuePair("idGrupo", idGrupo ));
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
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();
        params = new HashMap<>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                email = jsonChildNode.optString("email");
                nombreusuario = jsonChildNode.optString("nombreusuario");
                fechalistview = jsonChildNode.optString("fecha");
                asistencia = jsonChildNode.optString("asistencia");

                final int intasistencia = Integer.parseInt(asistencia);



                if (intasistencia==1)
                {

                    String outPut = fechalistview+ "  Asistencia ";
                    employeeList.add(createEmployee("", outPut));

                }

                else if (intasistencia==0)
                {

                    String outPut = fechalistview+ "  Falta ";
                    employeeList.add(createEmployee("", outPut));
                }




            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




         simpleAdapter = new SimpleAdapter(this, employeeList,
                android.R.layout.simple_list_item_1,
                new String[] { "" }, new int[] { android.R.id.text1 });
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String selectedFromList =(listView.getItemAtPosition(position).toString());



            }
        });
    }


    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }



}
