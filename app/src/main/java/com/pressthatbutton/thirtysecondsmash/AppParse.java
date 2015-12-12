package com.pressthatbutton.thirtysecondsmash;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;

/**
 * Created by JFaucette on 11/23/15.
 */
public class AppParse extends Application {

    private String ApplicationID = "E7XJt4pIywhKbpZ0tJKIpqwtHKmqasv1ZmfIk5vo";
    private String ClientKey = "eEg41XmXSQxHKxqZB9nGdB1c2iKYti1GF8C9SHFi";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, ApplicationID, ClientKey);
        ParseFacebookUtils.initialize(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}

