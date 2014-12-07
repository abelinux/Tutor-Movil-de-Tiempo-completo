package com.example.tutor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class Fragment_Mensajes extends Fragment {
	View rootView;
	TextView leyenda;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fm_mensajes, container, false);
		
		leyenda = (TextView) rootView.findViewById(R.id.leyenda); 
		leyenda.setText("TEXTO MODIFICADO");
		return rootView;
	}
}
