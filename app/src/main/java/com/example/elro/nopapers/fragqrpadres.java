package com.example.elro.nopapers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Bitmap;import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.io.InputStream;


public class fragqrpadres extends Fragment {


    private Button button;

    private ImageView imageView;
    private TextView clave;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
        RelativeLayout llLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_fragqrpadres, container, false);


        imageView = (ImageView) llLayout.findViewById(R.id.imageView6);
        clave = (TextView)llLayout.findViewById(R.id.textvclave);


        MainActivity activity = (MainActivity) getActivity();
        //String nombre =activity.getMyData();
        //String id =activity.getidusu();
        String text2Qr=activity.getMyDataClave();

        clave.setText(text2Qr);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,250,250);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


        button = (Button) llLayout.findViewById(R.id.salvar);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{


                    imageView.setDrawingCacheEnabled(true);
                    Bitmap b = imageView.getDrawingCache();
                    MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), b,"qrtutor", "SchoolActive");
                    Toast.makeText(getActivity(), "Imagen guardadada en galeria", Toast.LENGTH_SHORT).show();


                }catch(Exception e){
                    Toast.makeText(getActivity(), "Error al guardar", Toast.LENGTH_SHORT).show();


                }

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




}