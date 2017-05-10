package com.example.elro.nopapers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mainfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mainfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainfragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public String name,cuenta;
    TextView cuentatipo,nombreprofe;


    private OnFragmentInteractionListener mListener;

    public mainfragment() {
    }

    public static mainfragment newInstance(String param1, String param2) {
        mainfragment fragment = new mainfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RelativeLayout llLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_mainfragment, container, false);
        MainActivity activity = (MainActivity) getActivity();
        name = activity.getMyData();
        cuenta = activity.getMyDatacuenta();

        cuentatipo=(TextView)llLayout.findViewById(R.id.nombreprofe);
        nombreprofe=(TextView)llLayout.findViewById(R.id.profesor);

        if(cuenta.equals("alumno")){
            cuentatipo.setText("Alumno(a)");
        }
        else if(cuenta.equals("profesor")){
            cuentatipo.setText("Profesor(a)");
        }

        nombreprofe.setText(name);

        return llLayout;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void probando (View v){

    }
}
