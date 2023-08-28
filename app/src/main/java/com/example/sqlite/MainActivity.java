package com.example.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText et1, et2, et3, et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
    }

    public void insertar(View v) {
        // Método para insertar registros en la base de datos

        // Nombre de la BD y primera versión
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        // Instancia de la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        // Capturamos los datos de los campos de texto
        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String colegio = et3.getText().toString();
        String nromesa = et4.getText().toString();

        // Creamos un objeto ContentValues para almacenar los valores a insertar
        ContentValues registro = new ContentValues();
        registro.put("cedula", cedula);
        registro.put("nombre", nombre);
        registro.put("colegio", colegio);
        registro.put("nromesa", nromesa);

        // Insertamos los registros en la tabla "votantes"
        bd.insert("votantes", null, registro);

        // Cerramos la conexión con la base de datos
        bd.close();

        // Limpiamos los campos de texto
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        // Mostramos un mensaje de éxito
        Toast.makeText(this, "Se cargaron los datos de la persona", Toast.LENGTH_SHORT).show();
    }

    public void consulta(View v) {
        // Método para consultar registros en la base de datos

        // Nombre de la BD y primera versión
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        // Instancia de la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        // Capturamos la cédula ingresada
        String cedula = et1.getText().toString();

        // Realizamos la consulta
        Cursor fila = bd.rawQuery("SELECT nombre, colegio, nromesa FROM votantes WHERE cedula = " + cedula, null);

        if (fila.moveToFirst()) {
            // Si hay registros, mostramos los datos en los EditText
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            et4.setText(fila.getString(2));
        } else {
            // Si no hay registros, mostramos un mensaje de error
            Toast.makeText(this, "No existe una persona con dicha cédula", Toast.LENGTH_SHORT).show();
        }

        // Cerramos la conexión con la base de datos
        bd.close();
    }

    public void eliminar(View v) {
        // Método para eliminar registros de la base de datos

        // Nombre de la BD y primera versión
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        // Instancia de la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        // Capturamos la cédula ingresada
        String cedula = et1.getText().toString();

        // Realizamos la eliminación
        int cant = bd.delete("votantes", "cedula=" + cedula, null);

        // Cerramos la conexión con la base de datos
        bd.close();

        // Limpiamos los campos
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        // Mostramos mensaje según el resultado de la eliminación
        if (cant == 1) {
            Toast.makeText(this, "Se borró la persona con dicho documento", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe una persona con dicho documento", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View v) {
        // Método para modificar registros en la base de datos

        // Nombre de la BD y primera versión
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        // Instancia de la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        // Capturamos la cédula ingresada
        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String colegio = et3.getText().toString();
        String nromesa = et4.getText().toString();

        // Inicializamos los registros a modificar
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("colegio", colegio);
        registro.put("nromesa", nromesa);

        // Actualizamos los registros a partir de la cédula ingresada
        int cant = bd.update("votantes", registro, "cedula=" + cedula, null);

        // Cerramos la conexión con la base de datos
        bd.close();

        // Mostramos mensaje según el resultado de la modificación
        if (cant > 0) {
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe una persona con dicho documento", Toast.LENGTH_SHORT).show();
        }
    }

}
