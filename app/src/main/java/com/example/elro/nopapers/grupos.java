package com.example.elro.nopapers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class grupos extends Fragment {
    private String jsonResult;
    private String url = "http://schoolactive.xyz/VerGruposProfe.php";
    private ListView listView;
    public String name,cuenta, nombredegrupo;
    private Map<String, String> params;
    public String clave,name2, nombregrupo, idGrupo, remplazado, claveacc;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity    faActivity  = (FragmentActivity)    super.getActivity();
        RelativeLayout llLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_grupos, container, false);



        listView = (ListView) llLayout.findViewById(R.id.listView1);



        accessWebService();
        MainActivity activity = (MainActivity) getActivity();
        name = activity.getMyData();
        cuenta = activity.getMyDatacuenta();




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


    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://schoolactive.xyz/VerGruposProfe.php");


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("name", name ));
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
                nombredegrupo = jsonChildNode.optString("nombregrupo");
                name2 = jsonChildNode.optString("namemateria");
                clave = jsonChildNode.optString("claveacc");




                String outPut = clave + "         " + name2 + "         " + nombredegrupo;

                employeeList.add(createEmployee("", outPut));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), employeeList,
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
                        remplazado = m.replaceAll("");
                    }
                }
                String cadena_a,cadena_c=(remplazado);
                cadena_a = cadena_c.substring(0,6);
                claveacc=cadena_a;

                String cadena_d,cadena_e=(remplazado);
                cadena_d = cadena_e.substring(6);
                nombregrupo = cadena_d;




                Intent opengrupo = new Intent(getActivity(), grupointerfaz.class);
                opengrupo.putExtra("claveacc", claveacc);
                opengrupo.putExtra("nombregrupo", nombregrupo);
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