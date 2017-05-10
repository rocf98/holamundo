package com.example.elro.nopapers;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class grupointerfaz extends AppCompatActivity {
    private Button button,buttoninfo, btonlistaasistencia;


    private String jsonResult;
    private ListView listView;
    private Map<String, String> params;
    private String url = "http://pruebaschoolactive.esy.es/new.php";
    public String name, nombregrupo,claveacc, idGrupo, usuarioname, nombreusuario;
    public String email,resultqr,fecha,nombrealumno;
    int contador=1;
    TextView ettotales;

    public Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupointerfaz);

        listView = (ListView) findViewById(R.id.listaalumnos);
        ettotales=(TextView)findViewById(R.id.etalumnostotales);







        Intent opengrupo = getIntent();


        claveacc = opengrupo.getStringExtra("claveacc");




        MainActivity activity2 = new MainActivity();
        name = activity2.getMyData();

        nombregrupo = opengrupo.getStringExtra("nombregrupo");




        this.setTitle(nombregrupo);






        accessWebService();




        button = (Button) findViewById(R.id.button);
        activity =this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                IntentIntegrator integrator = new IntentIntegrator(activity);
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                                integrator.setPrompt("Asistencia del "+fecha);
                                integrator.setCameraId(0);
                                integrator.setOrientationLocked(false);
                                integrator.setBeepEnabled(false);
                                integrator.setBarcodeImageEnabled(false);
                                integrator.initiateScan();




                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(grupointerfaz.this);
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

                actualizarlistarequest actualizarlistarequest = new actualizarlistarequest(idGrupo,fecha, responseListener);
                RequestQueue queue = Volley.newRequestQueue(grupointerfaz.this);
                queue.add(actualizarlistarequest);
            }

        });

        buttoninfo = (Button) findViewById(R.id.buttoninfo);

        buttoninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        btonlistaasistencia = (Button) findViewById(R.id.btnlistaasistencia);

        btonlistaasistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent abrirlistas = new Intent(grupointerfaz.this, ListaAsistencias.class);

                abrirlistas.putExtra("idGrupo", idGrupo);
                abrirlistas.putExtra("nombregrupo", nombregrupo);
                abrirlistas.putExtra("fecha", fecha);
                startActivity(abrirlistas);

            }
        });




        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mMonthf = mMonth+1;




        fecha= mYear+"-"+mMonthf+"-"+mDay;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Finalizado", Toast.LENGTH_LONG).show();


                Intent abrirnotificarpadre = new Intent(grupointerfaz.this, NotificarAlPadre.class);

                abrirnotificarpadre.putExtra("idGrupo", idGrupo);
                abrirnotificarpadre.putExtra("nombregrupo", nombregrupo);
                abrirnotificarpadre.putExtra("fecha", fecha);
                startActivity(abrirnotificarpadre);

            } else {
                // Log.d("MainActivity", "Scanned");

                resultqr=result.getContents();
                Thread tr= new Thread() {


                    @Override
                    public void run() {
                        final String resultado= enviarDatosGETQR(idGrupo,resultqr);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                int r=obtDatosJSONQR(resultado);

                                if(r>0){


                                    final int asistencia = 1;
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                if (success) {


                                                    Toast.makeText(grupointerfaz.this, "Asistio el alumno: "+resultqr, Toast.LENGTH_SHORT).show();





                                                }
                                            } catch (JSONException e) {
                                                Toast.makeText(getApplicationContext(), "No se pudo verificar asistencia  " , Toast.LENGTH_LONG).show();

                                                e.printStackTrace();
                                            }


                                        }
                                    };
                                    nombreusuario=resultqr;
                                    subirasistenciarequest subirasistenciarequest = new subirasistenciarequest(resultqr, idGrupo,fecha, responseListener);
                                    RequestQueue queue1 = Volley.newRequestQueue(grupointerfaz.this);
                                    queue1.add(subirasistenciarequest);
                                }





                                else {
                                    Toast.makeText(getApplicationContext(), "El alumno no existe", Toast.LENGTH_SHORT).show();

                                }
                            }

                        });

                    }
                };
                tr.start();

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Asistencia del "+fecha);
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();


            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://pruebaschoolactive.esy.es/new.php");


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("claveacc", claveacc ));
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
            JSONArray jsonMainNode = jsonResponse.optJSONArray("usuarios");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("nombre");
                email = jsonChildNode.optString("email");
                usuarioname= jsonChildNode.optString("nombreusuario");
                idGrupo= jsonChildNode.optString("idGrupos");
                nombregrupo= jsonChildNode.optString("nombregrupo");


                //contador=contador+1;
                //             ettotales.setText(contador);



                String outPut = name;

                employeeList.add(createEmployee("", outPut));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Aun no tienes alumnos registrados", Toast.LENGTH_LONG).show();
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

                Intent abririnfoalum = new Intent(grupointerfaz.this, infromacionalumno.class);
                abririnfoalum.putExtra("nombre", nombrealumno);
                abririnfoalum.putExtra("idGrupo", idGrupo);
                abririnfoalum.putExtra("nombregrupo", nombregrupo);
                abririnfoalum.putExtra("fecha", fecha);





                startActivity(abririnfoalum);

            }
        });
    }


    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }

    public String enviarDatosGETQR(String idGrupo,String nombreusuario ){

        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try{
            url=new URL("http://pruebaschoolactive.esy.es/consultalistaalumn.php?idGrupo="+idGrupo+"&nombreusuario="+nombreusuario);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();

            resul=new StringBuilder();

            if(respuesta==HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while((linea=reader.readLine())!=null){
                    resul.append(linea);


                }



            }


        }catch(Exception e){}


        return  resul.toString();


    }



    public  int obtDatosJSONQR(String response){


        int res=0;

        try {

            JSONArray json= new JSONArray(response);
            if(json.length()>0){

                res=1;

            }

        }catch(Exception e){}
        return res;


    }

}
