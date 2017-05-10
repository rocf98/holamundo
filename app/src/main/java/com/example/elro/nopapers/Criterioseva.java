package com.example.elro.nopapers;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elro on 30/05/2016.
 */
public class Criterioseva extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://pruebaschoolactive.esy.es/CreacionGrupos.php";
    private Map<String, String> params;
    private String name;



    public Criterioseva(String namemateria, String creador, String nameescuela, String nivel, String claveacc, String nombregrupo, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("namemateria", namemateria);
        params.put("creador", creador);
        params.put("nameescuela", nameescuela);
        params.put("nivel", nivel);
        params.put("claveacc", claveacc);
        params.put("nombregrupo",nombregrupo);


        /*
        params.put("examen", examen);
        params.put("libreta", libreta);
        params.put("tareas", tareas);
        params.put("participacion", participacion);
        params.put("trabajos", trabajos);
        params.put("asistencia", asistencia);
        params.put("otros", otros);*/





    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
