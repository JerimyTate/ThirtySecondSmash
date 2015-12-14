package com.pressthatbutton.thirtysecondsmash;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

/**
 * Created by JFaucette on 11/23/15.
 */
public class AppParse extends Application {

    private String ApplicationID = "E7XJt4pIywhKbpZ0tJKIpqwtHKmqasv1ZmfIk5vo";
    private String ClientKey = "eEg41XmXSQxHKxqZB9nGdB1c2iKYti1GF8C9SHFi";
    public static ParseUser _parseUser;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Score.class);
        ParseObject.registerSubclass(ParseUser.class);

        _parseUser = new ParseUser();
        _parseUser.setUsername("Unknown User");

        Parse.initialize(this, ApplicationID, ClientKey);
        ParseUser.enableRevocableSessionInBackground();

        ParseFacebookUtils.initialize(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

