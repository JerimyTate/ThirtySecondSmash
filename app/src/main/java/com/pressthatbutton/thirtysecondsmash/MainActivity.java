package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public Button btnStartGame;
    public Button btnMainToOwn;
    public Button btnMainToAll;
    public Button btnMainToHelp;
    public Button btnMainToLogin;

    ParseUser parseUser = AppParse._parseUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnMainToLogin = (Button) findViewById(R.id.btnMainToLogin);
        btnMainToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLoginScreen(v);
            }
        });

        /*if(!parseUser.isAuthenticated()){
            btnMainToAll.setVisibility(View.GONE);
            btnMainToOwn.setVisibility(View.GONE);
            btnStartGame.setVisibility(View.GONE);
        }else{
            btnMainToAll.setVisibility(View.VISIBLE);
            btnMainToOwn.setVisibility(View.VISIBLE);
            btnStartGame.setVisibility(View.VISIBLE);
        }*/
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
        Intent intent = new Intent(MainActivity.this, ShowOwnHighSCores.class);
        startActivity(intent);
    }

    //Launch Instructions when pressed
    protected void PlayerInstructions(View view) {
        Intent intent = new Intent(MainActivity.this, PlayerInstructions.class);
        startActivity(intent);
    }

    //Launch Log In Screen when clicked
    protected void ShowLoginScreen(View view) {
        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
        startActivity(intent);
    }
}
