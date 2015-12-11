package com.pressthatbutton.thirtysecondsmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LoginScreen extends AppCompatActivity {
    private Button btnLogin;
    private Button btnFacebook;
    private Button btnSignUp;

    protected String name = null;
    protected String email = null;

    public ParseUser parseUser;

    private EditText userName;
    private EditText password;

    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userName = (EditText) findViewById(R.id.edit_txt_user_name);
        password = (EditText) findViewById(R.id.edit_txt_password);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bring up Dialog box to SAVE new Parse User.
                //NEED TO WORK ON!
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnFacebook = (Button) findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginScreen.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                            saveNewUser();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            getUserDetailsFromFB();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                            Log.d("MyApp", "User logged in through Facebook!");
                        }
                    }
                });
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calls method FieldsAreEmpty() that returns a boolean.
                if (!FieldsAreEmpty()) {
                    //Fields are not empty
                    final ProgressDialog dlg = new ProgressDialog(LoginScreen.this);
                    dlg.setTitle("Hold up!");
                    dlg.setMessage("We are logging you in... Chill");
                    dlg.show();
                    ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null && parseUser != null) {
                                //Login Successful
                                dlg.setProgress(100);
                                dlg.dismiss();
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else if (parseUser == null) {
                                //Username or Password is invalid
                                Toast.makeText(LoginScreen.this, "Username and/or Password does not match Parse's records.", Toast.LENGTH_LONG).show();
                            } else {
                                //An error occurred.
                                Toast.makeText(LoginScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.dismiss();
                } else {
                    //Fields are empty, Do Nothing.
                }
            }
        });
    }

    private void saveNewUser() {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(email);
        parseUser.setEmail(email);

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    //Save did not succeed.
                    Toast.makeText(LoginScreen.this,"Error! Parse Exception Code: "+e.getCode(),Toast.LENGTH_LONG).show();
                }else{
                    //Object saved successfully.
                    Toast.makeText(LoginScreen.this, "New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserDetailsFromFB(){
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me", null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                try{
                    email = response.getJSONObject().getString("email");
                    userName.setText(email);
                    saveNewUser();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    //Check if userName and password fields are empty
    //Returns true if either field is empty
    private boolean FieldsAreEmpty(){
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder("Please ");

        if (isEmpty(userName)) {
            validationError = true;
            validationErrorMessage.append("enter your username");
        }
        if (isEmpty(password)) {
            if (validationError) {
                validationErrorMessage.append(", and");
            }
            validationError = true;
            validationErrorMessage.append(" enter your password");
        }
        validationErrorMessage.append(".");

        if (validationError) {
            Toast.makeText(LoginScreen.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
            return true;
        }else{
            return false;
        }
    }

    //Check if EditText is empty
    //Returns true if empty.
    private boolean isEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}