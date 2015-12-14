package com.pressthatbutton.thirtysecondsmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
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
    private Button btnLogOut;
    public static LinearLayout ll_log_in_components;

    protected String email;

    public ParseUser parseUser = AppParse._parseUser;

    private EditText userName;
    private EditText password;
    private TextView logInStatus;

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
        ll_log_in_components = (LinearLayout)findViewById(R.id.container_log_in_components);

        btnLogOut = (Button)findViewById(R.id.btn_sign_out);
        btnLogOut.setVisibility(View.GONE);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e!=null){
                            Toast.makeText(LoginScreen.this, "Error Logging Out. ParseException code: "+e.getCode(), Toast.LENGTH_LONG).show();
                            Log.d("MyApp","Error! LoginScreen ParseUser.logOutInBackground. ParseException code: "+e.getCode());
                            e.printStackTrace();
                        }else{
                            Toast.makeText(LoginScreen.this, "Successfully Logged Out!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                            logInStatus.setText("You are currently Logged Out.");
                            btnLogOut.setVisibility(View.GONE);
                            ll_log_in_components.setVisibility(View.VISIBLE);
                            finish();
                        }
                    }
                });
            }
        });

        logInStatus = (TextView) findViewById(R.id.txt_login_status);
        if(parseUser!= null || parseUser.isAuthenticated()){
            logInStatus.setText("Currently logged in as "+parseUser.getUsername()+".");
            if(parseUser.isAuthenticated()) {
                ll_log_in_components.setVisibility(View.GONE);
                btnLogOut.setVisibility(View.VISIBLE);
            }
        }

        //Filters out characters that are not letters or digits
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                boolean keepOriginal = true;
                StringBuilder stringBuilder = new StringBuilder(end - start);
                for (int i = start; i<end;++i){
                    char c = source.charAt(i);
                    if(isCharAllowed(c)){
                        stringBuilder.append(c);
                    }else{
                        keepOriginal = false;
                    }
                }
                if(keepOriginal){
                    return null;
                }else {
                    return stringBuilder;
                }
            }

            private boolean isCharAllowed(char c){
                return Character.isLetterOrDigit(c) || Character.isSpaceChar(c)
                        || Character.valueOf(c).compareTo('@')==0 || Character.valueOf(c).compareTo('.')==0;
            }
        };
        userName.setFilters(new InputFilter[]{filter});

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_log_in_components.setVisibility(View.GONE);
                Fragment fragment = new SignUpFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_signup_fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        btnFacebook = (Button) findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginScreen.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if(err != null){
                            Log.d("MyApp", "Error! Parse Exception Code: " + err.getCode());
                            err.printStackTrace();
                        }
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                            saveNewUser(true);
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                        } else if(!user.isNew()){
                            Log.d("MyApp", "User logged in through Facebook!");
                            getUserDetailsFromFB();
                            saveNewUser(false);
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Log.d("MyApp","User did not log on to Facebook.");
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
                    Toast.makeText(LoginScreen.this, "Hold up!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginScreen.this, "We are logging you in... Chill", Toast.LENGTH_LONG).show();
                    ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null && parseUser != null) {
                                //Login Successful
                                parseUser = ParseUser.getCurrentUser();
                                Toast.makeText(LoginScreen.this, "Welcome, "+parseUser.getUsername()+"!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else if (parseUser == null) {
                                //Username or Password is invalid
                                Toast.makeText(LoginScreen.this, "Username and/or Password does not match Parse's records.", Toast.LENGTH_LONG).show();
                            } else if (e!=null){
                                //An error occurred.
                                Log.d("MyApp","Error! LoginScreen ParseUser.logInInBackground. ParseException code: "+e.getCode());
                                e.printStackTrace();
                            }else{
                                //???
                                Log.d("MyApp","Other Event. LoginScreen ParseUser.logInInBackground. ParseUser isAuthenticated: "+parseUser.isAuthenticated()+
                                        ". Session token: "+parseUser.getSessionToken()+".");
                            }
                        }
                    });
                } else {
                    //Fields are empty, Do Nothing.
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(parseUser!= null || parseUser.isAuthenticated()){
            logInStatus.setText("Currently logged in as "+parseUser.getUsername()+".");
            if(parseUser.isAuthenticated()) {
                ll_log_in_components.setVisibility(View.GONE);
                btnLogOut.setVisibility(View.VISIBLE);
            }
        }
    }

    private void saveNewUser(final boolean isNew) {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(email);
        parseUser.setEmail(email);

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    //Save did not succeed.
                    Toast.makeText(LoginScreen.this, "Error! Parse Exception Code: " + e.getCode(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } else if (parseUser.isAuthenticated()) {
                    //Object saved successfully.

                    if (isNew) {
                        Toast.makeText(LoginScreen.this, "New user, " + parseUser.getUsername() + ", signed up.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginScreen.this, "Returning user, " + parseUser.getUsername() + ", successfully logged in.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.d("MyApp","LoginScreen saveNewUser(). ParseUser session token: "+parseUser.getSessionToken()+
                            ". IsAuthenticated: "+parseUser.isAuthenticated());
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
                    //Print everything in Log debug
                    Log.d("MyApp",response.getRawResponse());
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