package com.example.taxidrivers;



import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.taxidrivers.FirstActivity.for_destination;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class HomeActivity extends Activity implements OnClickListener{

		Button bt;
		ToggleButton tb;
		SharedPreferences shed;
		String status="0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		bt=(Button)findViewById(R.id.register_button);
		tb=(ToggleButton)findViewById(R.id.toggleButton);
		bt.setOnClickListener(this);
		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					if(isChecked){
						status="1";
					}
					else{
						status="0";
					}
					
					for_destination1 fd=new for_destination1();
					fd.execute();
		
			}
		});
	    
		shed=getSharedPreferences("Taxidriver", Context.MODE_PRIVATE);
		if(shed.getString("taxi_id", "").equals(""))
		{
			
		}
		else
		{
			bt.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent in=new Intent(HomeActivity.this,FirstActivity.class);
		startActivity(in);
	}


class for_destination1 extends AsyncTask<String, String, String>
{

	SoapObject so=null;
	SoapPrimitive so1=null;
	int i=0;
	String response="";
	
	String NAMESPACE; 
	String URL;
	String METHOD_NAME;
	String SOAP_ACTION;
	ArrayList<String> destination;
	String resp="";
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		
		NAMESPACE = "http://facile/";
		URL ="http://172.30.16.95/mainproject/facile_serviceService";
		METHOD_NAME = "taxiUpdate";
		SOAP_ACTION = "http://facile/taxiUpdate";
	
		super.onPreExecute();
		
	}
	

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		 
	
        try
        {
        		Log.d("nameeee",URL);
        		
        		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
       			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
       			
       		    request.addProperty("taxi_id",shed.getString("taxi_id", "null"));
       		    request.addProperty("taxi_status",status);
       			
       			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
       			//envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                Log.d("Exception-----------", "hiq");
                androidHttpTransport.debug = true;
                Log.d("Exception-----------", "hiw");
				androidHttpTransport.call(SOAP_ACTION,envelope);
				Log.d("name","ahjdsadkdgsadjgsajds");
		        SoapPrimitive so1 = (SoapPrimitive)envelope.getResponse(); 


       			return so1.toString();
                
            
        }
        catch(Exception e)
        {
        	Log.d("Exception-----------", ""+e.toString());
        }
        return "some problem";   
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d("Post-----------", "result");
		if(result.equals("success"))
		Toast.makeText(HomeActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(HomeActivity.this, "Updated", Toast.LENGTH_LONG).show();
	}
	

}
}