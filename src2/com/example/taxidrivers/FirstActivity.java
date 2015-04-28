package com.example.taxidrivers;


import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends Activity implements OnClickListener{

	SharedPreferences shed;
	SharedPreferences.Editor editor;
	Button bt;
	EditText edt,edt1,edt2,edt3;
	Ksoap sop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		
		shed=getSharedPreferences("Taxidriver", Context.MODE_PRIVATE);
		editor=shed.edit();
		bt=(Button) findViewById(R.id.taxi_reg_submit);
		edt=(EditText) findViewById(R.id.driver_name);
		edt1=(EditText) findViewById(R.id.taxi_number);
		edt2=(EditText) findViewById(R.id.contact_number);
		edt3=(EditText) findViewById(R.id.location);
		sop=new Ksoap();
		bt.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==bt)
		{
		if(edt.equals("") || edt1.equals("") || edt2.equals("") || edt3.equals("") ){
		
			Toast.makeText(FirstActivity.this, "Fill all fields", Toast.LENGTH_LONG).show();
		}
		else
		{
			
		    for_destination fd=new for_destination();
			fd.execute();
		}
		}
		
	}
	
	
	class for_destination extends AsyncTask<String, String, String>
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
		String result=null;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			NAMESPACE = "http://facile/";
			URL ="http://172.30.16.95/mainproject/facile_serviceService";
			METHOD_NAME = "taxiregistration";
			SOAP_ACTION = "http://facile/taxiregistration";
		
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
	       			
	       			
	       			request.addProperty("drivers_name",edt.getText().toString());
	       			request.addProperty("taxi_number",edt1.getText().toString());
	       			request.addProperty("contact_number",edt2.getText().toString());
	       			request.addProperty("location",edt3.getText().toString());
	       			result=sop.getResponseData(URL,SOAP_ACTION,request);
//	       			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//	       			//envelope.dotNet = true;
//	                envelope.setOutputSoapObject(request);
//	                Log.d("Exception-----------", "hiq");
//	                androidHttpTransport.debug = true;
//	                Log.d("Exception-----------", "hiw");
//					androidHttpTransport.call(SOAP_ACTION,envelope);
//					
//			        SoapPrimitive so1 = (SoapPrimitive)envelope.getResponse(); 
//			        Log.d("after so", "gettingit");
//
//	       			return so1.toString();
	       			
	               Toast.makeText(getBaseContext(), result, 10).show(); 
	            
	        }
	        catch(Exception e)
	        {
	        	Log.d("Exception-----------", ""+e.toString());
	        }
	        Log.d("Exception-----------", "hi");
			return result;
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("Exception-----------", "result");
			Toast.makeText(FirstActivity.this, ""+result, Toast.LENGTH_LONG).show();
			if(result.equals("failed"))
				Toast.makeText(FirstActivity.this, "failed "+result, Toast.LENGTH_LONG).show();
			else
			{
				Toast.makeText(FirstActivity.this, "Successfully registered"+result, Toast.LENGTH_LONG).show();
				editor.putString("taxi_id", result);
				ParseUser user=new ParseUser();
				user.setUsername(edt.getText().toString());
				user.setPassword(edt1.getText().toString());
				
				user.signUpInBackground(new SignUpCallback() {
				
				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub
					PushService.subscribe(FirstActivity.this, "taxi"+edt1.getText().toString(), HomeActivity.class);
					Toast.makeText(getApplicationContext(), "hiiiiiiiiii", Toast.LENGTH_LONG).show();
				}
			});
				PushService.subscribe(FirstActivity.this, "taxi"+edt1.getText().toString(), NotificationActivity.class);
				editor.commit();
			}
		}
		
	}

}
