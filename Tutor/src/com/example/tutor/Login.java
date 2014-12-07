package com.example.tutor;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	EditText txtUsername, txtPassword;
	Button login;
	JSONParser jsonParser = new JSONParser();
	String respuesta, id_tutor;
	private static String url_login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		url_login =  getString(R.string.url)+"login.php";
		login = (Button) findViewById(R.id.button_login);
		txtUsername = (EditText) findViewById(R.id.usuario);
        txtPassword = (EditText) findViewById(R.id.password);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new importarSesion().execute();
			}
		});
	}
	
	
	private class importarSesion extends AsyncTask<Void, Integer, Void>{
		private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
        	super.onPreExecute();
        	pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Iniciando sesión ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params){
        	List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        	parametros.add(new BasicNameValuePair("usuario", txtUsername.getText().toString()));
        	parametros.add(new BasicNameValuePair("clave", txtPassword.getText().toString()));
			JSONObject json = jsonParser.makeHttpRequest(url_login,"POST", parametros);
			if(json != null){
				try {
					if (json.getInt("sucess") == 1) {
						id_tutor = json.getString("b");
						respuesta = json.getString("a");
					}else {
						respuesta=json.getString("a").toString();
					}
				} catch (JSONException e) {
					respuesta = "Error de conexión";
				}
			}
        	return null;	
        }
  		@Override
  		protected void onPostExecute(Void result){
  			pDialog.dismiss();
  			if( id_tutor != null ){
  				Intent i = new Intent (Login.this, Tutorados.class);
				i.putExtra("nombre", respuesta);
				i.putExtra("id_tutor", id_tutor);
				finish();
				startActivity(i);
				mostrarAlerta("Bienvenido "+respuesta);
				
  			}else{
  				mostrarAlerta(respuesta);
  			}
  		}
	}
	public void mostrarAlerta(String mensaje){
		 Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
	     toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	     toast.show();
	}
}