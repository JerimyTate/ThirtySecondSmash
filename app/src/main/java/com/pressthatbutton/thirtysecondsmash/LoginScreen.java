package com.pressthatbutton.thirtysecondsmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JFaucette on 11/23/15.
 */
public class LoginScreen extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    Button btnLogin;
    Button btnFacebook;
    Button btnRegister;

    String name = null;
    String email = null;

    ParseUser parseUser;

    private EditText userName;
    private EditText password;

    public static final List<String> mPermissions = new ArrayList<String>() {{
        add ("public_profile");
        add ("email");
    }};

    TextView tx1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userName = (EditText) findViewById(R.id.txtUserName);
        password = (EditText) findViewById(R.id.txtPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        btnFacebook = (Button) findViewById(R.id.btnFacebook);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validationError = false;
                StringBuilder validationErrorMessage = new StringBuilder("Please ")

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
                    return;
                }

                final ProgressDialog dlg = new ProgressDialog(LoginScreen.this);
                dlg.setTitle("Hold up!");
                dlg.setMessage("We are logging you in... Chill");
                dlg.show();

                ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), (parseUser, e) {
                    dlg.dismiss();
                }
                if (e !=null) {
                    Toast.makeText(LoginScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                }
        });

        btnRegister.setOnClickListener((v) {
                Intent intent = new Intent(LoginScreen.this, MainActivity.class));
        }
        startActivity(intent);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginScreen.this,mPermissions, (user, err) {

                    if (user == null){
                        Log.d("MyApp", "User cancelled Facebook logoin.");
                    } else if (user.isNew()) {
                        Log.d("MyApp", "User signed up and logged in through Facebook.");
                        getUserDetailsFromFB();
                        saveNewUser();
                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        getUserDetailsFromFB();
                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(intent);
                        Log.d("MyApp", "User logged in with Facebook");
                    }
                    }
        }
    });
    }

    private void saveNewUser() {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(email);
        parseUser.setEmail(email);

        parseUser.saveInBackground((e)); {
            Toast.makeText(LoginScreen.this,"New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserDetailsFromFB() {

        new GraphRequest(
                AccessToken.getCurrentAccessToken(), "/me", null, HttpMethod.GET, (response));

        try {
            email = response.getJSONObject().getString("email");
            userName.setText(email);
            saveNewUser();
        } catch (JSONException e) {
            e.printStackTrace();
    }
        ).executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isEmpty(EditText text){
        if(text.getText().toString().trim().length()>0) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}