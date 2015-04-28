package com.example.taxidrivers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.text.StaticLayout;
import android.util.Log;
import android.widget.Toast;

public class Ksoap {

	public SoapObject getSoapObject(String nameSpace,String methodname)
	{
		return new SoapObject(nameSpace,methodname);
	}
	public String getResponseData(String url,String soapAction,SoapObject request)
	{
		String result="";
		SoapSerializationEnvelope envelop=new SoapSerializationEnvelope(SoapEnvelope.VER11);
		//envelop.dotNet=true;
		envelop.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport=new HttpTransportSE(url);
		
		//androidHttpTransport.debug=true;s
		try{
			androidHttpTransport.call(soapAction,envelop);
			SoapPrimitive response=(SoapPrimitive)envelop.getResponse();
			result=response.toString();
		}
		catch(Exception e)
		{
			
			Log.d("Exception at soap: ", e.toString());
		}
		
		finally{
			
			return result;
		
		  }
	}
	
	public SoapObject getsopobjectnew(String url,String soapAction,SoapObject request)
	{
		 SoapObject so=null;
		SoapSerializationEnvelope envelop=new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelop.dotNet=true;
		envelop.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport=new HttpTransportSE(url);
	
		androidHttpTransport.debug=true;
		try{
			androidHttpTransport.call(soapAction,envelop);
			request=(SoapObject)envelop.getResponse();
			
		}
		catch(Exception e)
		{
			
			Log.d("Exception at soap: ", e.toString());
		}
		
		finally{
			
			return request;
		
		  }
	}
}
