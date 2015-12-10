package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public Button btnStartGame;
    public Button btnMainToOwn;
    public Button btnMainToAll;
    public Button btnMainToHelp;
    public Button btnChangeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartGame = (Button)findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(v);
            }
        });

        btnMainToOwn = (Button)findViewById(R.id.btnMainToOwn);
        btnMainToOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyHighScores(v);
            }
        });

        btnMainToAll = (Button)findViewById(R.id.btnMainToAll);
        btnMainToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllHighScores(v);
            }
        });

        btnMainToHelp = (Button)findViewById(R.id.btnMainToHelp);
        btnMainToHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerInstructions(v);
            }
        });

        btnChangeName = (Button)findViewById(R.id.btnChangeName);
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeNameDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Launch Game when clicked
    public void startGame(View view)
    {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    //Launch High All High Scores when clicked
    public void showAllHighScores(View view)
    {
        Intent intent = new Intent(MainActivity.this, ShowAllHighScores.class);
        startActivity(intent);
    }
    //Launch High All High Scores when clicked
    public void showMyHighScores(View view)
    {
        Intent intent = new Intent(MainActivity.this, ShowOwnHighScores.class);
        startActivity(intent);
    }

    //Launch Instructions when pressed
    public void playerInstructions(View view)
    {
        Intent intent = new Intent(MainActivity.this, PlayerInstructions.class);
        startActivity(intent);
    }

    //Launch Change Name Dialog
    public void showChangeNameDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChangeNameDialogFragment newFragment = new ChangeNameDialogFragment();

        newFragment.show(fragmentManager, "ChangeNameDialog");
    }
}
