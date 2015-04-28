package com.example.taxidrivers;

import com.parse.ParsePush;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NotificationActivity extends Activity {

	TextView txt;
	Button a,d;
	String array[]=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		txt=(TextView) findViewById(R.id.request);
		a=(Button) findViewById(R.id.accept);
		d=(Button) findViewById(R.id.decline);
		
		Bundle b=getIntent().getExtras();
			String data=b.getString("com.parse.Data");
			array=data.split(" ");
				txt.setText(array[0]+" "+array[1]);
			a.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ParsePush push=new ParsePush();
					push.setChannel("user"+array[1]);
					push.setMessage("Please wait, Coming");
					push.sendInBackground();
				}
			});
			
			d.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ParsePush push=new ParsePush();
					push.setChannel("user"+array[1]);
					push.setMessage("Sorry, can't reach there");
					push.sendInBackground();
				}
			});
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

}
