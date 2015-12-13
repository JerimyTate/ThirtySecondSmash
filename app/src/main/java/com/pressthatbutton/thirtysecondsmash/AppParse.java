package com.pressthatbutton.thirtysecondsmash;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseSession;
import com.parse.ParseUser;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

/**
 * Created by JFaucette on 11/23/15.
 */
public class AppParse extends Application {

    private String ApplicationID = "E7XJt4pIywhKbpZ0tJKIpqwtHKmqasv1ZmfIk5vo";
    private String ClientKey = "eEg41XmXSQxHKxqZB9nGdB1c2iKYti1GF8C9SHFi";
    public static ParseUser _parseUser;
    public static ParseSession _parseSession;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Score.class);
        ParseObject.registerSubclass(ParseUser.class);
        ParseObject.registerSubclass(ParseSession.class);

        _parseUser = new ParseUser();
        _parseUser.setUsername("Unknown User");
        _parseSession = new ParseSession();

        Parse.initialize(this, ApplicationID, ClientKey);
        ParseFacebookUtils.initialize(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

