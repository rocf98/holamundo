package com.example.elro.nopapers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class tutorados extends Fragment {
    private String jsonResult;
    private String url = "http://schoolactive.xyz/VerGruposProfe.php";
    private ListView listView;
    public String name,cuenta, nombredegrupo;
    private Map<String, String> params;
    public String clave,name2, nombregrupo, idGrupo, remplazado, claveacc;
    private Button button;
     public Activity activity = getActivity();





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        RelativeLayout llLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_tutorados, container, false);


        button = (Button) llLayout.findViewById(R.id.btnscaner);
        activity =getActivity();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
               // integrator.setPrompt(" ");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

               /* Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                IntentIntegrator integrator = new IntentIntegrator(activity);
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                                integrator.setPrompt("Asistencia del ");
                                integrator.setCameraId(0);
                                integrator.setOrientationLocked(false);
                                integrator.setBeepEnabled(false);
                                integrator.setBarcodeImageEnabled(false);
                                integrator.initiateScan();




                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

                /*actualizarlistarequest actualizarlistarequest = new actualizarlistarequest(idGrupo,fecha, responseListener);
                RequestQueue queue = Volley.newRequestQueue(grupointerfaz.this);
                queue.add(actualizarlistarequest); */
            }

        });


















        return llLayout;








    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.main, menu);

    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(getActivity(), "Finalizado", Toast.LENGTH_LONG).show();


                Intent abrirnotificarpadre = new Intent(getActivity(), NotificarAlPadre.class);

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
                                                Toast.makeText(getContext(), "No se pudo verificar asistencia  " , Toast.LENGTH_LONG).show();

                                                e.printStackTrace();
                                            }


                                        }
                                    };
                                    nombreusuario=resultqr;
                                    subirasistenciarequest subirasistenciarequest = new subirasistenciarequest(resultqr, idGrupo,fecha, responseListener);
                                    RequestQueue queue1 = Volley.newRequestQueue(getActivity());
                                    queue1.add(subirasistenciarequest);
                                }





                                else {
                                    Toast.makeText(getContext(), "El alumno no existe", Toast.LENGTH_SHORT).show();

                                }
                            }

                        });

                    }
                };
                tr.start();

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Asistencia del ");
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
    */
}