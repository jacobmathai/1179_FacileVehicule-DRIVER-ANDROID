package com.example.taxidrivers;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;


public class splash extends Activity {
	
	private static int progress;
	private ProgressBar progressBar;
	private int progressStatus = 0;
	private Handler handler = new Handler();
    final Context mContext = this; 
  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash); 
      
        progress=0;
        progressBar=(ProgressBar)findViewById(R.id.progressBar1);
        splash.this.progressBar.setMax(200);
        new Thread()
        {
        	
        	public void run()
        	{
        		//---do some work here---
        		while (progressStatus < 200)
        		{
        			progressStatus = doSomeWork();
        			//---Update the progress bar---
        			handler.post(new Runnable() 
        			{
						
						public void run() {
							progressBar.setProgress(progressStatus);
							
						}
					});   				
        		}
        		
        		//---hides the progress bar---
        		handler.post(new Runnable() 
        		{
					public void run() {
						//---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
        				progressBar.setVisibility(4);
        				 
        				splash.this.finish();
        				Intent home=new Intent(splash.this,HomeActivity.class);
        				startActivity(home);
        				}
					
				});
        	}
        	
        	//---do some long lasting work here---
        	private int doSomeWork()
        	{
        		try {
        				//---simulate doing some work---
        				Thread.sleep(20);
        			} catch (InterruptedException e)
        			{
        				e.printStackTrace();
        			}
        			return ++progress;
       		}
        	
        }.start();
        			
	}
	
	}

