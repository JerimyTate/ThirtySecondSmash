package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowOwnHighScores extends AppCompatActivity {
    public Button btnOwnToMain;
    public Button btnOwnToAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_own_high_scores);

        btnOwnToMain = (Button)findViewById(R.id.btnOwnToMain);
        btnOwnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllHighScores(v);
            }
        });

        btnOwnToAll = (Button)findViewById(R.id.btnOwnToAll);
        btnOwnToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMain(v);
            }
        });
    }

    //Launch All High Scores when clicked
    private void ShowAllHighScores(View view)
    {
        Intent intent = new Intent(view.getContext(), ShowAllHighScores.class);
        startActivity(intent);
    }

    //Return to Main Activity when clicked
    private void BackToMain(View view)
    {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }
}
