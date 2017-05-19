package com.example.elro.nopapers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ListaAsistencias extends AppCompatActivity {

    TextView etfecha1, etfecha2,etiprueba;
    Calendar c = Calendar.getInstance();
    int cday, cmonth, cyear;
    TextView etfecha;
    String fecha,nombregrupo,idGrupo,nombrealumno, fecha1, fecha2,fechalistview,asistencia;
    private String jsonResult;
    private ListView listView;
    private Map<String, String> params;
    private String url = "http://schoolactive.xyz/ListaAsistenciasTotales.php";
Button btnconsultarfechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asistencias);

        etfecha1 = (TextView) findViewById(R.id.fecha1);
        etfecha2 = (TextView) findViewById(R.id.fecha2);
        etfecha=(TextView)findViewById(R.id.fecha);

        listView = (ListView) findViewById(R.id.listviewfechas);

        Intent abrirlistas = getIntent();
        fecha = abrirlistas.getStringExtra("fecha");
        nombregrupo = abrirlistas.getStringExtra("nombregrupo");
        idGrupo = abrirlistas.getStringExtra("idGrupo");






        etfecha1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ListaAsistencias.this, d,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        etfecha2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ListaAsistencias.this, de,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        btnconsultarfechas = (Button) findViewById(R.id.consultarasistencias);

        btnconsultarfechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
;



                accessWebService();


            }
        });







    }




    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            cday = dayOfMonth;
            cmonth = monthOfYear + 1;
            cyear = year;

            etfecha1.setText(cyear + "/" + cmonth + "/"
                    + cday);
            fecha1=etfecha1.getText().toString();





        }
    };


    DatePickerDialog.OnDateSetListener de = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            cday = dayOfMonth;
            cmonth = monthOfYear + 1;
            cyear = year;

            etfecha2.setText(cyear + "/" + cmonth + "/"
                    + cday);


            fecha2=etfecha2.getText().toString();

        }
    };








    private class JsonReadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://schoolactive.xyz/ListaAsistenciasTotales.php");


            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("fecha1", fecha1 ));
                nameValuePairs.add(new BasicNameValuePair("fecha2", fecha2 ));
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
        ListaAsistencias.JsonReadTask task = new ListaAsistencias.JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { url });
    }

    // build hash set for list view
    public void ListDrwaer() {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();
        params = new HashMap<>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("listatotales");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("nombre");
                fechalistview = jsonChildNode.optString("fecha");
                asistencia = jsonChildNode.optString("asistencia");









                final int intasistencia = Integer.parseInt(asistencia);



                if (intasistencia==1)
                {

                    String outPut = fechalistview+"  "+name+ "  Asistencia ";
                    employeeList.add(createEmployee("", outPut));

                }

                else if (intasistencia==0)
                {

                    String outPut = fechalistview+""+name+ "  Falta ";
                    employeeList.add(createEmployee("", outPut));
                }









            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "No ha seleccionado las fecha", Toast.LENGTH_LONG).show();
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











