package com.example.taxidrivers;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

//    // Initialize Crash Reporting.
//    ParseCrashReporting.enable(this);

//    // Enable Local Datastore.
//    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "UWERoaNqloQ328PNjUWf7dSIg6GlyTQgSCPvbypE", "tiZof4anDjkxFkiImUkdmeFxHwLqDUJAyTRfVT9k");

    
    //Enable to receive push
    PushService.setDefaultPushCallback(this,NotificationActivity.class);
    ParseInstallation pi = ParseInstallation.getCurrentInstallation();
    
    //Register a channel to test push channels
    Context ctx = this.getApplicationContext();
    PushService.subscribe(ctx, "Texi", HomeActivity.class);
    
    pi.saveEventually();

//    /*ParseUser.enableAutomaticUser();
//    ParseACL defaultACL = new ParseACL();
//    // Optionally enable public read access.
//    // defaultACL.setPublicReadAccess(true);
//    */ParseACL.setDefaultACL(defaultACL, true);
    
  }
}
