package com.example.practicawebapiempleados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView miRecicler;
    private RecyclerView.Adapter miAdpater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leerServicio();

        //Buscamos el control para cargar los datos
        miRecicler= (RecyclerView) findViewById(R.id.RV1);
        //añadimos que el tamaño del RecyclerView no se cambiará
        // que tiene hijos(items) que tienen anchura y altura fijas.Permite que el recyclerView optimice mejor.
        miRecicler.setHasFixedSize(true);
        miRecicler.setLayoutManager((new LinearLayoutManager(this)));

    }


    public void leerServicio() {
        try {
            String url = "https://webapiempleado.azurewebsites.net/api/empleados";
            new HttpAsyncTask().execute(url);
        } catch (Exception e) {
// manage exceptions
            System.out.println(e.toString());
        }
    }

    public String recuperarContenido(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        String resultado = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse respuesta = null;
        InputStream stream = null;
        try {
            respuesta = httpclient.execute(httpget);
            HttpEntity entity = respuesta.getEntity();

            if (entity != null) {
                stream = entity.getContent();
                resultado = convertirInputToString(stream);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {

                System.out.println(e.toString());
            }
        }
        return resultado;
    }

    private String convertirInputToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String resultado = "";
        while ((line = bufferedReader.readLine()) != null)
            resultado += line;
        inputStream.close();
        return resultado;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return recuperarContenido(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Datos recibidos!", Toast.LENGTH_LONG).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                List<Empleados> lista = convertirJsonPlantillas(jsonarray);

                //Especificamos el adaptador con la lista a visualizar
                miAdpater = new Adaptador(lista);
                miRecicler.setAdapter((miAdpater));

                /*String datos = "";
                for (Empleados d : lista) {
                    datos += d.toString() + "\n";
                }
                //txtdatos.setText(datos); */
            } catch (JSONException e) {
                System.out.println(e.toString());
               // txtdatos.setText(e.getMessage());
            }

        }
    }

    public List<Empleados> convertirJsonPlantillas(JSONArray jsonarray) throws JSONException {
        List<Empleados> lista = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            Empleados emp = new Empleados();
            String idEmp,apel,ofi,sal, dept,comi,fecha, jefed ;


            idEmp = jsonarray.getJSONObject(i).optString("idEmpleado").toString();
            apel = jsonarray.getJSONObject(i).optString("apellido").toString();
            ofi = jsonarray.getJSONObject(i).optString("oficio").toString();
            sal = jsonarray.getJSONObject(i).optString("salario").toString();
            dept = jsonarray.getJSONObject(i).optString("departamentoNo").toString();
            comi = jsonarray.getJSONObject(i).optString("comision").toString();
            fecha = jsonarray.getJSONObject(i).optString("fechaAlta").toString();
            jefed = jsonarray.getJSONObject(i).optString("jefeDirecto").toString();

            emp.setIdEmpleado(idEmp);
            emp.setApellido(apel);
            emp.setOficio((ofi));
            emp.setSalario(sal);
            emp.setDepartamento(dept);
            emp.setComision(comi);
            emp.setFechaAlta(fecha);
            emp.setJefeDirecto(jefed);
            lista.add(emp);
        }
        return lista;
    }


}