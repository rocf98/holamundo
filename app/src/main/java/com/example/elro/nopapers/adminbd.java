package com.example.elro.nopapers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario1 on 20/07/2016.
 */
public class adminbd extends SQLiteOpenHelper {
    public adminbd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL("create table categorias(idcategoria Integer primary key, categoria text, tipo text);");
      //  db.execSQL("create table conceptos(idIngreso Integer  primary key,concepto text,categoria text, descripcion text,fecha text ,tipo text);");
        db.execSQL("CREATE TABLE listaasistencia (nombrealumno varchar(100) COLLATE utf8_unicode_ci NOT NULL,nameusuario varchar(100) COLLATE utf8_unicode_ci NOT NULL,hora time NOT NULL,fecha date NOT NULL,\n" +
                "asistencia int(10) NOT NULL,falta int(10) NOT NULL,faltajustificada int(10) NOT NULL,totalasistencias int(10) NOT NULL,clavegrupo varchar(100) COLLATE utf8_unicode_ci NOT NULL,idtableasistencia int(11) NOT NULL AUTO_INCREMENT,\n" +
                "PRIMARY KEY (idtableasistencia)); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
