package com.example.tutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Mensajes extends Fragment {
	View rootView;
	EditText mensaje;
	Button enviar;
	ListView list;
	
	TextView ver;
	TextView name;
	TextView api;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	//URL to get JSON Array
	private static String url, url_mensaje;
	//JSON Node Names
	private static final String TAG_OS = "mensajes";
	private static final String TAG_VER = "a";
	private static final String TAG_NAME = "b";
	private static final String TAG_API = "c";
	JSONArray lista_tutorados = null;
	String id_tutor, nombre, cct, id_alumno;
	String respuesta;
	ListAdapter adapter;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fm_mensajes, container, false);
		list=(ListView) rootView.findViewById(R.id.list);
		mensaje = (EditText) rootView.findViewById(R.id.mensaje);
		enviar = (Button) rootView.findViewById(R.id.enviar);
		id_alumno = getActivity().getIntent().getStringExtra("id_alumno");
		id_tutor = getActivity().getIntent().getStringExtra("id_tutor");
		url =  getString(R.string.url)+"mensajes.php";
		url_mensaje=  getString(R.string.url)+"newMensaje.php";
		oslist = new ArrayList<HashMap<String, String>>();
		new JSONParse().execute();
		
		enviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new JSONEnviarMensaje().execute();
			}
		});
		
		return rootView;
	}
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
             ver = (TextView) rootView.findViewById(R.id.nombre);
             name = (TextView) rootView.findViewById(R.id.plantel);
             api = (TextView) rootView.findViewById(R.id.nivel);
             list.invalidateViews();
             pDialog = new ProgressDialog(rootView.getContext());
             pDialog.setMessage("Consultando los mensajes ...");
             pDialog.setIndeterminate(false);
             pDialog.setCancelable(false);
             pDialog.show();
		}
		@Override
        protected JSONObject doInBackground(String... args) {
	        JSONParser jParser = new JSONParser();
	        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        	parametros.add(new BasicNameValuePair("id_alumno", id_alumno));
	        JSONObject json = jParser.makeHttpRequest(url,"POST", parametros);
	        return json;
		}
		@Override
        protected void onPostExecute(JSONObject json) {
        pDialog.dismiss();
        try {
            lista_tutorados = json.getJSONArray(TAG_OS);
            for(int i = 0; i < lista_tutorados.length(); i++){
            	JSONObject c = lista_tutorados.getJSONObject(i);
	            String ver = c.getString(TAG_NAME);
	            String name = c.getString(TAG_VER);
	            String api = c.getString(TAG_API);
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put(TAG_VER, ver);
	            map.put(TAG_NAME, name);
	            // map.put(TAG_API, api);
	            oslist.add(map);
	            
	            list=(ListView) rootView.findViewById(R.id.list);
	                     
	            adapter = new SimpleAdapter(rootView.getContext(), oslist,
                R.layout.list_v,
                	new String[] { TAG_VER,TAG_NAME, TAG_API }, new int[] {
	                    R.id.nombre,R.id.plantel, R.id.nivel});
	            		
	            		list.setAdapter(adapter);
	            		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            			@Override
	                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                    	Toast.makeText(rootView.getContext(), "You Clicked at "+oslist.get(+position).get("c"), Toast.LENGTH_SHORT).show();
	                    }
	                });
            	}
        	} catch (JSONException e) {
        		e.printStackTrace();
        	}
       }
    }
	private class JSONEnviarMensaje extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
             ver = (TextView) rootView.findViewById(R.id.nombre);
             name = (TextView) rootView.findViewById(R.id.plantel);
             api = (TextView) rootView.findViewById(R.id.nivel);
             pDialog = new ProgressDialog(rootView.getContext());
             pDialog.setMessage("Enviando el mensaje...");
             pDialog.setIndeterminate(false);
             pDialog.setCancelable(false);
             pDialog.show();
		}
		@Override
        protected JSONObject doInBackground(String... args) {
	        JSONParser jParser = new JSONParser();
	        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        	parametros.add(new BasicNameValuePair("mensaje", mensaje.getText().toString() ));
        	parametros.add(new BasicNameValuePair("id_alumno", id_alumno));
        	parametros.add(new BasicNameValuePair("id_tutor", id_tutor));
	        JSONObject json = jParser.makeHttpRequest(url_mensaje,"POST", parametros);
	        return json;
		}
		@Override
        protected void onPostExecute(JSONObject json) {
        pDialog.dismiss();
	    	if(json != null){
				try {
					if (json.getString("a") =="true" ) {
						respuesta = "insertado";
						mensaje.setText("");
						
						new JSONParse().execute();
					}else {
						respuesta="error";
					}
				} catch (JSONException e) {
					respuesta = "Error de conexión";
				}
			}
			
       }
	}
}