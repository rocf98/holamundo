package com.example.elro.nopapers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.elro.nopapers.R.id.button;
import static com.example.elro.nopapers.R.id.et;

public class NotificarAlPadre extends AppCompatActivity {

    TextView etfecha;
    String fecha,nombregrupo,idGrupo,nombrealumno;
    private String jsonResult;
    private ListView listView;
    private Map<String, String> params;
    private String url = "http://pruebaschoolactive.esy.es/ListaAlumnosFaltas.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificar_al_padre);

        accessWebService();


        etfecha=(TextView)findViewById(R.id.fecha);
        listView = (ListView) findViewById(R.id.listfaltantes);



        Intent abrirnotificarpadre = getIntent();
        fecha = abrirnotificarpadre.getStringExtra("fecha");
        nombregrupo = abrirnotificarpadre.getStringExtra("nombregrupo");
        idGrupo = abrirnotificarpadre.getStringExtra("idGrupo");

        etfecha.setText(fecha);
        this.setTitle("Notificar al Padre");
    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://pruebaschoolactive.esy.es/ListaAlumnosFaltas.php");


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("fecha", fecha ));
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
        NotificarAlPadre.JsonReadTask task = new NotificarAlPadre.JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { url });
    }

    // build hash set for list view
    public void ListDrwaer() {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();
        params = new HashMap<>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("listafaltantes");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("nombre");







                String outPut = name;

                employeeList.add(createEmployee("", outPut));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Asistieron todos los alumnos", Toast.LENGTH_LONG).show();
        }




        SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList,
                android.R.layout.simple_list_item_1,
                new String[] { "" }, new int[] { android.R.id.text1 });
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                String selectedFromList =(listView.getItemAtPosition(position).toString());


                if (selectedFromList != null && !selectedFromList.isEmpty()) {
                    Pattern p = Pattern.compile("[{=}]");
                    Matcher m = p.matcher(selectedFromList);
                    if (m.find()) {
                        nombrealumno = m.replaceAll("");
                    }
                }


            }
        });
    }


    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }





}
