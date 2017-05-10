package com.example.elro.nopapers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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


public class gruposalumnos extends Fragment implements View.OnClickListener {


    public String nombre,email,clavefinalf,usuario;
    public EditText txtcode;
    public Button btnsubir;
    private String jsonResult;
    private String url = "http://pruebaschoolactive.esy.es/listviewgalumno.php";
    private ListView listView;
    public String name,cuenta;
    private Map<String, String> params;
    public String creador,escuela,nivel,name2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout llLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_gruposalumnos, container, false);
        getActivity().setTitle("Grupos") ;
        MainActivity activity = (MainActivity) getActivity();
        nombre = activity.getMyData();
        email = activity.getMyDataemail();
        usuario = activity.getMyDatausuario();
        accessWebService();
        txtcode = (EditText) llLayout.findViewById(R.id.codigo);
        btnsubir = (Button) llLayout.findViewById(R.id.btncode);
        clavefinalf= txtcode.getText().toString();
        listView= (ListView) llLayout.findViewById(R.id.listview);

                btnsubir.setOnClickListener(this);

        return llLayout;




                    }


    @Override
    public void onClick(View v) {

Thread tr= new Thread() {


    @Override
    public void run() {
        final String resultado= enviarDatosGET(txtcode.getText().toString());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

              int r=obtDatosJSON(resultado);

                if(r>0){


                    final String clavefinal = txtcode.getText().toString();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {


                                   // Toast.makeText(getActivity(), "Registro Corre", Toast.LENGTH_SHORT).show();
                                    Intent nextgroup = new Intent(getActivity(), NextRegisterAlumn.class);
                                    startActivity(nextgroup);

                                    txtcode.setText("");


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };

                    coderequest coderequest = new coderequest(clavefinal,usuario,nombre, responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(getActivity());
                    queue1.add(coderequest);
                }





                else {
                    Toast.makeText(getActivity(), "Codigo incorrecto", Toast.LENGTH_SHORT).show();
                    txtcode.setText("");
                }
            }

        });

    }
};
tr.start();
    }




    public String enviarDatosGET(String code ){

    URL url=null;
    String linea="";
    int respuesta=0;
    StringBuilder resul=null;

    try{
        url=new URL("http://pruebaschoolactive.esy.es/loggincode.php?code="+code);
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



    public  int obtDatosJSON(String response){


        int res=0;

        try {

           JSONArray json= new JSONArray(response);
            if(json.length()>0){

                res=1;

            }

        }catch(Exception e){}
        return res;


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
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://pruebaschoolactive.esy.es/listviewgalumno.php");


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("usuario", usuario ));
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
            JSONArray jsonMainNode = jsonResponse.optJSONArray("grupos");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("nombregrupo");

                //creador = jsonChildNode.optString("creador");
                //escuela = jsonChildNode.optString("nameescuela");
               // nivel = jsonChildNode.optString("nivel");

                String outPut = name;

                employeeList.add(createEmployee("", outPut));
            }
        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(getActivity(), "Sin grupos", Toast.LENGTH_SHORT).show();



        }




        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), employeeList,
                android.R.layout.simple_list_item_1,
                new String[] { "" }, new int[] { android.R.id.text1 });
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedFromList =(listView.getItemAtPosition(position).toString());


                Intent opengrupo = new Intent(getActivity(), NextRegisterAlumn.class);
                opengrupo.putExtra("nombregrupo", selectedFromList);

                startActivity(opengrupo);

            }
        });
    }


    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }


}
