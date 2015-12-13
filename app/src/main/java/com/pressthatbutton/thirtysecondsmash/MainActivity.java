package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    public static final String PACKAGE_NAME = "com.pressthatbutton.thirtysecondsmash";
    public Button btnStartGame;
    public Button btnMainToOwn;
    public Button btnMainToAll;
    public Button btnMainToHelp;
    public Button btnChangeName;
    public Button btnMainToLogin;

    public static String PlayerName = "Pl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //I have no clue what this does.
        try {
            PackageInfo info = getPackageManager().getPackageInfo(PACKAGE_NAME, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            //
        } catch (NoSuchAlgorithmException e) {
            //
        }

        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartGame(v);
            }
        });

        btnMainToOwn = (Button) findViewById(R.id.btnMainToOwn);
        btnMainToOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMyHighScores(v);
            }
        });

        btnMainToAll = (Button) findViewById(R.id.btnMainToAll);
        btnMainToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllHighScores(v);
            }
        });

        btnMainToHelp = (Button) findViewById(R.id.btnMainToHelp);
        btnMainToHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerInstructions(v);
            }
        });

        btnChangeName = (Button) findViewById(R.id.btnChangeName);
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChangeNameDialog();
            }
        });

        btnMainToLogin = (Button) findViewById(R.id.btnMainToLogin);
        btnMainToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLoginScreen(v);
            }
        });
    }

    //Launch Game when clicked
    protected void StartGame(View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    //Launch High All High Scores when clicked
    protected void ShowAllHighScores(View view) {
        Intent intent = new Intent(MainActivity.this, ShowAllHighScores.class);
        startActivity(intent);
    }

    //Launch High Own High Scores when clicked
    protected void ShowMyHighScores(View view) {
        Intent intent = new Intent(MainActivity.this, ShowOwnHighScores.class);
        startActivity(intent);
    }

    //Launch Instructions when pressed
    protected void PlayerInstructions(View view) {
        Intent intent = new Intent(MainActivity.this, PlayerInstructions.class);
        startActivity(intent);
    }

    //Launch Change Name Dialog
    protected void ShowChangeNameDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChangeNameDialogFragment newFragment = new ChangeNameDialogFragment();

        newFragment.show(fragmentManager, "ChangeNameDialog");
    }

    //Launch Log In Screen when clicked
    protected void ShowLoginScreen(View view) {
        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
        startActivity(intent);
    }
}
