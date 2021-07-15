package com.example.practicawebapiempleados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class Ventana2 extends AppCompatActivity {
    private TextView idEmpleadov2, apellidov2,  oficiov2, salariov2, comisionv2, fechaaltav2, jefedirectov2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana2);
        this.idEmpleadov2 = (TextView) findViewById(R.id.textViewEmp_detalle);
        this.apellidov2=(TextView) findViewById(R.id.txtApellido_detalle);
        this.oficiov2=(TextView) findViewById(R.id.textViewOficio_detalle);
        this.salariov2=(TextView) findViewById(R.id.textViewSalario_detalle);
        this.comisionv2=(TextView) findViewById(R.id.textViewComision_detalle);
        this.fechaaltav2=(TextView) findViewById(R.id.textViewFechaAltav2);
        this.jefedirectov2=(TextView) findViewById(R.id.textView2);

        //Recogemos los parametros enviados por el primer Activity a través del método getExtras
        Bundle bundle = getIntent().getExtras();
        String idem = bundle.getString("NUMEROEMPLEADO");
        leerServicio(idem);

    }
    public void cerrarVentana(View view){
        finish();

    }
    public void leerServicio(String idem) {
        try {
            String urlbase = "https://webapiempleado.azurewebsites.net/api/empleados";
            String url= urlbase+ "idem";
            new Ventana2.HttpAsyncTask().execute(url);
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

                String datos = "";
                for (Empleados d : lista) {
                    idEmpleadov2.setText(d.getIdEmpleado());
                    apellidov2.setText(d.getApellido());
                    oficiov2.setText(d.getOficio());
                    salariov2.setText(d.getSalario());
                    comisionv2.setText(d.getComision());
                    fechaaltav2.setText(d.getFechaAlta());
                    jefedirectov2.setText(d.getJefeDirecto());
                }
                    /*
                    d.getApellido();
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